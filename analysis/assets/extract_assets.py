"""Reproduce the original game resources without modifying source bytes.

Run: uv run --with pillow --with imageio-ffmpeg --with mido python analysis/assets/extract_assets.py
"""
from pathlib import Path
from PIL import Image, ImageDraw, ImageFont
import hashlib
import io
import json
import math
import shutil
import struct
import subprocess
import wave
import zipfile
import zlib

ROOT = Path(__file__).resolve().parents[2]
PNG_SIGNATURE = bytes.fromhex("89504e470d0a1a0a")


def sha(data):
    return hashlib.sha256(data).hexdigest()


def verify_png(data):
    """Check every PNG chunk CRC and file boundary; Pillow also decodes all pixels."""
    assert data[:8] == PNG_SIGNATURE
    offset, chunks = 8, []
    while offset < len(data):
        size = struct.unpack_from(">I", data, offset)[0]
        kind = data[offset + 4:offset + 8]
        body = data[offset + 8:offset + 8 + size]
        crc = struct.unpack_from(">I", data, offset + size + 8)[0]
        assert zlib.crc32(kind + body) & 0xFFFFFFFF == crc, (kind, offset)
        chunks.append(kind.decode("ascii"))
        offset += size + 12
        if kind == b"IEND":
            break
    assert offset == len(data), (offset, len(data))
    with Image.open(io.BytesIO(data)) as image:
        image.load()
        return image.size, image.mode, chunks, "transparency" in image.info or "A" in image.getbands()


def contact_sheet(entries, output, cols=5, cell=(230, 205), scale_limit=4):
    rows = math.ceil(len(entries) / cols)
    canvas = Image.new("RGB", (cols * cell[0], rows * cell[1]), "#171c20")
    draw = ImageDraw.Draw(canvas)
    for index, entry in enumerate(entries):
        x, y = index % cols * cell[0], index // cols * cell[1]
        for ty in range(y + 32, y + cell[1] - 4, 12):
            for tx in range(x + 4, x + cell[0] - 4, 12):
                draw.rectangle((tx, ty, min(tx+11,x+cell[0]-5), min(ty+11,y+cell[1]-5)), fill="#353c42" if ((tx-x)//12+(ty-y)//12) % 2 else "#414a51")
        draw.text((x + 7, y + 4), entry["source"], fill="#fff5cc")
        draw.text((x + 7, y + 18), f'{entry["width"]} x {entry["height"]}', fill="#a9bdc9")
        with Image.open(ROOT / entry["file"]) as im:
            im = im.convert("RGBA")
            factor = min((cell[0]-12)/im.width, (cell[1]-40)/im.height, scale_limit)
            # Nearest-neighbor display leaves the original pixels unaltered.
            im = im.resize((max(1,round(im.width*factor)),max(1,round(im.height*factor))), Image.Resampling.NEAREST)
            px, py = x + (cell[0]-im.width)//2, y+34+(cell[1]-40-im.height)//2
            canvas.paste(im, (px, py), im)
    canvas.save(output)


def main():
    entries = []
    originals = []
    archive = zipfile.ZipFile(ROOT / "Loan-12-Su-Quan.jar")
    for member in sorted(archive.namelist()):
        source = Path(member)
        if source.suffix.lower() not in (".mg", ".png", ".amr", ".mid"):
            continue
        rel = source
        data = archive.read(member)
        raw_path = ROOT / "assets/original" / rel
        raw_path.parent.mkdir(parents=True, exist_ok=True)
        raw_path.write_bytes(data)
        original = {"source":rel.as_posix(), "file":raw_path.relative_to(ROOT).as_posix(), "bytes":len(data), "sha256":sha(data)}
        originals.append(original)
        if source.suffix in (".mg", ".png"):
            restored = data
            method = "original PNG"
            if source.suffix == ".mg":
                assert data[4:8] == PNG_SIGNATURE[4:], source
                assert struct.unpack(">I", data[:4])[0] == len(data), source
                restored = PNG_SIGNATURE[:4] + data[4:]
                method = "replace first 4 bytes (big-endian source byte length) with 89 50 4E 47"
            size, mode, chunks, transparent = verify_png(restored)
            out = ROOT / "assets/images" / rel.with_suffix(".png")
            out.parent.mkdir(parents=True, exist_ok=True)
            out.write_bytes(restored)
            entries.append({"kind":"image", "source":rel.as_posix(), "original":original["file"], "file":out.relative_to(ROOT).as_posix(), "bytes":len(restored), "sourceSha256":sha(data), "sha256":sha(restored), "width":size[0], "height":size[1], "mode":mode, "alpha":transparent, "chunks":chunks, "restoration":method})
        else:
            out = ROOT / "assets/audio" / rel.name
            out.write_bytes(data)
            entry = {"kind":"audio", "source":rel.as_posix(), "original":original["file"], "file":out.relative_to(ROOT).as_posix(), "bytes":len(data), "sha256":sha(data), "format":"AMR-NB" if source.suffix == ".amr" else "Standard MIDI"}
            if source.suffix == ".mid":
                assert data[:4] == b"MThd", source
                entry.update(dict(zip(("midiFormat","tracks","ticksPerQuarterNote"),struct.unpack_from(">HHH",data,8))))
                try:
                    import mido
                    midi = mido.MidiFile(file=io.BytesIO(data))
                    entry["durationSeconds"] = round(midi.length, 6)
                    entry["noteOnEvents"] = sum(msg.type == "note_on" and msg.velocity > 0 for track in midi.tracks for msg in track)
                    entry["programs"] = sorted({msg.program for track in midi.tracks for msg in track if msg.type == "program_change"})
                except ImportError:
                    pass
            else:
                assert data[:6] == b"#!AMR\n", source
                entry["sampleRate"] = 8000
            entries.append(entry)
    manifest = {"sourceArchive":"Loan-12-Su-Quan.jar", "sourceArchiveSha256":sha((ROOT / "Loan-12-Su-Quan.jar").read_bytes()), "restoration":"All .mg resources are PNGs with only the first four signature bytes replaced by their complete byte length in big-endian order. Restore bytes 0..3 to 89 50 4E 47; all subsequent bytes remain identical.", "verification":"All original SHA-256 hashes retained; every PNG chunk CRC checked; all PNG pixels decoded with Pillow.", "counts":{"images":sum(x["kind"]=="image" for x in entries),"audio":sum(x["kind"]=="audio" for x in entries)}, "assets":entries, "originals":originals}
    (ROOT / "assets/manifest.json").write_text(json.dumps(manifest,ensure_ascii=False,indent=2)+"\n",encoding="utf-8")
    images = [x for x in entries if x["kind"] == "image"]
    # AMR is retained verbatim. PCM WAV derivatives make the original sound
    # effects playable in browsers without an AMR decoder.
    try:
        import imageio_ffmpeg
        ffmpeg = imageio_ffmpeg.get_ffmpeg_exe()
    except ImportError:
        ffmpeg = shutil.which("ffmpeg")
    if ffmpeg:
        for entry in entries:
            if entry.get("format") != "AMR-NB":
                continue
            wav_path = (ROOT / entry["file"]).with_suffix(".wav")
            subprocess.run([ffmpeg,"-hide_banner","-loglevel","error","-y","-i",str(ROOT/entry["file"]),"-c:a","pcm_s16le",str(wav_path)],check=True)
            with wave.open(str(wav_path)) as wav:
                entry["browserAudio"] = {"file":wav_path.relative_to(ROOT).as_posix(),"format":"PCM signed 16-bit WAV","sha256":sha(wav_path.read_bytes()),"bytes":wav_path.stat().st_size,"sampleRate":wav.getframerate(),"channels":wav.getnchannels(),"samples":wav.getnframes(),"durationSeconds":wav.getnframes()/wav.getframerate()}
        (ROOT / "assets/manifest.json").write_text(json.dumps(manifest,ensure_ascii=False,indent=2)+"\n",encoding="utf-8")
    main_images = [x for x in images if "/" not in x["source"]]
    nested_images = [x for x in images if "/" in x["source"]]
    contact_sheet(main_images, ROOT / "analysis/assets/contact-sheet-main.png")
    contact_sheet(nested_images, ROOT / "analysis/assets/contact-sheet-map-fonts.png")
    contact_sheet([x for x in images if x["source"] in ("bkmenu.mg","bksplashscreen.mg","bkboard.mg","avatar.mg","m/m.mg")],ROOT / "analysis/assets/contact-sheet-major.png", cols=3,cell=(340,460))
    print(json.dumps(manifest["counts"]))
    for entry in images:
        print(f'{entry["source"]:28} {entry["width"]:4} x {entry["height"]:4} {entry["mode"]}')


if __name__ == "__main__":
    main()
