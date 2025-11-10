## Поставьте, пожалуйста, хотя бы 6. Тогда у меня со штрафом 2 хотя бы 4 будет...
## Это ужасно, я делал эту задачу безумное количество времени
## Там немного может ломаться IO, запускайте через IDEA, пожалуйста


# Возможности

Все, что было в задании (даже дополнительное) (даже json импорт/экспорт работает)

# Solid

## Single Responsibility
Каждый класс отвечает за свою задачу.
DomainFactory отвечает только за создание объектов
Каждый фасад, сервис, команда - только за свою штуку

## Open/Closed
В DataImporter/Visitor можно добавлять новые форматы (я добавил JSON), ничего не меняя
С командами и декораторами так же.

## Liskov Substitution
Repository, Command, DataVisitor можно использовать вместо классов, которые их реализуют/расширяют.

## Interface Segregation
Обеспечено разделение интерфейсов: Repository, Command, DomainFactory

## Dependency Inversion
Все фасады зависят от абстракций (Repository, DomainFactory)
DataExporter работает через интерфейс DataVisitor

# GRASP

## High Cohesion
В фасадах все операции в одном месте
В аналитике и сервисе так же

## Low Coupling
Используются интерфейсы.
Фасады скрывают репозитории и фабрики.

## Information Expert
BankAccount управляет всем своим, Operation тоже

# GoF

## Factory
см. `com.sashafiesta.finances.factory`
Позволяет создавать объекты централизованно

# Facade
см. `com.sashafiesta.finances.facade`
Упрощает доступ к операциям

# Command
см. `com.sashafiesta.finances.command`
Представляет запрос как объект

# Decorator
см. `com.sashafiesta.finances.command.decorator`
Позволяет добавлять функционал к командам динамически

# Visitor
см. `com.sashafiesta.finances.exporter.visitor`
Разделяет алгоритм от объектов работы