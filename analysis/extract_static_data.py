"""Read constant-only JVM <clinit> methods without executing the target JAR.

Supports only stack/array construction used by the selected shipped classes.
Fails on any invocation, branch, or unsupported opcode rather than guessing.
"""
from pathlib import Path
import struct, json, zipfile, hashlib

ROOT = Path(__file__).resolve().parents[1]

class Reader:
    def __init__(self, data): self.data, self.pos = data, 0
    def take(self, n):
        out = self.data[self.pos:self.pos+n]; self.pos += n; return out
    def u1(self): return self.take(1)[0]
    def u2(self): return struct.unpack('>H', self.take(2))[0]
    def u4(self): return struct.unpack('>I', self.take(4))[0]

def read_class(data):
    r = Reader(data)
    assert r.u4() == 0xcafebabe
    minor, major, count = r.u2(), r.u2(), r.u2()
    cp = [None] * count; i = 1
    while i < count:
        tag = r.u1()
        if tag == 1: value = r.take(r.u2()).replace(b'\xc0\x80', b'\0').decode('utf-8', 'surrogatepass')
        elif tag == 3: value = struct.unpack('>i', r.take(4))[0]
        elif tag == 4: value = struct.unpack('>f', r.take(4))[0]
        elif tag in (5,6): value = r.take(8)
        elif tag in (7,8,16): value = r.u2()
        elif tag in (9,10,11,12,18): value = (r.u2(), r.u2())
        elif tag == 15: value = (r.u1(), r.u2())
        else: raise ValueError(f'Unknown pool tag {tag}')
        cp[i] = (tag,value); i += 2 if tag in (5,6) else 1
    access, this, superclass = r.u2(), r.u2(), r.u2()
    r.take(r.u2()*2)
    def attributes():
        result = {}
        for _ in range(r.u2()):
            name = cp[r.u2()][1]; result[name] = r.take(r.u4())
        return result
    fields = []
    for _ in range(r.u2()):
        flags, name, desc = r.u2(), cp[r.u2()][1], cp[r.u2()][1]
        fields.append((name,desc,flags,attributes()))
    methods = []
    for _ in range(r.u2()):
        flags, name, desc = r.u2(), cp[r.u2()][1], cp[r.u2()][1]
        methods.append((name,desc,flags,attributes()))
    return cp, fields, methods, major

def extract(data):
    cp, fields, methods, major = read_class(data)
    result = {}
    for name,desc,flags,attrs in fields:
        if 'ConstantValue' in attrs:
            val = cp[int.from_bytes(attrs['ConstantValue'], 'big')]
            result[name] = cp[val[1]][1] if val[0] == 8 else val[1]
    clinit = [m for m in methods if m[0] == '<clinit>']
    if not clinit: return result
    r = Reader(clinit[0][3]['Code']); r.u2(); r.u2(); code = Reader(r.take(r.u4()))
    stack=[]; local={}
    def constant(index):
        tag,value=cp[index]; return cp[value][1] if tag == 8 else value
    while code.pos < len(code.data):
        offset=code.pos; op=code.u1()
        if op == 0: pass
        elif op == 1: stack.append(None)
        elif 2 <= op <= 8: stack.append(op-3)
        elif op == 16: stack.append(struct.unpack('>b',code.take(1))[0])
        elif op == 17: stack.append(struct.unpack('>h',code.take(2))[0])
        elif op == 18: stack.append(constant(code.u1()))
        elif op == 19: stack.append(constant(code.u2()))
        elif op == 25: stack.append(local[code.u1()])
        elif 42 <= op <= 45: stack.append(local[op-42])
        elif op == 58: local[code.u1()] = stack.pop()
        elif 75 <= op <= 78: local[op-75] = stack.pop()
        elif op in (79,83,84,85,86):
            value,index,array=stack.pop(),stack.pop(),stack.pop(); array[index]=value
        elif op == 89: stack.append(stack[-1])
        elif op == 188:
            kind=code.u1(); stack.append([0]*stack.pop())
        elif op == 189:
            code.u2(); stack.append([None]*stack.pop())
        elif op == 179:
            cls,nt=cp[code.u2()][1]; name,desc=cp[nt][1]; result[cp[name][1]]=stack.pop()
        elif op == 177: break
        else: raise ValueError(f'Unsupported <clinit> opcode {op:#x} at {offset}')
    return result

def main():
    jar=ROOT/'Loan-12-Su-Quan.jar'
    names=['dd','bq','ea','com/mic/smsgame/suquan/h','com/mic/smsgame/suquan/e']
    output={'_source':{'jar':jar.name,'sha256':hashlib.sha256(jar.read_bytes()).hexdigest(),'method':'Non-executing constant-array JVM bytecode interpreter'}, 'classes':{}}
    with zipfile.ZipFile(jar) as archive:
        for name in names: output['classes'][name]=extract(archive.read(name+'.class'))
    destination=ROOT/'analysis'/'static-data.json'
    destination.write_text(json.dumps(output,ensure_ascii=False,indent=2),encoding='utf-8')
    classes=output['classes']; dd,bq,ea,words=(classes[key] for key in ('dd','bq','ea','com/mic/smsgame/suquan/h'))
    game={'source':output['_source'], 'characters':[], 'locations':[], 'roads':[], 'campaign':[], 'sideQuests':[], 'training':[],
          'spells':[{'id':i,'name':name,'description':words['b'][i]} for i,name in enumerate(words['a'])],
          'items':[{'id':i,'name':name,'description':words['d'][i]} for i,name in enumerate(words['c'])],
          'heroDescriptions':words['e'],'credits':words['f'],'collectibleNames':words['g'],'intro':words['h'],'ending':words['i']}
    for i,row in enumerate(dd['o']):
        game['characters'].append({'id':i,'name':ea['a'][i],'level':row[0],'baseHpBonus':row[1],'baseAttackBonus':row[2],'skills':row[3:],
                                   'maxHp':100+5*row[0]+row[1], 'attack':row[0]//2+5+row[2]})
    for i,row in enumerate(bq['b']):
        game['locations'].append({'id':i,'name':bq['a'][i],'x':row[0],'y':row[1],'type':row[2],'adjacencyRoadIds':bq['c'][i],'encounterCharacterId':dd['p'][i]})
    for i,row in enumerate(bq['d']):
        game['roads'].append({'id':i,'from':row[0],'to':row[1],'minLevel':row[2],'maxLevel':row[3]})
    for i,row in enumerate(dd['l']):
        game['campaign'].append({'id':i,'mode':row[0],'locationId':row[1],'enemyId':row[2],'xpReward':row[3],'goldReward':row[4],'collectibleFlag':row[5],
                                 'unlockLocationIds':dd['m'][i] or [],'before':ea['b'][i] if i<len(ea['b']) else None,
                                 'win':ea['c'][i] if i<len(ea['c']) else None,'lose':ea['d'][i] if i<len(ea['d']) else None})
    for i,row in enumerate(dd['n']):
        game['sideQuests'].append({'id':i,'mode':row[0],'locationId':row[1],'enemyId':row[2],'permanentAttackBonus':row[3],'permanentHpBonus':row[4],
                                  'before':ea['e'][i],'win':ea['f'][i],'lose':ea['g'][i]})
    for i,row in enumerate(dd['q']):
        game['training'].append({'id':i,'characterId':row[0],'levelOffset':row[1],'xpThresholdDifferencePercent':row[2],'goldCost':row[3]})
    (ROOT/'analysis'/'game-data.json').write_text(json.dumps(game,ensure_ascii=False,indent=2),encoding='utf-8')
    for key,fields in output['classes'].items():
        print(key,{k:len(v) if isinstance(v,list) else v for k,v in fields.items()})

if __name__=='__main__': main()
