# FilmArchive

FilmArchive — учебный информационный портал о кино для пользователей, интересующихся фильмами, актёрами, жанрами, историей кино и кинематографом. Разработчик: **Даниил Сабадыш**. Email: **sabadyshdv23@oiate.ru**, Telegram: **[@Hellgard](https://t.me/Hellgard)**.

## Стек

Java 21, Spring Boot **3.5.13**, Spring MVC, Spring Data JPA / Hibernate, Thymeleaf, PostgreSQL, Maven, HTML5, CSS3, JavaScript. 
## Структура

```text
FilmArchive/
├── pom.xml
├── compose.yaml
├── database/create-database.sql
├── README.md
├── IMPLEMENTATION.md          # структура, изменения и код по порядку задания
├── LAB_CHECKLIST.md           # соответствие требованиям
├── VERIFICATION.md            # результаты и ограничения проверки
├── tools/
│   ├── check_contracts.py
│   └── check_rendered_html.py
└── src/
    ├── main/
    │   ├── java/com/hellgardd/filmarchive/
    │   │   ├── FilmArchiveApplication.java
    │   │   ├── config/         # AppConfig, DataInitializer, SiteModelAdvice
    │   │   ├── controller/     # Catalog, StaticPage, Service, SiteSearch, SEO, ошибки
    │   │   ├── dto/            # существующие формы и новые record-результаты
    │   │   ├── entity/         # Film, Genre, Country, AgeLimit, ShootingQuality, Person
    │   │   ├── exception/      # ошибки формы, отсутствующих записей и внешних API
    │   │   ├── repository/     # существующие JPA repository
    │   │   └── service/        # существующие сервисы + поиск сайта, инструменты, API, SEO
    │   └── resources/
    │       ├── application.properties
    │       ├── messages.properties
    │       ├── static/
    │       │   ├── css/style.css
    │       │   ├── js/main.js
    │       │   └── robots.txt
    │       └── templates/
    │           ├── fragments/ # header, footer, messages, film-fields, film-card
    │           ├── index.html, about.html, history.html, genres-info.html
    │           ├── cinema-technologies.html, ratings-info.html, formats.html
    │           ├── recommendations.html, links.html, contacts.html
    │           ├── catalog.html, site-search.html, services.html
    │           ├── edit.html, edit-film.html, search.html, aggregate.html
    │           ├── info.html, error.html
    │           └── services/
    │               ├── random-film.html, watch-time.html, profitability.html
    │               └── age-check.html, currency.html, weather.html
    └── test/java/com/hellgardd/filmarchive/
        ├── FilmFormTest.java, FilmServiceTest.java, CatalogIT.java
        └── PortalServiceTest.java, ExternalServicesTest.java, PortalWebTest.java
```

Контроллеры принимают HTTP-запросы и заполняют модель. Расчёты, выбор фильма, поиск, вызовы API и генерация SEO-данных находятся в service. Доступ к PostgreSQL выполняют существующие repository. FilmService, SearchService, StatisticsService и GlobalExceptionHandler расширены или повторно используются без второго набора аналогичной логики.

## PostgreSQL и запуск

Нужны JDK 21, Maven и запущенный PostgreSQL. Создайте БД один раз:

```shell
psql -h localhost -p 5432 -U postgres -d postgres -f database/create-database.sql
```

Файл выполняет создание cinema_db. Можно создать её вручную в psql или Query Tool pgAdmin вне транзакции:

```sql
CREATE DATABASE cinema_db WITH ENCODING = 'UTF8' TEMPLATE = template0;
```

Альтернатива локальному серверу — PostgreSQL из compose.yaml:

```shell
docker compose up -d
```

Проверьте порт, имя пользователя и пароль в выбранном способе запуска. **Пароль БД обязателен через DB_PASSWORD**; в Java и properties пароль не хранится. Для PowerShell:

```powershell
$env:DB_URL='jdbc:postgresql://localhost:5432/cinema_db'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='ваш_пароль'
mvn clean verify
mvn spring-boot:run
```

Для Linux/macOS используйте export DB_PASSWORD='ваш_пароль' и аналогично задайте DB_URL, DB_USERNAME. Параметры можно указать в Run Configuration IntelliJ IDEA. Откройте http://localhost:8080/. Собранное приложение запускается командой java -jar target/film-archive-1.0.0.jar.

Hibernate создаёт/обновляет таблицы через ddl-auto=update. Open Session in View отключён; FilmRepository загружает отношения через EntityGraph. Таблицы и связи сохранены: Film → Genre/Country/AgeLimit/ShootingQuality и Film ↔ Person через film_people. Удаление фильма не удаляет актёров; удаление актёра очищает связи и сохраняет фильмы.

На пустой БД загрузчик создаёт учебные справочники, актёров и фильмы. SEED_DATA=false отключает начальные данные. SHOW_SQL=true включает SQL в журнале. Валидация каталога сохранена: длительность 1–300 минут, обязательные справочники и актёры, дата не позже сегодняшней по app.zone, проверка денежных значений и уникальных названий.

## Десять статических страниц

У каждой свой URL и отдельный Thymeleaf-шаблон с информационным содержанием. Статические материалы не запрашивают данные каталога.

| URL | Страница / шаблон |
|---|---|
| / | Главная — index.html |
| /about | О проекте — about.html |
| /history | История кинематографа — history.html |
| /genres-info | Основные жанры кино — genres-info.html |
| /cinema-technologies | Технологии — cinema-technologies.html |
| /ratings-info | Возрастные рейтинги — ratings-info.html |
| /formats | Форматы видео — formats.html |
| /recommendations | Как выбрать фильм — recommendations.html |
| /links | Восемь внешних тематических ресурсов — links.html |
| /contacts | Контакты — contacts.html |

## Десять сервисов

| URL | Назначение |
|---|---|
| /catalog | Публичный каталог PostgreSQL, без административных форм |
| /search | Существующий поиск фильмов по жанру и стране |
| /site-search?query=история | Поиск страниц портала отдельно от БД |
| /aggregate | Существующая статистика фильмов, актёров и справочников |
| /services/random-film | Случайный фильм из PostgreSQL через FilmService |
| /services/watch-time | Количество × средняя длительность, минуты и часы |
| /services/profitability | Сборы / бюджет и сборы − бюджет |
| /services/age-check | Сравнение возраста с рейтингом 0/6/12/16/18 |
| /services/currency | Конвертация USD/EUR/RUB/GBP/CNY/JPY через Frankfurter |
| /services/weather | Город, температура, ощущаемая температура и описание через Open-Meteo |

/services показывает карточки всех десяти сервисов. Калькуляторы работают на сервере без JavaScript; общий main.js дополнительно показывает предварительные расчёты времени и окупаемости. Денежные расчёты сервера выполняются в BigDecimal. Учебная прибыль не учитывает маркетинг и долю кинотеатров. Проверка возраста сравнивает числа из выбранного рейтинга.

Каталог показывает название, жанр, страну, длительность, дату, рейтинг, качество, актёров, бюджет и сборы. Текущая Film не содержит отдельного поля валюты: суммы каталога отображаются без выдуманной валютной маркировки.

## Поиск и общие элементы

fragments/header.html содержит меню, выпадающие разделы через details/summary и форму GET /site-search?query=.... SiteSearchService хранит публичные страницы и ключевые слова, ищет по заголовку, описанию и ключевым словам без учёта регистра, нормализует ё/е и пробелы. Все слова многословного запроса должны встречаться в записи. Пустой запрос показывает подсказку; отсутствие результатов — «По вашему запросу ничего не найдено.»

Поиск фильмов /search использует существующий SearchService, PostgreSQL и параметры genre, country с числовыми id. /get_countries?genre={id} возвращает JSON стран. При отключённом JavaScript сначала отправьте поиск по жанру.

fragments/footer.html присутствует на всех страницах, включая управление и ошибки. Он содержит название, назначение, разработчика, email, Telegram и © 2026 FilmArchive. Контакты можно переопределить через SITE_CONTACT_EMAIL и SITE_CONTACT_TELEGRAM (имя без @). Стили и JavaScript общие, без CDN.

## Существующее управление

/edit — фильмы, справочники, актёры и формы; /edit_film/{id} — редактирование фильма; /info — сохранённая информация о системе. POST-маршруты сохранены: /add_genre, /add_country, /add_quality, /add_age_limit, /add_person, /add_film, /update_film/{id}, /delete_film/{id}, /delete_person/{id}.

## Внешние API и ошибки

[Frankfurter v2](https://frankfurter.dev/) возвращает курс и дату для валютной пары; CurrencyService умножает сумму на курс. [Open-Meteo](https://open-meteo.com/en/docs) и его [Geocoding API](https://open-meteo.com/en/docs/geocoding-api) ищут город и возвращают текущие погодные данные. Выбранные публичные endpoints не требуют API-ключей. Адреса настраиваются через CURRENCY_API_URL, WEATHER_API_URL и WEATHER_GEOCODING_URL. Ключи в код не добавлялись. При замене поставщика ключ следует передавать через переменную окружения и соответствующее свойство.

Для каждого HTTP-запроса заданы connect timeout 3 секунды и read timeout 8 секунд. Погода использует два последовательных запроса. При недоступности или некорректном ответе API GlobalExceptionHandler возвращает 503 и «Сервис временно недоступен». Отсутствие города и неверные формы обрабатываются как ошибки входных данных. Для неизвестных URL — 404; ошибок БД — 503 с сообщением о PostgreSQL; stack trace остаётся в журнале сервера.

## Проверки и W3C

```shell
mvn clean verify
python tools/check_contracts.py
python tools/check_rendered_html.py
```

PortalWebTest рендерит реальные Thymeleaf-шаблоны через Spring MVC с mock-сервисами каталога и сохраняет страницы в target/rendered-html. Проверяются все публичные маршруты, общие элементы, карточка фильма, расчёты, поиск, 404 и безопасная ошибка API. ExternalServicesTest проверяет реальные HTTP-клиенты на локальном тестовом API, без зависимости от интернета.

Полная проверка существующих CRUD-операций с отдельным PostgreSQL в Docker:

```shell
mvn -Pintegration verify
```

Нужен запущенный Docker. CatalogIT использует Testcontainers; production-БД для теста не нужна.

Локальный HTML-скрипт проверяет структуру, id, label, метаданные, обработку Thymeleaf и внешние ссылки, но не заменяет W3C. Для [W3C Nu HTML Checker](https://validator.w3.org/nu/) используйте HTML из target/rendered-html либо сохранённый исходный код страницы из работающего браузера; файлы templates с th:* передавать как готовый HTML нельзя. На публичном хостинге можно проверять по URL. Проверяйте также заполненные каталог и поиск, формы управления, страницы с результатами расчётов и ошибки: вложенность, уникальные id, label, атрибуты, закрытие тегов и отсутствие сообщений Error. Подробные результаты — VERIFICATION.md.

## robots.txt, sitemap и публикация

Правила robots лежат в src/main/resources/static/robots.txt. GET /robots.txt читает этот файл и добавляет строку Sitemap с текущим SITE_BASE_URL. GET /sitemap.xml генерирует XML из реестра публичных страниц SiteSearchService; административные страницы и /site-search туда не попадают. Единственный реестр страниц используется поиском и sitemap.

Перед публикацией задайте SITE_BASE_URL реальным origin, например https://filmarchive.example (без пути, query и fragment). Этот адрес используется в sitemap и canonical. Подключите PostgreSQL хостинга через DB_URL/DB_USERNAME/DB_PASSWORD, обеспечьте HTTPS и исходящий доступ к API. Для HTTPS PostgreSQL при необходимости добавьте параметры sslmode к DB_URL согласно настройкам хостинга.

После публикации проверьте публичную доступность /robots.txt, /sitemap.xml и основных страниц, убедитесь, что ссылки используют ваш домен, выполните W3C-проверку и отправьте sitemap в панели поисковых систем. Localhost не доступен поисковым роботам. Каждая страница имеет title и meta description; административные страницы, результаты поиска и URL с параметрами помечены noindex. robots запрещает обход административных URL, но не ограничивает HTTP-доступ. До публичного размещения ограничьте доступ к административным GET/POST-маршрутам средствами хостинга или аутентификацией.
