# Lesson-11.-Part-1.-Homework

## Подготовка базы данных.

Необходимо создать таблицы БД. СУБД выбираем MySQL.
Нужно подготовить скрипт DDL, который описывает создание базы данных и таблиц, используемых в этой лабораторной.

## JDBC

Реализовать подключение к БД посредством JDBC (Java DataBase Connectivity).

Согласно интерфейсу, реализовать методы ниже для обеих таблиц базы данных (где T – это имя вашей сущности):

```
public T save(T entity);
public void deleteById(long id);
public void deleteByEntity(T entity);
public void deleteAll();
public T update(T entity);
public T getById(long id);
public List<T> getAll();
```

Также необходимо реализовать метод, который возвращает все дочерние сущности по идентификатору родительской:

```
public List<T> getAllByVId(); // Здесь T – это дочерняя сущность, V – родительская.
```

## Hibernate

Реализовать подключение к БД посредством Hibernate - популярное ORM – решение для Java.

Согласно интерфейсу, реализовать методы ниже для обеих таблиц базы данных (где T – это имя вашей сущности):

```
public T save(T entity);
public void deleteById(long id);
public void deleteByEntity(T entity);
public void deleteAll();
public T update(T entity);
public T getById(long id);
public List<T> getAll();
```

Также необходимо реализовать метод, который возвращает все дочерние сущности по идентификатору родительской:
```
public List<T> getAllByVId();	// Здесь T – это дочерняя сущность, V – родительская.
```

## MyBatis

Реализовать подключение к БД посредством MyBatis – Java фреймворк для работы с сущностями БД.

Согласно интерфейсу, реализовать методы ниже для обеих таблиц базы данных (где T – это имя вашей сущности):

```
public T save(T entity);
public void deleteById(long id);
public void deleteByEntity(T entity);
public void deleteAll();
public T update(T entity);
public T getById(long id);
public List<T> getAll();
```

Также необходимо реализовать метод, который возвращает все дочерние сущности по идентификатору родительской:

```
public List<T> getAllByVId();		// Здесь T – это дочерняя сущность, V – родительская.
```

Для последнего метода необходимо описать условие: если дочерних сущностей больше 5, то нужно вернуть первые 5.
