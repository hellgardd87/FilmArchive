"use strict";
document.addEventListener("DOMContentLoaded", () => {
 document.querySelectorAll("form[data-confirm]").forEach(form => {
  form.addEventListener("submit", event => {
   if (!window.confirm(form.dataset.confirm)) event.preventDefault();
  });
 });
 // Focus the invalid form after server-side validation, without losing user input.
 const errors = document.querySelector(".validation-summary");
 if (errors) { errors.setAttribute("tabindex", "-1"); errors.focus(); }
 const path = window.location.pathname;
 document.querySelectorAll(".site-header nav a").forEach(link => {
  const href = new URL(link.href).pathname;
  if (href === path || (href === "/edit" && path.startsWith("/edit_film/"))) {
   link.setAttribute("aria-current", "page");
   const dropdown = link.closest(".nav-dropdown");
   if (dropdown) dropdown.classList.add("is-current");
  }
 });

 const genre = document.getElementById("search-genre");
 const country = document.getElementById("search-country");
 const form = document.getElementById("search-form");
 const status = document.getElementById("search-status");
 const submit = document.getElementById("search-submit");
 if (!genre || !country || !form) return;
 let request;
 genre.addEventListener("change", async () => {
  if (request) request.abort();
  const current = new AbortController();
  request = current;
  country.replaceChildren(new Option("Все страны жанра", ""));
  country.disabled = true;
  status.textContent = "";
  submit.disabled = false;
  if (!genre.value) return;
  submit.disabled = true;
  status.textContent = "Загрузка стран…";
  try {
   const url = new URL(form.dataset.countriesUrl, window.location.origin);
   url.searchParams.set("genre", genre.value);
   const response = await fetch(url, { signal: current.signal, headers: { Accept: "application/json" } });
   if (!response.ok) throw new Error("Не удалось загрузить страны");
   const countries = await response.json();
   if (!Array.isArray(countries)) throw new Error("Неверный ответ сервера");
   if (request !== current) return;
   countries.forEach(item => country.add(new Option(item.title, item.id)));
   country.disabled = countries.length === 0;
   status.textContent = countries.length ? "Можно уточнить страну или искать по всему жанру." : "В этом жанре пока нет фильмов.";
  } catch (error) {
   if (error.name !== "AbortError" && request === current) status.textContent = "Не удалось загрузить страны. Нажмите «Найти», чтобы открыть фильмы жанра.";
  } finally {
   if (request === current) submit.disabled = false;
  }
 });

});

document.addEventListener("DOMContentLoaded", () => {
 const header = document.querySelector(".site-header");
 if (header) {
  const toggle = header.querySelector(".menu-toggle");
  const panel = header.querySelector(".header-panel");
  const menus = [...header.querySelectorAll(".nav-dropdown")];
  const compact = window.matchMedia("(max-width: 1023px)");
  const closeDropdowns = () => menus.forEach(menu => { menu.open = false; });
  if (toggle && panel) {
   const setOpen = open => {
    header.classList.toggle("is-menu-open", open);
    toggle.setAttribute("aria-expanded", String(open));
    toggle.setAttribute("aria-label", open ? "Закрыть меню" : "Открыть меню");
    if (!open) closeDropdowns();
   };
   toggle.hidden = false;
   header.classList.add("is-enhanced");
   toggle.addEventListener("click", () => setOpen(toggle.getAttribute("aria-expanded") !== "true"));
   compact.addEventListener("change", () => {
    const focused = document.activeElement;
    const restoreFocus = compact.matches && panel.contains(focused);
    setOpen(false);
    if (restoreFocus) toggle.focus();
    else if (!compact.matches && focused === toggle) header.querySelector(".brand").focus();
   });
   document.addEventListener("keydown", event => {
    if (event.key !== "Escape") return;
    const openMenu = menus.find(menu => menu.open);
    if (openMenu) {
     openMenu.open = false;
     if (openMenu.contains(document.activeElement)) openMenu.querySelector("summary").focus();
    } else if (header.classList.contains("is-menu-open")) {
     setOpen(false);
     if (header.contains(document.activeElement)) toggle.focus();
    }
   });
   document.addEventListener("click", event => {
    if (!header.contains(event.target)) setOpen(false);
    else menus.forEach(menu => { if (!menu.contains(event.target)) menu.open = false; });
   });
   header.addEventListener("focusout", event => {
    if (event.relatedTarget && !header.contains(event.relatedTarget)) setOpen(false);
   });
  }
  menus.forEach(menu => {
   menu.addEventListener("toggle", () => {
    if (menu.open) menus.forEach(other => { if (other !== menu) other.open = false; });
   });
   menu.addEventListener("focusout", event => {
    if (event.relatedTarget && !menu.contains(event.relatedTarget)) menu.open = false;
   });
  });
 }
 document.querySelectorAll("form[data-calculator]").forEach(form => {
  form.addEventListener("input", () => {
   const output = form.querySelector("output");
   const valid = [...form.querySelectorAll("input")].every(input => input.value !== "" && input.validity.valid);
   if (!valid) { output.textContent = ""; return; }
   const data = new FormData(form);
   if (form.dataset.calculator === "watch-time") {
    const minutes = Number(data.get("count")) * Number(data.get("duration"));
    output.textContent = "Предварительно: " + minutes + " мин, " + Math.floor(minutes / 60) + " ч " + minutes % 60 + " мин.";
   } else {
    const budget = Number(data.get("budget"));
    const boxOffice = Number(data.get("boxOffice"));
    output.textContent = "Предварительно: коэффициент " + (boxOffice / budget).toFixed(4) + ", разность " + (boxOffice - budget).toFixed(2) + ".";
   }
  });
 });
});

// Search suggestions reuse the same server-side search as the results page.
document.addEventListener("DOMContentLoaded", () => {
 const form = document.querySelector(".site-search-form");
 if (!form) return;
 const input = form.querySelector("input[type=search]");
 const popup = form.querySelector(".search-suggestions");
 const list = form.querySelector("#global-suggestions");
 const message = form.querySelector(".search-suggestions-message");
 const status = form.querySelector(".search-suggestions-status");
 if (!input || !popup || !list || !message || !status) return;
 input.setAttribute("role", "combobox");
 input.setAttribute("aria-autocomplete", "list");
 input.setAttribute("aria-controls", list.id);
 input.setAttribute("aria-expanded", "false");
 input.setAttribute("autocomplete", "off");
 let timer, request, revision = 0, active = -1;
 const select = index => {
  const options = [...list.children];
  active = index;
  options.forEach((option, i) => option.setAttribute("aria-selected", String(i === index)));
  if (options[index]) {
   input.setAttribute("aria-activedescendant", options[index].id);
   options[index].scrollIntoView({ block: "nearest" });
  } else input.removeAttribute("aria-activedescendant");
 };
 const close = () => {
  clearTimeout(timer);
  timer = undefined;
  if (request) request.abort();
  request = undefined;
  revision++;
  popup.hidden = true;
  input.setAttribute("aria-expanded", "false");
  select(-1);
 };
 const show = text => {
  message.textContent = text;
  message.hidden = !text;
  popup.hidden = false;
  input.setAttribute("aria-expanded", "true");
  status.textContent = text;
 };
 const schedule = () => {
  close();
  list.replaceChildren();
  status.textContent = "";
  const query = input.value.trim();
  if (!query || !input.validity.valid) return;
  const currentRevision = revision;
  timer = setTimeout(async () => {
   timer = undefined;
   if (document.activeElement !== input) return;
   const current = new AbortController();
   request = current;
   show("Поиск…");
   try {
    const url = new URL(form.action, window.location.href);
    url.searchParams.set("suggest", "true");
    url.searchParams.set("query", query);
    const response = await fetch(url, { signal: current.signal, headers: { Accept: "application/json" } });
    if (!response.ok) throw new Error("Search unavailable");
    const pages = await response.json();
    if (!Array.isArray(pages)) throw new Error("Invalid search response");
    if (revision !== currentRevision) return;
    const prefix = new URL(form.action).pathname.replace(/\/site-search$/, "");
    pages.slice(0, 8).forEach((page, index) => {
     if (typeof page.url !== "string" || !page.url.startsWith("/") || page.url.startsWith("//")) return;
     const link = document.createElement("a");
     link.href = prefix + page.url;
     link.id = "global-suggestion-" + index;
     link.className = "search-suggestion";
     link.setAttribute("role", "option");
     link.setAttribute("aria-selected", "false");
     link.tabIndex = -1;
     const title = document.createElement("span");
     title.className = "search-suggestion-title";
     title.textContent = page.title;
     const description = document.createElement("span");
     description.className = "search-suggestion-description";
     description.textContent = page.description;
     link.append(title, description);
     list.append(link);
    });
    show(list.children.length ? "" : "Совпадений не найдено.");
    if (list.children.length) status.textContent = "Найдено совпадений: " + list.children.length + ". Используйте стрелки для выбора.";
   } catch (error) {
    if (error.name !== "AbortError" && revision === currentRevision) show("Не удалось загрузить подсказки. Нажмите Enter для поиска.");
   } finally {
    if (request === current) request = undefined;
   }
  }, 200);
 };
 input.addEventListener("input", event => { if (!event.isComposing) schedule(); });
 input.addEventListener("compositionstart", close);
 input.addEventListener("compositionend", schedule);
 input.addEventListener("focus", schedule);
 input.addEventListener("keydown", event => {
  if (event.isComposing) return;
  if (event.key === "Escape" && (!popup.hidden || timer || request)) {
   event.preventDefault();
   event.stopPropagation();
   close();
  } else if (!popup.hidden && list.children.length && (event.key === "ArrowDown" || event.key === "ArrowUp")) {
   event.preventDefault();
   const count = list.children.length;
   select(event.key === "ArrowDown" ? (active + 1) % count : (active < 0 ? count - 1 : (active - 1 + count) % count));
  } else if (event.key === "Enter" && !popup.hidden && active >= 0) {
   event.preventDefault();
   window.location.assign(list.children[active].href);
  } else if (event.key === "Tab") close();
 });
 // Keep focus on the input until a mouse/touch click follows a suggestion link.
 popup.addEventListener("pointerdown", event => { if (event.target.closest("a")) event.preventDefault(); });
 input.addEventListener("blur", close);
 form.addEventListener("submit", close);
});
