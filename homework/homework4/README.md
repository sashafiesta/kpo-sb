
# Возможности

- Управление заказами (создание, просмотр статуса).
- Управление счетами (создание, пополнение, баланс).
- Асинхронная оплата с гарантией доставки сообщений (Transactional Outbox/Inbox).

В целом все, фронта нет.

# Архитектура

Система попилена на следующие микросервисы:

1. API Gateway (`localhost:8080`):
Единая точка входа, отвечающая только за routing запросов к другим сервисам.
2. Order Service (`localhost:8081`):
Отвечает за создание заказа и обновление его статуса. Реализует паттерн Transactional Outbox для инициации оплаты.
3. Payments Service (`localhost:8082`):
Отвечает за счета пользователей и списание средств. Реализует паттерны Transactional In/Outbox для гарантии.
4. RabbitMQ:
Брокер сообщений для асинхронного общения между сервисами.
5. PostgreSQL бд:
бд для Order Service `orders_db`
бд для Payments Service `payments_db`

# Алгоритм оплаты

1. Пользователь создает заказ -> Order Service сохраняет заказ (status: `NEW`) и задачу на оплату в таблицу `outbox_messages` в одной транзакции.
2. Фоновый процесс Order Service читает `outbox` и отправляет событие в RabbitMQ.
3. Payment Service читает сообщение -> сохраняет в `inbox_messages` (дедупликация).
4. Payment Service пытается списать деньги атомарно.
   - Если денег хватает -> записывает успех в `outbox`.
   - Если нет -> записывает провал в `outbox`.
5. Фоновый процесс Payment Service отправляет результат обратно в RabbitMQ.
6. Order Service получает результат и меняет статус заказа на `FINISHED` или `CANCELLED`.

# API Референс

## Публичный

### Счета (Payments)
Создать счет:
`POST http://localhost:8080/api/v1/payments/accounts`
Body: `{"userId": 100}`
Возвращает: 200 OK и данные счета.

Пополнить счет:
`POST http://localhost:8080/api/v1/payments/accounts/deposit`
Body: `{"userId": 100, "amount": 1000}`
Возвращает: 200 OK и обновленный баланс.

### Заказы (Orders)
Создать заказ:
`POST http://localhost:8080/api/v1/orders`
Body: `{"userId": 100, "amount": 500, "description": "Item"}`
Возвращает: 200 OK, JSON с заказом и статусом `NEW`.

Посмотреть статус заказа:
`GET http://localhost:8080/api/v1/orders/{id}`
Возвращает: 200 OK и JSON с актуальным статусом (`NEW` / `FINISHED` / `CANCELLED`).

## Внутренний (RabbitMQ)
Order Service отправляеn в Exchange `shop.exchange` с ключом `payment.process`:
```json
{
  "orderId": 1,
  "userId": 100,
  "amount": 500
}
```

  
# Сборка и запуск:
## Сборка
```
chmod +x build.sh
./build.sh
```
## Запуск
```
chmod +x run.sh
./run.sh
```
## Тестирование
Юнитов нет, интерфейс можно потрогать руками. 

Тесты запускаются руками:
```
./checker.sh
```
