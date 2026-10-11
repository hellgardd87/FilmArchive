"""Validate rendered HTML structure locally; complements the W3C Nu validator."""
from html.parser import HTMLParser
from pathlib import Path
import sys

VOID = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "source", "track", "wbr"}

class Page(HTMLParser):
    def __init__(self):
        super().__init__()
        self.stack, self.ids, self.labels, self.controls, self.errors = [], set(), [], [], []
        self.title_count = self.main_count = self.header_count = self.footer_count = 0
        self.has_charset = self.has_viewport = self.has_description = self.has_doctype = False
        self.language = None
        self.forms = 0

    def handle_decl(self, decl):
        self.has_doctype |= decl.lower() == "doctype html"

    def handle_starttag(self, tag, attributes):
        attrs = dict(attributes)
        if any(name.startswith("th:") for name in attrs):
            self.errors.append("unprocessed Thymeleaf attribute")
        if "id" in attrs:
            if attrs["id"] in self.ids:
                self.errors.append("duplicate id: " + attrs["id"])
            self.ids.add(attrs["id"])
        if tag == "html": self.language = attrs.get("lang")
        if tag == "title": self.title_count += 1
        if tag == "main": self.main_count += 1
        if tag == "header": self.header_count += 1
        if tag == "footer": self.footer_count += 1
        if tag == "meta":
            self.has_charset |= attrs.get("charset", "").upper() == "UTF-8"
            self.has_viewport |= attrs.get("name") == "viewport"
            self.has_description |= attrs.get("name") == "description" and bool(attrs.get("content"))
        if tag == "img" and "alt" not in attrs: self.errors.append("img without alt")
        if tag == "button" and attrs.get("type") not in {"submit", "button", "reset"}:
            self.errors.append("button without correct type")
        if tag == "label" and "for" in attrs: self.labels.append(attrs["for"])
        if tag in {"input", "select", "textarea"} and attrs.get("type") not in {"hidden", "submit", "button", "reset"}:
            self.controls.append((attrs.get("id"), "label" in self.stack))
        if tag == "a" and attrs.get("target") == "_blank" and not {"noopener", "noreferrer"}.issubset(set(attrs.get("rel", "").split())):
            self.errors.append("external link without safe rel")
        if tag == "form":
            if self.forms: self.errors.append("nested form")
            self.forms += 1
        if tag not in VOID: self.stack.append(tag)

    def handle_endtag(self, tag):
        if tag == "form": self.forms -= 1
        if tag in VOID: return
        if not self.stack or self.stack[-1] != tag:
            self.errors.append("unbalanced closing tag: " + tag)
        else:
            self.stack.pop()

    def validate(self):
        if self.stack: self.errors.append("unclosed tags: " + str(self.stack))
        if self.language != "ru": self.errors.append("missing lang=ru")
        if not self.has_doctype: self.errors.append("missing HTML5 doctype")
        if self.title_count != 1: self.errors.append("expected one title")
        if (self.main_count, self.header_count, self.footer_count) != (1, 1, 1):
            self.errors.append("expected one header, main and footer")
        if not all((self.has_charset, self.has_viewport, self.has_description)):
            self.errors.append("missing head metadata")
        for target in self.labels:
            if target not in self.ids: self.errors.append("label points to missing id: " + target)
        for id_, wrapped in self.controls:
            if not wrapped and id_ not in self.labels: self.errors.append("control without label: " + str(id_))
        return self.errors

folder = Path(sys.argv[1]) if len(sys.argv) > 1 else Path(__file__).resolve().parents[1] / "target/rendered-html"
files = sorted(folder.glob("*.html"))
if not files: raise SystemExit("No rendered HTML files. Run mvn test first.")
errors = []
for file in files:
    page = Page()
    page.feed(file.read_text(encoding="utf-8"))
    errors.extend(str(file.name) + ": " + error for error in page.validate())
if errors: raise SystemExit("\n".join(errors))
print(f"OK: {len(files)} rendered pages: nesting, ids, labels, metadata, fragments and external links.")
