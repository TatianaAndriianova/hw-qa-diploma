# Дипломный проект — автотесты

Проект содержит автотесты на Java + Selenide + JUnit 5 с поддержкой двух баз данных: **PostgreSQL** и **MySQL**.

---

## Требования

- Java 11+
- Docker и Docker Compose
- Gradle (Wrapper включён — `./gradlew`)
- Google Chrome (или Chromium)

---

## Запуск с PostgreSQL (по умолчанию)

### 1. Поднять контейнеры

```bash
docker-compose --profile postgres up -d
```

### 2. Запустить тесты

```bash
./gradlew test
```

По умолчанию тесты подключаются к:

| Параметр | Значение по умолчанию |
|---|---|
| `db.url` | `jdbc:postgresql://localhost:5432/app` |
| `db.user` | `app` |
| `db.password` | `pass` |

---

## Запуск с MySQL

### 1. Поднять контейнеры

```bash
docker-compose --profile mysql up -d
```

### 2. Запустить тесты с указанием параметров БД

```bash
./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app -Ddb.user=app -Ddb.password=pass
```

---

## Дополнительные параметры запуска

Тесты поддерживают следующие системные свойства через флаг `-D`:

| Свойство | Описание | Значение по умолчанию |
|---|---|---|
| `db.url` | JDBC URL базы данных | `jdbc:postgresql://localhost:5432/app` |
| `db.user` | Пользователь БД | `app` |
| `db.password` | Пароль БД | `pass` |
| `selenide.headless` | Запуск браузера в headless-режиме | не задано |

Пример запуска в headless-режиме:

```bash
./gradlew test -Dselenide.headless=true
```

---

## Отчёт Allure

После прогона тестов сформировать и открыть отчёт:

```bash
./gradlew allureServe
```

---

## Остановка контейнеров

```bash
docker-compose --profile postgres down
# или
docker-compose --profile mysql down
```

Чтобы также удалить тома с данными:

```bash
docker-compose --profile postgres down -v
```

---

## Подключение к БД вручную (для отладки)

**PostgreSQL:**

```bash
docker exec -it diploma-postgres psql -U app -d app
```

**MySQL:**

```bash
docker exec -it diploma-mysql mysql -u app -ppass app
```
