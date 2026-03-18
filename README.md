# Product Purchase Service

Тестовое Spring Boot приложение для расчета цены продукта и проведения оплаты.

Старался сделать решение так, как обычно делают прикладную тестовую в реальной команде: без тяжелой архитектуры ради самой архитектуры, но с понятным разделением ответственности, аккуратной валидацией, нормальными тестами и возможностью без боли добавить новый платежный процессор.

## Что реализовано

- `POST /calculate-price`
- `POST /purchase`
- валидация входных данных, включая формат налогового номера
- хранение продуктов и купонов в БД
- два типа купонов: `FIXED` и `PERCENT`
- расчет цены через `BigDecimal`
- единый JSON-ответ для ошибок с HTTP 400
- поддержка двух mock-процессоров: Paypal и Stripe
- расширяемый слой платежей через общий интерфейс `PaymentGateway`
- unit и integration тесты
- запуск локально на H2
- профиль `postgres`
- `Dockerfile` и `compose.yaml`

## Стек

- Java 17
- Spring Boot 3
- Spring Web
- Spring Validation
- Spring Data JPA
- H2
- PostgreSQL
- JUnit 5 / MockMvc

## Как устроено

Структуру сделал не по слоям `controller/service/repository` на весь проект, а ближе к package-by-feature:

- `catalog` хранит продукты, купоны и инициализацию справочных данных
- `pricing` отвечает за налоговые правила и расчет итоговой цены
- `payment` содержит mock-процессоры, адаптеры и реестр процессоров
- `purchase` собирает сценарий покупки
- `api` содержит HTTP-контракт, DTO, валидацию и обработку ошибок

Осознанно не уходил в DDD/hexagonal/onion. Для двух эндпоинтов это было бы избыточно. Но при этом точки расширения оставил там, где они действительно нужны:

- новый платежный процессор добавляется через новую реализацию `PaymentGateway`
- правила расчета налога централизованы в `CountryTax`
- расчет цены вынесен в отдельный `PriceCalculator`

## Налоговые номера

Поддерживаются форматы:

- Германия: `DEXXXXXXXXX`
- Италия: `ITXXXXXXXXXXX`
- Греция: `GRXXXXXXXXX`
- Франция: `FRYYXXXXXXXXX`

Для Франции `Y` трактуется как латинская буква, `X` как цифра.

## Предзагруженные данные

Продукты:

- `1` - Iphone - `100.00`
- `2` - Headphones - `20.00`
- `3` - Case - `10.00`

Купоны:

- `D15` - фиксированная скидка `15.00`
- `P10` - скидка `10%`
- `P100` - скидка `100%`

Справочные данные добавляются идемпотентно при старте приложения, поэтому одинаково работают и на H2, и на PostgreSQL.

## Примеры запросов

Расчет цены:

```bash
curl --request POST "http://127.0.0.1:8337/calculate-price" \
  --header "Content-Type: application/json" \
  --data '{
    "product": 1,
    "taxNumber": "DE123456789",
    "couponCode": "D15"
  }'
```

Покупка:

```bash
curl --request POST "http://127.0.0.1:8337/purchase" \
  --header "Content-Type: application/json" \
  --data '{
    "product": 1,
    "taxNumber": "IT12345678900",
    "couponCode": "D15",
    "paymentProcessor": "paypal"
  }'
```

Пример ответа при ошибке:

```json
{
  "message": "Validation failed",
  "errors": [
    {
      "field": "taxNumber",
      "message": "taxNumber must match one of the supported country formats"
    }
  ]
}
```

## Локальный запуск

```bash
./mvnw spring-boot:run
```

Для Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Приложение поднимется на `http://127.0.0.1:8337`.

## PostgreSQL профиль

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

Параметры подключения можно переопределить через:

- `APP_DATASOURCE_URL`
- `APP_DATASOURCE_USERNAME`
- `APP_DATASOURCE_PASSWORD`

## Docker

Сборка образа:

```bash
./mvnw -DskipTests package
docker build -t product-purchase-service .
```

Запуск через compose:

```bash
./mvnw -DskipTests package
docker compose up --build
```

В compose поднимаются:

- приложение на порту `8337`
- PostgreSQL на порту `5432`

Сделал упор на быстрый локальный сценарий:

- jar собирается локально через Maven
- Docker-образ поднимается поверх готового артефакта
- `docker build` и `docker compose up --build` не тратят время на пересборку проекта внутри контейнера

Такой вариант для тестового здесь практичнее, чем тяжелый multi-stage с полной Maven-сборкой в контейнере на каждый локальный запуск.

## Тесты

```bash
./mvnw test
```

При разработке ядро расчета цены и часть платежного слоя сначала проверял через unit-тесты, а затем уже доводил HTTP-контракт integration-тестами через `MockMvc`.

## История коммитов

Проект разбит на несколько осмысленных шагов:

- инициализация Spring Boot проекта
- доменная модель и расчет цены
- purchase flow и платежные адаптеры
- REST API и валидация
- инфраструктурные доработки и документация

То есть история получается не одним коммитом "final", а более похожей на обычную рабочую разработку.

