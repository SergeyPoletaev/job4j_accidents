# job4j_accidents

[![build](https://github.com/SergeyPoletaev/job4j_accidents/workflows/build/badge.svg)](https://github.com/SergeyPoletaev/job4j_accidents/actions)

### Описание проекта:

В системе существуют две роли - обычные пользователи и автоинспекторы.  
Пользователь добавляет описание нарушения ПДД и в заявке указывает:

* адрес
* номер машины
* описание нарушения
* фотографию нарушения.

У заявки есть статус - Принята, Отклонена, Завершена.

### Стек технологий:

* Java 17
* Maven 3.8
* Spring Boot 2
* Thymeleaf 3
* Bootstrap 5
* PostgreSQL 14
* JDBC
* Liquibase

### Требования к окружению:

* Java 17
* Maven 3.8
* PostgreSQL 14

### Запуск проекта:

1. Настроить подключение к серверу БД в соответствии с настройками из файла    
   [./job4j_accidents/src/main/resources/db.properties](https://github.com/SergeyPoletaev/job4j_accidents/blob/master/src/main/resources/db.properties)   
   В случае изменения настроек подключения к БД привести в соответствие также настройки в файле  
   [./job4j_accidents/db/liquibase.properties](https://github.com/SergeyPoletaev/job4j_accidents/blob/master/db/liquibase.properties)
2. Создать базу данных, например через утилиту psql:

``` 
create database accidents 
```

3. Создать таблицы нужной структуры и упаковать проект в jar архив. Для этого в папке с проектом выполнить:

``` 
mvn package -Pproduction 
```  

Архив jar будет находится по пути: ./job4j_accidents/target/job4j_accidents-1.0-SNAPSHOT.jar

4. Запустить приложение командой:

``` 
java -jar job4j_accidents-1.0-SNAPSHOT.jar 
```

---

### Контакты

telegram: [@svpoletaev](https://t.me/svpoletaev)

