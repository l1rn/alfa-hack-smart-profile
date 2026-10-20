"""Strip Alfa site nav/footer noise from bookmarklet dumps; rename by URL slug."""
from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parent / "alfa_site"

START_MARKERS = (
    "Онлайн-заявки\nВойти\n",
    "Онлайн-заявки\r\nВойти\r\n",
)
END_MARKERS = (
    "\nСВЯЗЬ С БАНКОМ\n",
    "\nСВЯЗЬ С БАНКОМ\n",
    "\n© 2001-",
    "\n© 2001-",
)


def parse_frontmatter(text: str) -> tuple[dict[str, str], str]:
    if not text.startswith("---\n"):
        return {}, text
    end = text.find("\n---\n", 4)
    if end < 0:
        return {}, text
    meta_block = text[4:end]
    body = text[end + 5 :]
    meta: dict[str, str] = {}
    for line in meta_block.splitlines():
        if ":" not in line:
            continue
        key, val = line.split(":", 1)
        meta[key.strip()] = val.strip().strip('"')
    return meta, body


def extract_core(body: str) -> str:
    start = 0
    for marker in START_MARKERS:
        idx = body.rfind(marker)
        if idx >= 0:
            start = idx + len(marker)
            break

    end = len(body)
    for marker in END_MARKERS:
        idx = body.find(marker, start)
        if 0 <= idx < end:
            end = idx

    core = body[start:end].strip()
    # Drop trailing menu echo that often repeats after FAQ
    for cut in ("\nМалый бизнес\n\nКредитование бизнеса\n", "\nМалый бизнес\nКредитование бизнеса\n"):
        idx = core.find(cut)
        if idx > 400:
            core = core[:idx].strip()
            break
    return core


def slug_from_url(url: str) -> str:
    path = re.sub(r"^https?://[^/]+", "", url).strip("/")
    if not path or path == "sme":
        return "sme-hub"
    if path == "sme/profits":
        return "profits-hub"
    slug = path.split("/")[-1] or path.replace("/", "-")
    return re.sub(r"[^a-zA-Z0-9_-]+", "-", slug).strip("-") or "page"


def main() -> None:
    files = sorted(ROOT.glob("*.md"))
    if not files:
        print("no files")
        return

    for path in files:
        raw = path.read_text(encoding="utf-8")
        meta, body = parse_frontmatter(raw)
        url = meta.get("url", "")
        title = meta.get("title", path.stem)
        core = extract_core(body)
        if len(core) < 200:
            print(f"SKIP weak extract ({len(core)} chars): {path.name}")
            continue

        out = "\n".join(
            [
                "---",
                f'title: "{title}"',
                f'url: "{url}"',
                f'saved_at: "{meta.get("saved_at", "")}"',
                f'source_host: "{meta.get("source_host", "alfabank.ru")}"',
                f'cleaned: "true"',
                "---",
                "",
                f"# {title}",
                "",
                f"> Источник: {url}",
                "",
                core,
                "",
            ]
        )
        slug = slug_from_url(url)
        new_path = ROOT / f"{slug}.md"
        if new_path.exists() and new_path.resolve() != path.resolve():
            # keep unique if collision
            new_path = ROOT / f"{slug}-{path.stat().st_mtime_ns}.md"
        new_path.write_text(out, encoding="utf-8")
        if new_path.resolve() != path.resolve():
            path.unlink()
        print(f"OK {path.name} -> {new_path.name} ({len(core)} chars)")


if __name__ == "__main__":
    main()
