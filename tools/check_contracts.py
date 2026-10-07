"""Static cross-layer checks; requires only Python 3 standard library."""
from pathlib import Path
from html.parser import HTMLParser
import re
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
JAVA = ROOT / "src/main/java/com/hellgardd/filmarchive"
TEMPLATES = ROOT / "src/main/resources/templates"
errors = []


def check(condition, message):
    if not condition:
        errors.append(message)


def properties(class_name, package="dto"):
    source = (JAVA / package / (class_name + ".java")).read_text(encoding="utf-8")
    return {name[0].lower() + name[1:] for name in re.findall(r"public\s+[\w<>]+\s+get(\w+)\(", source)}


class TemplateParser(HTMLParser):
    VOID = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "param", "source", "track", "wbr"}

    def __init__(self, path):
        super().__init__(convert_charrefs=True)
        self.path = path
        self.stack = []
        self.forms = 0

    def handle_starttag(self, tag, attrs):
        if tag == "form":
            check(self.forms == 0, f"{self.path}: nested form")
            self.forms += 1
        if tag not in self.VOID:
            self.stack.append(tag)

    def handle_endtag(self, tag):
        if tag == "form":
            self.forms -= 1
        if tag not in self.VOID:
            check(bool(self.stack) and self.stack[-1] == tag, f"{self.path}: unbalanced </{tag}>")
            if self.stack:
                self.stack.pop()


form_properties = properties("FilmForm")
film_source = (JAVA / "entity/Film.java").read_text(encoding="utf-8")
film_properties = properties("Film", "entity")
for name in ("title", "description", "duration", "budget", "totalBoxOffice", "releaseDate", "genre", "country", "ageLimit", "shootingQuality", "actors"):
    check(name in film_properties, f"Missing Film property: {name}")
check('name="film_people"' in film_source, "Join table must be film_people")
check("CascadeType.REMOVE" not in film_source and "CascadeType.ALL" not in film_source, "Unsafe cascading removal")

form_types = {"filmForm": "FilmForm", "genreForm": "ReferenceForm", "countryForm": "ReferenceForm", "ageForm": "AgeLimitForm", "qualityForm": "QualityForm", "personForm": "PersonForm"}
controller = (JAVA / "controller/CatalogController.java").read_text(encoding="utf-8")
routes = set(re.findall(r'@(Get|Post)Mapping\("([^"]+)"\)', controller))
required = {"Get": ["/", "/edit", "/edit_film/{id}", "/search", "/get_countries", "/aggregate", "/info"],
            "Post": ["/add_genre", "/add_country", "/add_quality", "/add_age_limit", "/add_person", "/delete_person/{id}", "/add_film", "/update_film/{id}", "/delete_film/{id}"]}
for method, paths in required.items():
    for path in paths:
        check((method, path) in routes, f"Missing {method} route {path}")
for path in TEMPLATES.rglob("*.html"):
    html = path.read_text(encoding="utf-8")
    parser = TemplateParser(path.relative_to(ROOT))
    parser.feed(html)
    check(not parser.stack, f"{path.name}: unclosed tags {parser.stack}")
    check('lang="ru"' in html, f"{path.name}: missing Russian language")
    for fragment, name in re.findall(r"~\{([\w/-]+)\s*::\s*(\w+)", html):
        target = TEMPLATES / (fragment + ".html")
        check(target.is_file(), f"{path.name}: missing fragment file {fragment}")
        if target.is_file():
            check(re.search(r'th:fragment="' + name + r'(?:\(|")', target.read_text(encoding="utf-8")), f"Missing fragment {fragment}::{name}")
    for form, inner in re.findall(r'<form[^>]+th:object="\$\{(\w+)\}"[^>]*>(.*?)</form>', html, re.S):
        check(form in form_types, f"{path.name}: unknown model form {form}")
        known = properties(form_types[form]) if form in form_types else set()
        for field in re.findall(r'th:field="\*\{(\w+)\}"', inner):
            check(field in known, f"{path.name}: {form}.{field} is not a DTO property")
    if path.name == "film-fields.html":
        for field in re.findall(r'th:field="\*\{(\w+)\}"', html):
            check(field in form_properties, f"Shared film form field {field} is not in FilmForm")
    for prop in re.findall(r"\$\{f\.(\w+)", html):
        check(prop in film_properties, f"{path.name}: unknown Film property {prop}")

ns = {"m": "http://maven.apache.org/POM/4.0.0"}
pom = ET.parse(ROOT / "pom.xml")
artifacts = {n.text for n in pom.findall(".//m:dependency/m:artifactId", ns)}
for dependency in ("spring-boot-starter-web", "spring-boot-starter-data-jpa", "spring-boot-starter-thymeleaf", "spring-boot-starter-validation", "postgresql"):
    check(dependency in artifacts, f"Missing Maven dependency {dependency}")
check(pom.findtext(".//m:java.version", namespaces=ns) == "21", "Java version must be 21")
check(not any("mysql" in a.lower() or "ucanaccess" in a.lower() for a in artifacts), "Forbidden database dependency")
for name in ("Film", "Genre", "Country", "AgeLimit", "ShootingQuality", "Person"):
    repository = (JAVA / "repository" / (name + "Repository.java")).read_text(encoding="utf-8")
    check(f"JpaRepository<{name},Long>" in repository, f"{name}Repository must extend JpaRepository")

if errors:
    raise SystemExit("\n".join(errors))
print("OK: DTO fields, Film properties, routes, fragments, HTML structure, repositories and Maven dependencies agree.")
