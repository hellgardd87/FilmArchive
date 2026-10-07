# Каталог фильмов

## Структура проекта

```text
FilmArchive/
├── pom.xml
├── compose.yaml
├── database/create-database.sql
├── README.md
├── VERIFICATION.md
├── tools/check_contracts.py
└── src/
    ├── main/
    │   ├── java/com/hellgardd/filmarchive/
    │   │   ├── FilmArchiveApplication.java
    │   │   ├── config/
    │   │   │   ├── AppConfig.java
    │   │   │   └── DataInitializer.java
    │   │   ├── controller/
    │   │   │   ├── CatalogController.java
    │   │   │   └── GlobalExceptionHandler.java
    │   │   ├── dto/
    │   │   │   ├── FilmForm.java
    │   │   │   ├── ReferenceForm.java
    │   │   │   ├── AgeLimitForm.java
    │   │   │   ├── QualityForm.java
    │   │   │   ├── PersonForm.java
    │   │   │   ├── CountryOption.java
    │   │   │   └── Statistics.java
    │   │   ├── entity/
    │   │   │   ├── Film.java
    │   │   │   ├── Genre.java
    │   │   │   ├── Country.java
    │   │   │   ├── AgeLimit.java
    │   │   │   ├── ShootingQuality.java
    │   │   │   └── Person.java
    │   │   ├── exception/
    │   │   │   ├── NotFoundException.java
    │   │   │   └── FormValidationException.java
    │   │   ├── repository/
    │   │   │   ├── FilmRepository.java
    │   │   │   ├── GenreRepository.java
    │   │   │   ├── CountryRepository.java
    │   │   │   ├── AgeLimitRepository.java
    │   │   │   ├── ShootingQualityRepository.java
    │   │   │   └── PersonRepository.java
    │   │   └── service/
    │   │       ├── FilmService.java
    │   │       ├── GenreService.java
    │   │       ├── CountryService.java
    │   │       ├── PersonService.java
    │   │       ├── ReferenceService.java
    │   │       ├── SearchService.java
    │   │       ├── StatisticsService.java
    │   │       ├── DatabaseInitializationService.java
    │   │       └── TextNormalizer.java
    │   └── resources/
    │       ├── application.properties
    │       ├── messages.properties
    │       ├── templates/
    │       │   ├── index.html
    │       │   ├── edit.html
    │       │   ├── edit-film.html
    │       │   ├── search.html
    │       │   ├── aggregate.html
    │       │   ├── info.html
    │       │   ├── error.html
    │       │   └── fragments/
    │       │       ├── header.html
    │       │       ├── messages.html
    │       │       ├── footer.html
    │       │       └── film-fields.html
    │       └── static/
    │           ├── css/style.css
    │           └── js/main.js
    └── test/java/com/hellgardd/filmarchive/
        ├── FilmFormTest.java
        ├── FilmServiceTest.java
        └── CatalogIT.java
```

## Стек и архитектура

Java 21, Spring Boot 3.5.16, Spring MVC, Spring Data JPA/Hibernate, Thymeleaf, Jakarta Validation, PostgreSQL, Maven, HTML5, CSS3, JavaScript. CSS и JavaScript поставляются локально; внешний CDN для интерфейса не нужен.

Controller принимает HTTP-запросы и связывает формы с DTO. Service выполняет проверки, поиск и транзакционные изменения. Repository работает с PostgreSQL через JPA. Сущности находятся в entity; настройки и загрузчик — в config. Все зависимости передаются через конструкторы. Open Session in View отключён; необходимые отношения загружаются через EntityGraph.

## Файлы реализации

1. [pom.xml](pom.xml) — Maven, зависимости, сборка исполняемого JAR и профиль интеграционных тестов.
2. [application.properties](src/main/resources/application.properties) — подключение PostgreSQL, Hibernate, кодировка и конфигурация загрузчика.
3. [FilmArchiveApplication.java](src/main/java/com/hellgardd/filmarchive/FilmArchiveApplication.java) — запуск Spring Boot.
4. [entity](src/main/java/com/hellgardd/filmarchive/entity) — Film, Genre, Country, AgeLimit, ShootingQuality, Person.
5. [repository](src/main/java/com/hellgardd/filmarchive/repository) — шесть JpaRepository, проверка дубликатов, поиск и подсчёт связей.
6. [service](src/main/java/com/hellgardd/filmarchive/service) — бизнес-логика, транзакции, статистика и инициализация.
7. [CatalogController.java](src/main/java/com/hellgardd/filmarchive/controller/CatalogController.java) — все запрошенные маршруты.
8. [dto](src/main/java/com/hellgardd/filmarchive/dto) — формы с валидацией, JSON-представление стран, статистика.
9. [GlobalExceptionHandler.java](src/main/java/com/hellgardd/filmarchive/controller/GlobalExceptionHandler.java) — безопасные сообщения об ошибках.
10. [templates](src/main/resources/templates) — семь основных страниц и общие фрагменты.
11. [style.css](src/main/resources/static/css/style.css) — адаптивное оформление.
12. [main.js](src/main/resources/static/js/main.js) — подтверждение удаления и динамическая загрузка стран.
13. [DatabaseInitializationService.java](src/main/java/com/hellgardd/filmarchive/service/DatabaseInitializationService.java) — индексы PostgreSQL и демонстрационные данные.

## База данных и связи

Film содержит title, description, duration, budget, totalBoxOffice, releaseDate, genre, country, ageLimit, shootingQuality, actors. Формы передают идентификаторы справочников: genreId, countryId, ageLimitId, shootingQualityId, actorIds. Сервис преобразует их в сущности и проверяет существование всех выбранных записей.

Денежные поля — BigDecimal / NUMERIC(15,2). Дата — LocalDate / DATE. Идентификаторы генерируются через IDENTITY. Обязательные ссылки Film — ManyToOne с внешними ключами. Связь Film–Person — ManyToMany через film_people(film_id, person_id), с уникальной парой и внешними ключами. CascadeType.REMOVE отсутствует.

При удалении фильма сервис очищает actors, выполняет flush, затем удаляет Film. При удалении актёра сервис удаляет его из actors всех связанных фильмов, выполняет flush и удаляет Person. Всё происходит в одной транзакции. Актёр не удаляется вместе с фильмом; фильм не удаляется вместе с актёром.

Наименование и код нормализуются: обрезаются края, схлопываются пробелы, применяется Unicode NFKC. Дубликаты проверяются без учёта регистра. Уникальные индексы lower(...) на стороне PostgreSQL защищают от конкурентного добавления одинаковых значений. Первоначальная схема создаётся Hibernate через ddl-auto=update, индексы — через EntityManager в транзакционном загрузчике. Пользователь БД должен иметь права на создание таблиц и индексов.

Удаление единственного актёра может оставить существующий фильм без состава. Это допустимо для операции удаления актёра; при следующем добавлении или сохранении фильма необходимо выбрать хотя бы одного актёра.

## HTTP-маршруты

| Метод | Маршрут | Назначение |
|---|---|---|
| GET | / | Главная |
| GET | /edit | Фильмы, справочники, актёры, формы добавления |
| POST | /add_genre | title, code |
| POST | /add_country | title, code |
| POST | /add_quality | type |
| POST | /add_age_limit | category, code |
| POST | /add_person | fullName |
| POST | /delete_person/{id} | Удаление актёра и связей |
| POST | /add_film | Создание фильма |
| GET | /edit_film/{id} | Заполненная форма фильма |
| POST | /update_film/{id} | Сохранение фильма |
| POST | /delete_film/{id} | Удаление фильма и связей |
| GET | /search?genre={id}&country={id} | Поиск |
| GET | /get_countries?genre={id} | JSON стран для жанра |
| GET | /aggregate | Семь показателей статистики |
| GET | /info | Информация о системе |

genre и country в поиске — числовые идентификаторы, совпадающие с value списков. country необязателен. Пример JSON: [{"id":1,"title":"США"}]. До выбора жанра результаты не показываются. При отключённом JavaScript сначала отправьте поиск по жанру: сервер отобразит соответствующие страны.

## Валидация и уведомления

Название фильма обязательно и уникально; длительность 1–300 минут включительно; 0 < бюджет < 600000000; 0 < сборы < 3000000000; денежные суммы имеют максимум два знака после запятой. Дата обязательна и не позже текущей даты по app.zone (по умолчанию Europe/Moscow). Справочники и хотя бы один актёр обязательны.

Формы справочников проверяют обязательность, длину и дубликаты названия/кода. Успешное действие перенаправляет на /edit и показывает flash-сообщение через RedirectAttributes. При ошибке валидации страница отображается повторно с введёнными значениями, перечнем ошибок и сообщением warning. Фрагмент уведомлений и CSS поддерживают success, warning и error.

@ControllerAdvice обрабатывает несуществующие записи (404), неверные значения (400), конфликты ограничений БД (409), недоступность БД (503) и неожиданные ошибки (500). Для /get_countries ошибки возвращаются JSON. Остальные ошибки показываются через error.html. Stack trace остаётся только в журнале сервера. Ошибки подключения при старте выводятся в журнал: HTTP-интерфейс ещё не запущен.

## Установка PostgreSQL

1. Скачайте Windows-установщик с [официальной страницы PostgreSQL](https://www.postgresql.org/download/windows/). Для Linux/macOS выберите свой пакет на [странице загрузок](https://www.postgresql.org/download/).
2. Установите сервер и средства командной строки; pgAdmin можно установить для удобства.
3. Оставьте порт 5432 и задайте пароль пользователя postgres. Запомните этот пароль.
4. Убедитесь, что служба PostgreSQL запущена. В Windows используйте «Службы».
5. Если psql отсутствует в PATH, вызывайте его по полному пути, например & 'D:\PostgreSQL\bin\psql.exe'.

## Создание cinema_db

Подключитесь к служебной базе postgres:

```shell
psql -h localhost -p 5432 -U postgres -d postgres
```

Введите установленный при установке пароль, затем выполните:

```sql
CREATE DATABASE cinema_db WITH ENCODING = 'UTF8' TEMPLATE = template0;
```

Выйдите командой \q. Альтернатива — выполнить файл:

```shell
psql -h localhost -p 5432 -U postgres -d postgres -f database/create-database.sql
```

В pgAdmin подключитесь к серверу, откройте Query Tool для базы postgres, выполните SQL создания базы и обновите дерево баз. Команда выполняется один раз и вне транзакции. Таблицы вручную создавать не требуется. Описание команды: [PostgreSQL CREATE DATABASE](https://www.postgresql.org/docs/current/sql-createdatabase.html).

При наличии Docker вместо локальной установки можно запустить PostgreSQL:

```shell
docker compose up -d
```

compose.yaml создаёт cinema_db и сохраняет данные в Docker volume. Если порт 5432 уже занят локальным PostgreSQL, используйте локальный сервер или измените проброс порта и DB_URL. В проекте используется только PostgreSQL; Docker — альтернативный способ запустить тот же сервер.

## Параметры подключения

Файл src/main/resources/application.properties:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/cinema_db}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
spring.jpa.hibernate.ddl-auto=update
```

Замените password своим паролем в properties либо задайте переменные среды. Java-классы не содержат учётные данные.

PowerShell (в той же консоли, где запускается приложение):

```powershell
$env:DB_URL='jdbc:postgresql://localhost:5432/cinema_db'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='ваш_пароль'
```

Linux/macOS:

```shell
export DB_URL=jdbc:postgresql://localhost:5432/cinema_db
export DB_USERNAME=postgres
export DB_PASSWORD='ваш_пароль'
```

SHOW_SQL=true включает SQL в журнале. SEED_DATA=false отключает демонстрационные данные. Настройки можно также передать в Run Configuration IntelliJ IDEA.

## Java, Maven, сборка и запуск

Установите JDK 21 и Maven 3.6.3 или новее. Укажите JAVA_HOME на JDK 21, добавьте Java bin и Maven bin в PATH. Spring Boot 3.5 поддерживает Java 21; требования к Maven описаны в [официальной документации](https://docs.spring.io/spring-boot/3.5/system-requirements.html).

Проверьте версии:

```shell
java -version
mvn -version
```

Из каталога с pom.xml выполните:

```shell
mvn clean verify
```

Запуск через Spring Boot:

```shell
mvn spring-boot:run
```

Или запуск собранного приложения:

```shell
java -jar target/film-archive-1.0.0.jar
```

Откройте **http://localhost:8080/**. Для остановки нажмите Ctrl+C.

В IntelliJ IDEA откройте pom.xml как проект, выберите Project SDK 21 и Maven Runner JDK 21, дождитесь загрузки Maven-зависимостей и запустите FilmArchiveApplication. PostgreSQL должен быть запущен, cinema_db создана, пароль указан.

## Демонстрационные данные

При первом запуске на полностью пустой базе автоматически создаются:

- 4 жанра: Фантастика, Комедия, Драма, Боевик;
- 4 страны: США, Великобритания, Франция, Германия;
- 5 ограничений: 0+, 6+, 12+, 16+, 18+;
- 3 качества: HD, Full HD, 4K;
- 8 актёров;
- 4 фильма: Матрица, Интерстеллар, Форрест Гамп, Приключения Паддингтона 2;
- 8 связей фильм–актёр.

Числа бюджета и сборов служат демонстрационными значениями. На последующих стартах записи не добавляются повторно. Любая существующая запись в каталоге отключает автоматическое заполнение: загрузчик не восстанавливает удалённые данные и не дополняет частично заполненную базу.

## Проверка CRUD

1. Откройте /edit, проверьте списки фильмов и всех справочников.
2. Добавьте жанр и страну с уникальными названиями и кодами; добавьте качество, возрастное ограничение и актёра.
3. Повторите названия или коды с другим регистром: должна появиться ошибка, новая запись не создаётся.
4. Добавьте фильм со всеми полями и несколькими актёрами. Проверьте сообщение и список актёров через запятую.
5. Откройте «Изменить»: все поля заполнены, актёры отмечены. Измените название, справочники и состав; сохраните.
6. Проверьте отказ для пустого/пробельного названия, длительности 0 и 301, бюджета 0 и 600000000, сборов 0 и 3000000000, будущей даты и отсутствующего актёра. Встроенную проверку браузера при необходимости можно отключить через DevTools, чтобы проверить сервер.
7. Удалите фильм и подтвердите действие: фильм исчезает, актёры остаются.
8. Добавьте актёра в несколько фильмов, затем удалите его. Фильмы остаются, актёр исчезает из состава всех фильмов.
9. Откройте /edit_film/999999: должна появиться страница 404 без stack trace.

## Проверка поиска

1. Откройте /search и выберите «Фантастика».
2. Список стран должен содержать США и Великобританию для демонстрационной базы.
3. Нажмите «Найти» без страны: появятся «Интерстеллар» и «Матрица» в порядке названия.
4. Выберите США: останется «Матрица».
5. Выберите жанр «Боевик»: результаты пустые, страны недоступны.
6. Откройте /get_countries?genre=ID_ФАНТАСТИКИ и проверьте JSON.
7. Быстро меняйте жанры: отмена предыдущего fetch не позволяет устаревшему ответу заменить новый список стран.

## Проверка статистики

На /aggregate исходные показатели: 4 фильма, 8 актёров, 4 жанра, 4 страны, 5 возрастных ограничений, 3 качества, 8 связей. Добавьте фильм с двумя существующими актёрами: фильмы +1, связи +2, актёры без изменений. Удалите фильм: показатели вернутся. После удаления актёра уменьшается число актёров и число его связей, число фильмов не меняется.

Для сверки в PostgreSQL:

```sql
SELECT count(*) FROM films;
SELECT count(*) FROM people;
SELECT count(*) FROM genres;
SELECT count(*) FROM countries;
SELECT count(*) FROM age_limits;
SELECT count(*) FROM shooting_qualities;
SELECT count(*) FROM film_people;
```

## Автоматические проверки

```shell
mvn test
```

13 unit-тестов проверяют валидацию, числовые границы, будущую дату, нормализацию, дубликаты и порядок удаления связей.

Для полного сценария с отдельным настоящим PostgreSQL нужен работающий Docker и доступ к образу postgres:17-alpine:

```shell
mvn -Pintegration verify
```

CatalogIT запускает PostgreSQL в контейнере и Spring Boot на случайном порту; проверяет серверный рендеринг страниц, создание/изменение/удаление фильма и актёра, JSON стран, поиск, статистику, внешние ключи и ответы на неверные запросы. Пользовательская cinema_db не используется.

Дополнительная статическая проверка согласованности без зависимостей Java (нужен Python 3):

```shell
python tools/check_contracts.py
```

Фактически выполненные проверки и ограничения текущего окружения приведены в [VERIFICATION.md](VERIFICATION.md).
