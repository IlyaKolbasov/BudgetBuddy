# BudgetBuddy

## Возможности проекта:

### Сохранение расходных операций:
- Получение информации о расходных операциях в тенге (KZT), рублях (RUB) и других валютах в реальном времени.
- Сохранение данных в собственной базе данных.

### Управление лимитами:
- Хранение месячного лимита расходов в долларах США (USD) для двух категорий: товаров и услуг.
- Установка лимита по умолчанию равным 1000 USD, если не задано иное.
- Возможность клиенту установить новый лимит с текущей датой, без возможности изменения существующих лимитов.

### Запрос данных биржевых курсов:
- Получение данных курсов валютных пар KZT/USD и RUB/USD с дневным интервалом.

### Обработка превышения лимитов:
- Автоматическая пометка транзакций, превышающих установленный лимит, с флагом `limit_exceeded`.

### Отчетность по транзакциям:
- Возможность клиенту запрашивать список транзакций, превышающих лимит, с указанием лимита (дата установления, сумма лимита, валюта).

## Доступ к проекту
Проект успешно задеплоен и доступен по следующему адресу:
[https://budgetbuddy-won0.onrender.com/](https://budgetbuddy-won0.onrender.com/)

Проект использует Swagger для документирования API. Вы можете получить доступ к интерфейсу Swagger, перейдя по следующему URL:
[https://budgetbuddy-won0.onrender.com/swagger-ui/index.html](https://budgetbuddy-won0.onrender.com/swagger-ui/index.html)

## Тестирование
В проекте выполнено JUnit тестирование для следующих сервисов:

- **LimitService**: Тесты проверяют корректность работы с лимитами и их обработку.
- **TransactionService**: Тесты обеспечивают проверку функциональности управления транзакциями, выставление флага `limitExceeded`.

## Стек технологий

### Язык программирования
- **Java**: Используются типы BigDecimal, Date, перечисления (enums).
- **Коллекции**: Stream API, лямбда-выражения.
- **Обработка исключений**: Эффективное управление ошибками.
- **Code conventions**: Следование принятому стилю кодирования.

### Фреймворки и библиотеки
- **Spring WEB**: Работа с бинами, компонентами, конфигурациями.
- **ORM**: Spring Data, включая Entities, Transactions, Relations (ManyToOne, OneToMany), JPQL.
- **Lombok**: Упрощение кода с помощью аннотаций.
- **Maven**: Управление зависимостями, плагины сборки, модули.

### База данных
- **SQL**.
- **Инструменты миграции**: Flyway.
