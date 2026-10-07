"use strict";
document.addEventListener("DOMContentLoaded", () => {
 document.querySelectorAll("form[data-confirm]").forEach(form => {
  form.addEventListener("submit", event => {
   if (!window.confirm(form.dataset.confirm)) event.preventDefault();
  });
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
 // Focus the invalid form after server-side validation, without losing user input.
 const errors = document.querySelector(".validation-summary");
 if (errors) { errors.setAttribute("tabindex", "-1"); errors.focus(); }
 const path = window.location.pathname;
 document.querySelectorAll(".site-header nav a").forEach(link => {
  const href = new URL(link.href).pathname;
  if (href === path || (href === "/edit" && path.startsWith("/edit_film/"))) link.setAttribute("aria-current", "page");
 });
});
