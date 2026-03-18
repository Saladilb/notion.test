# Product Purchase Service

Небольшое Spring Boot приложение для расчета цены товара и покупки с учетом налога, купона и выбранного платежного процессора.

## Что есть

- `POST /calculate-price`
- `POST /purchase`
- проверка `taxNumber` по формату страны
- хранение продуктов и купонов в БД
- купоны с фиксированной скидкой и скидкой в процентах
- два платежных процессора: `paypal` и `stripe`
- H2 для локального запуска
- профиль `postgres`
- Docker и `compose.yaml`

## Стек

- Java 17
- Spring Boot 3
- Spring Web
- Spring Validation
- Spring Data JPA
- Lombok
- H2
- PostgreSQL
- JUnit 5

## Данные по умолчанию

Продукты:

- `1` - Iphone - `100.00`
- `2` - Headphones - `20.00`
- `3` - Case - `10.00`

Купоны:

- `D15` - скидка `15.00`
- `P10` - скидка `10%`
- `P100` - скидка `100%`

## Локальный запуск

```bash
./mvnw spring-boot:run
```

Для Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Приложение стартует на `http://127.0.0.1:8337`.

## PostgreSQL

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
```

При необходимости можно переопределить:

- `APP_DATASOURCE_URL`
- `APP_DATASOURCE_USERNAME`
- `APP_DATASOURCE_PASSWORD`

## Docker

Сборка:

```bash
./mvnw -DskipTests package
docker build -t product-purchase-service .
```

Запуск:

```bash
./mvnw -DskipTests package
docker compose up --build
```

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

## Тесты

```bash
./mvnw test
```
