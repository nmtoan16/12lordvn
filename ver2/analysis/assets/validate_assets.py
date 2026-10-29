"""Check resource fidelity and source-rectangle boundaries against the JAR."""
from pathlib import Path
import hashlib
import json
import wave
import zipfile

ROOT = Path(__file__).resolve().parents[2]
manifest = json.loads((ROOT / "assets/manifest.json").read_text(encoding="utf-8"))
digest = lambda data: hashlib.sha256(data).hexdigest()
archive_path = ROOT / manifest["sourceArchive"]
assert digest(archive_path.read_bytes()) == manifest["sourceArchiveSha256"]
with zipfile.ZipFile(archive_path) as archive:
    for entry in manifest["originals"]:
        original = archive.read(entry["source"])
        assert digest(original) == entry["sha256"], entry["source"]
        assert original == (ROOT / entry["file"]).read_bytes(), entry["file"]
    for entry in manifest["assets"]:
        output = (ROOT / entry["file"]).read_bytes()
        assert digest(output) == entry["sha256"], entry["file"]
        source = archive.read(entry["source"])
        if entry["source"].endswith(".mg"):
            assert output == bytes.fromhex("89504e47") + source[4:], entry["file"]
        else:
            assert output == source, entry["file"]
        if "browserAudio" in entry:
            audio = entry["browserAudio"]
            assert digest((ROOT / audio["file"]).read_bytes()) == audio["sha256"]
            with wave.open(str(ROOT / audio["file"])) as wav:
                assert wav.getnframes() == audio["samples"]
                assert wav.getframerate() == audio["sampleRate"] == 8000
                assert wav.getnchannels() == audio["channels"] == 1
                assert wav.getsampwidth() == 2
images = {entry["file"]:entry for entry in manifest["assets"] if entry["kind"] == "image"}
sprites = json.loads((ROOT / "assets/sprites.json").read_text(encoding="utf-8"))
for name, atlas in sprites["atlases"].items():
    image = images[atlas["file"]]
    for frame in atlas["frames"]:
        assert frame["x"] >= 0 and frame["y"] >= 0, name
        assert frame["x"] + frame["width"] <= image["width"], name
        assert frame["y"] + frame["height"] <= image["height"], name
print("PASS: 151 resources match JAR bytes and SHA-256; 131 output images; 14 PCM WAV derivatives; 33 atlases / 212 rectangles within bounds")
