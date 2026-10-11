# Соответствие требованиям лабораторной работы

| Требование | Статус и реализация |
|---|---|
| Минимум 10 статических страниц | Выполнено: index, about, history, genres-info, cinema-technologies, ratings-info, formats, recommendations, links, contacts в src/main/resources/templates; у каждой отдельный GET URL. |
| Минимум 10 сервисов | Выполнено: каталог, поиск фильмов, поиск сайта, статистика, случайный фильм, время просмотра, окупаемость, проверка возраста, валюта, погода. Карточки — /services. |
| Единое меню на каждой странице | Выполнено: fragments/header.html :: navigation; выпадающие информационные страницы и сервисы; сохранённые административные страницы и ошибки тоже используют fragment. |
| Поиск на каждой странице | Выполнено: форма query в header, GET /site-search. Это отдельный поиск страниц, а не поиск фильмов в PostgreSQL. |
| Общий footer | Выполнено: fragments/footer.html :: footer на всех страницах. |
| Назначение сайта | Выполнено: главная, /about, /contacts и footer. |
| Разработчик и контакты | Выполнено: Даниил Сабадыш, sabadyshdv23@oiate.ru, @Hellgard — в footer и /contacts. |
| Название проекта и год | Выполнено: FilmArchive, © 2026 FilmArchive в footer. |
| Внешние тематические ссылки | Выполнено: 8 ресурсов на /links, понятные тексты, target=_blank, rel=noopener noreferrer. |
| Прикладной сервис с БД | Выполнено: каталог /catalog и существующее управление /edit; PostgreSQL, FilmService и JPA repository. |
| Существующие сущности и связи | Сохранены: Film, Genre, Country, AgeLimit, ShootingQuality, Person и Film ↔ Person. |
| Архитектура Spring Boot | Сохранена: controller/service/repository/entity/dto/exception/config; бизнес-логика вне контроллеров. |
| Погода и валюта | Выполнено: отдельные WeatherService и CurrencyService, API URL из properties/окружения, таймауты, DTO результатов. Выбранные API не требуют ключей. |
| Понятные ошибки | Выполнено: GlobalExceptionHandler; 404 для отсутствующих страниц, 400 для неверных данных, 503 для БД/API; без stack trace в HTML. |
| W3C | Подготовлено: семантический HTML5, lang, charset, viewport, title, description, labels, типы кнопок, уникальные id. Проверены 25 отрендеренных страниц локальным HTML-скриптом. Внешняя W3C-проверка не выполнялась: загрузите готовый HTML из target/rendered-html в Nu HTML Checker или проверьте публичный URL. |
| robots.txt | Выполнено: static/robots.txt хранит правила; GET /robots.txt добавляет Sitemap с настроенным доменом через SeoService/SeoController. |
| sitemap.xml | Выполнено: GET /sitemap.xml генерируется из публичного реестра SiteSearchService, без административных страниц и результатов поиска сайта. |
| Подготовка к индексации | Выполнено: title, description, canonical, noindex для управления/результатов, robots и sitemap. После публикации задайте SITE_BASE_URL, включите HTTPS, проверьте доступность страниц и передайте sitemap поисковикам. Localhost не индексируется. |
| README | Выполнено: назначение, стек, структура, БД, запуск, URL, сервисы, поиск, проверки и публикация. |

## Десять сервисов и URL

1. Каталог — /catalog.
2. Поиск фильмов — /search.
3. Поиск по сайту — /site-search.
4. Статистика — /aggregate.
5. Случайный фильм — /services/random-film.
6. Время просмотра — /services/watch-time.
7. Окупаемость — /services/profitability.
8. Возраст — /services/age-check.
9. Валюта — /services/currency.
10. Погода — /services/weather.

## Что ещё проверить перед сдачей и публикацией

- Выполнить mvn clean verify на JDK 21 с доступом к Maven Central.
- Запустить PostgreSQL и проверить приложение со своими DB_URL/DB_USERNAME/DB_PASSWORD.
- Выполнить mvn -Pintegration verify при запущенном Docker для существующего полного CRUD-сценария.
- Проверить готовые страницы W3C Nu HTML Checker, включая заполненные формы и результаты.
- После публикации указать реальный SITE_BASE_URL и проверить /robots.txt, /sitemap.xml и canonical.
- Ограничить доступ к административным операциям перед публичным размещением: robots регулирует обход, а не доступ.
