# RMS and equipment persistence evidence

The original JAR is unchanged. The extension uses logical keys in the original `dr` allocator, not MIDP physical record IDs.

## V1 continuity update — 2026-10-23

The web shell now runs `web/migrate-v1.js` before creating the game iframe. On an empty ver2 filesystem it reads a committed v1 snapshot from `loan12suquan-fs-v1/fs4`, validates both original stores (`16b` with 16-byte chunks and `161b` with 161-byte chunks), and copies their original Blobs to `loan12suquan-fs-ver2/fs4`. The v1 database is opened without a version upgrade and only read-only transactions are used; opening an absent source is aborted instead of creating it. A playable source requires map checkpoint key 100.

Copying both stores, replacing the target RMS directory and writing the migration receipt `/.loan-ver2-v1-migration` happen in one IndexedDB transaction. The target is checked again inside that transaction. An existing RMS file or receipt prevents automatic replacement, including when two tabs start simultaneously. A failed write aborts the entire replacement. The parent starts the VM only after commit, so its filesystem cache cannot overwrite the transferred data with a pre-import snapshot.

Existing ver2 progress takes precedence, even if the player previously only opened its menu. The **Lấy bản lưu ver1** button is available before starting the game and explicitly confirms replacing ver2 progress. It uses the same validated atomic copy. Close other ver2 tabs before confirming; concurrent gameplay in another already-running VM is not merged. An unreadable source blocks automatic startup with an actionable message and leaves both snapshots intact.

This is a one-way copy of the last committed checkpoint, not shared storage or live synchronization. It requires the same browser profile and origin. Subsequent gameplay and equipment are saved in ver2; v1 remains unchanged. The export/import flow is still available across profiles, devices and origins.

Regression coverage: `v1-migration.spec.cjs` exercises actual v1 Continue, ver2 Continue, equipment save/reload, source byte equality, existing/cancel/confirm, concurrent first opens, absent/corrupt source, transaction abort/retry and responsive screenshots. Its map fixture is the original v1 automated test character TOAN, not a personal browser profile.

## Original records and call sites

- `bg.java:8–9` opens `16b` with 16-byte chunks and `161b` with 161-byte chunks. `bg.java:169–304` uses logical `161b` keys 1/2/3 for collectibles; `bg.java:361–415` uses key 9 for inventory. Gold is the signed big-endian integer at byte 8 of key 9.
- `com/mic/smsgame/suquan/f.java:88–104` stores the original battle snapshot at logical `161b` key 106. Other dynamic `f` accessors target `16b`; all recovered callers in `dd.java` use 101/103/104/105. No recovered JAR caller uses `161b` logical 120/121/122.
- `com/mic/smsgame/suquan/q.java:35,46,107,171–209` caches the six inventory integers in field `E:[I`; the original purchase subtracts its cached gold and calls `bg.b(2,price)`. A web balance change must refresh `E[2]` while that shop instance is open.
- `com/mic/smsgame/suquan/f.java:14` resets original progress and deletes snapshot 106, but has no knowledge of the extension keys. The web after-hook resets extension profile/sidecar separately. Cancel does not call this reset method.

## Physical layout

`dr.java:40–84` loads physical records 1 and 2 (2048-byte index banks) and physical record 3 (one byte selecting bank 1 or 2). Each active bank begins with a big-endian descriptor count; each descriptor contains logical key, byte length, chunk count, then physical chunk IDs. Its trailing free list contains reusable 161-byte chunk IDs. `bd.java:13,30` supplies the descriptor codec. MIDP's exported `midp-rms` file has a 40-byte header and blocks consisting of an 8-byte ID/length header plus payload padded to 8 bytes.

`runtime/equipment/import.js` follows this active index and validates lengths, physical IDs, aliases, counts, free lists and all known extension codecs. It does not search arbitrary chunks for extension magic. Invalid 120/121/122 records fail before a VM import can mutate IndexedDB.

## Extension contract

- Logical 120: versioned equipment profile, six owned tiers, up to three equipped IDs, monotonic revision, and the complete maximum 18 purchase receipts.
- Logical 121: sidecar tied to SHA-256 of the exact original key-106 bytes, profile/battle identity, frozen loadout, and bounded validated combat state. A mismatched snapshot does not reapply start bonuses.
- Logical 122: absolute before/after purchase or reset journal. Purchases persist journal, then absolute gold plus profile, then remove journal, with a durable barrier after each stage. Recovery validates both records and expected gold before writing. A repeated transaction ID returns its receipt without another charge.
- Pure async port: `read(key)`, `write(key,Uint8Array|null)`, `readGold()`, `setGold(absolute)`, `flush()`. `runtime/java-port.js` runs interpreted calls on fresh scheduled Java contexts; the return observer copies results before context teardown. A Java call that yields cannot be treated as a completed ordinary JavaScript function call.

## Durability and limits

`dr.java:207–256` switches inactive index then physical pointer 3; `l.java` catches all RMS exceptions and may return `-1` instead of throwing. Therefore a returned Java flush alone is not proof of persistence. The web port now waits for IndexedDB transaction completion, propagates abort/request errors across overlapping empty barriers, exports committed physical bytes, follows the active bank and compares every requested logical write/delete. A mismatch throws `RMS_DURABILITY`. Every failed port flush latches `requiresReload`; subsequent port writes cannot delete a durable journal based only on optimistic Java cache values.

Automatic recovery handles valid journal/profile/gold combinations. Unknown versions, corrupt CRCs, malformed combat data, unrelated profile/gold conflicts and invalid RMS layouts fail closed and preserve bytes. Payload corruption that destroys the active profile remains a diagnostic/recovery case rather than permission to overwrite an invalid save. Original new-game reset and extension reset are separate Java/web operations; a termination between them remains a boundary requiring attention.

Verification includes all purchase/reset mutation boundaries in pure tests; actual Chrome RMS tests inject IndexedDB aborts and swallowed physical-pointer errors at each of the three purchase flushes, reload the VM, and verify exactly one tier/charge plus idempotent replay. Test browser contexts are disposable and do not use a personal Chrome profile.
