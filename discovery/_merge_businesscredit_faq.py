from __future__ import annotations

import re
from pathlib import Path

faq_dir = Path(__file__).resolve().parent / "alfa_site" / "FAQ"
out = Path(__file__).resolve().parent / "alfa_site" / "businesscredit_faq_merged.md"


def core(text: str) -> str:
    for marker in ("Онлайн-заявки\nВойти\n", "Онлайн-заявки\r\nВойти\r\n"):
        i = text.rfind(marker)
        if i >= 0:
            text = text[i + len(marker) :]
            break
    for end in ("\nСВЯЗЬ С БАНКОМ\n", "\nСВЯЗЬ С БАНКОМ\n", "\n© 2001-", "\n© 2001-"):
        j = text.find(end)
        if j > 0:
            text = text[:j]
            break
    return text.strip()


files = sorted(faq_dir.glob("*.md"), key=lambda p: p.stat().st_size, reverse=True)
faq_bits: list[tuple[int, str, str]] = []
seen_bits: set[str] = set()

for p in files:
    raw = p.read_text(encoding="utf-8")
    c = core(raw)
    i = c.find("Полезно знать")
    if i < 0:
        i = c.find("Как нельзя тратить кредит")
    bit = c[i:].strip() if i >= 0 else ""
    if len(bit) < 80:
        continue
    key = re.sub(r"\s+", " ", bit)[:400]
    if key in seen_bits:
        continue
    seen_bits.add(key)
    faq_bits.append((len(bit), p.name, bit))

merged_paras: list[str] = []
para_seen: set[str] = set()

for _, _name, bit in sorted(faq_bits, key=lambda x: -x[0]):
    for para in re.split(r"\n\s*\n", bit):
        ptxt = para.strip()
        if len(ptxt) < 40:
            continue
        if ptxt.startswith("Малый бизнес") or "СВЯЗЬ" in ptxt:
            continue
        k = re.sub(r"\s+", " ", ptxt)
        if k in para_seen:
            continue
        if any(k in re.sub(r"\s+", " ", ex) for ex in merged_paras):
            continue
        merged_paras = [ex for ex in merged_paras if re.sub(r"\s+", " ", ex) not in k]
        para_seen = {re.sub(r"\s+", " ", ex) for ex in merged_paras}
        para_seen.add(k)
        merged_paras.append(ptxt)

header = """---
title: "Кредит для бизнеса — FAQ merged from accordion dumps"
url: "https://alfabank.ru/sme/profits/businesscredit/"
source_host: "alfabank.ru"
note: "Merged from discovery/alfa_site/FAQ/*.md — one accordion open per save"
cleaned: "true"
---

# Кредит для бизнеса — merged FAQ

> Источник: https://alfabank.ru/sme/profits/businesscredit/

Аккордеоны на сайте открывают по одному: каждый сейв дал кусок. Ниже склейка уникальных блоков.

"""

out.write_text(header + "\n\n".join(merged_paras) + "\n", encoding="utf-8")
text = out.read_text(encoding="utf-8")
print(f"wrote {out.name}: {out.stat().st_size} bytes, {len(merged_paras)} blocks from {len(files)} files")
for m in [
    "Снимать наличные",
    "девяти месяцев",
    "поручител",
    "земельных участков",
    "аннуитет",
    "Защита бизнеса",
    "с нуля",
    "паспорт",
    "ИНН",
]:
    ok = m.lower() in text.lower() or m in text
    print(f"  [{'OK' if ok else '--'}] {m}")
