# Возможности

- Добавление посылок
- Проверка посылок на плагиат

все.

# Архетектура

Система попилена на следующие микросервисы:

1. API Gateway (`localhost:8080`):
Единая точка входа, перекидывающая запросы к другим сервисам.
2. File Service (`localhost:8081`):
Принимает запросы от пользователей, вычисляет хеши, сохраняет метаданные, начинает проверку в `Analysis Service`.
3. Analysis Service (`localhost:8081`):
4. PostgreSQL бд:
бд для File Serivce `file_db`
бд для Analysis Service `analysis_db`

# Алгоритм проверки
При загрузке файла считаем хеш.
Ищем совпадения по `task_id` и `file_hash`, если имя студента совпадает.
Нашли - бан.

# API Референс
## Публичный
Отправить файл:
`POST http://localhost:8080/api/v1/files/upload`
Body: `file` бинарник, `student_name` имя студента в строке, `task_id` id в строке
Возвращает: 200 OK и строку с id
Получить отчет:
`GET http://localhost:8080/api/v1/reports/{id}`
Возвращает: 200 OK и json
```
{
	"submissionId": "{id}",
	"isPlagiarized": {true/false},
	"verdictMessage": "{message}",
	"createdAt": "{timestamp}"
}
```

## Внутренний
AnalysisService ждет от File Service запросы вида:
```
{
  "submissionId": "{id}",
  "studentName": "{name}",
  "taskId": "{taskid}",
  "fileHash": "{hash}"
}
```
и не возвращает ничего полезного, просто регистрирует

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

Загрузка файла:
```
./uploader.sh <путь> <имя> <id>
```

Проверка файла:
```
./checker.sh <id>
```
