"""Encode sprite source rectangles verified against the decompiled game.

Run after extract_assets.py. No texture is resampled or re-encoded.
"""
from pathlib import Path
import json

ROOT = Path(__file__).resolve().parents[2]
MANIFEST = json.loads((ROOT / "assets/manifest.json").read_text(encoding="utf-8"))
IMAGES = {a["source"]: a for a in MANIFEST["assets"] if a["kind"] == "image"}
atlases = {}


def strip(source, count, provenance):
    image = IMAGES[source + ".mg"]
    width = image["width"] // count
    atlases[source] = {"file": image["file"], "layout": "horizontal", "frames": [{"x": i * width,"y":0,"width":width,"height":image["height"]} for i in range(count)],"unusedTrailingPixels":image["width"]-width*count,"provenance":provenance}


for name, count in {"fireball":9,"hellfire":4,"icebolt":5,"frozen":9,"healing":3,"shieldlighting":7,"chainlighting":3}.items():
    strip(name,count,"analysis/decompiled/i.java: spell constructor frame counts; analysis/decompiled/dq.java: a(Image,int) and drawRegion")
for name in ("crystalblue","crystalgray","crystalgreen","crystalred","crystalviolet","crystalyellow"):
    strip(name,3,"analysis/decompiled/bs.java: crystal image mapping; bx.java and bp.java: frame count 3")
for name in ("explodesword","exploderice","explodeheart","explodeyinyang","explodegold","explodebook","explodericex2","explodeheartx2","explodeyinyangx2","explodegoldx2","explodebookx2"):
    strip(name,4,"analysis/decompiled/bm.java: constructor g array and a(cc,int,int) uses 4 frames")
for name,count in {"defenceshield":3,"powerattack":3,"focusitem":2,"zoomfocus1":2,"itemshops":6,"m/ar":6,"m/mt1":3}.items():
    strip(name,count,"analysis/decompiled/bx.java, co.java, bh.java, dn.java, com/mic/smsgame/suquan/aa.java")
atlases["avatar"] = {"file":"assets/images/avatar.png","layout":"grid","columns":10,"rows":8,"frames":[{"index":i,"x":i%10*30,"y":i//10*30,"width":30,"height":30} for i in range(80)],"provenance":"analysis/decompiled/u.java: b(Graphics,int,int,int,boolean); bs.java: a(int,int); Java transform 2 mirrors horizontally"}
atlases["blastlighting"] = {"file":"assets/images/blastlighting.png","layout":"irregular","frames":[{"x":x,"y":y,"width":w,"height":h,"verticalAnchorOffset":anchor} for x,y,w,h,anchor in zip([19,20,17,12,0],[0,17,51,68,131],[12,11,4,27,56],[17,34,17,63,62],[91,74,56,1,0])],"provenance":"analysis/decompiled/au.java: w/x/y/z/A arrays; a(Graphics,int,int)"}
data={"sourceArchiveSha256":MANIFEST["sourceArchiveSha256"],"rectangleConvention":"top-left origin, integer source-pixel coordinates; no scaling. Horizontal strip widths use Java integer division, preserving the game's ignored trailing pixels.","atlases":atlases,"boardTileOrder":["sword","rice","heart","yinyang","gold","book","swordred"],"spellIconOrder":["fireballicon","meteoricon","hellfireicon","chainlightingicon","shieldlightingicon","blastlightingicon","icebolticon","healingicon","frozenicon"]}
(ROOT / "assets/sprites.json").write_text(json.dumps(data,ensure_ascii=False,indent=2)+"\n",encoding="utf-8")
print(f"Wrote {len(atlases)} atlases, {sum(len(a['frames']) for a in atlases.values())} source rectangles")
