### Вариант 0.

*Хозяин*

| Название поля | Тип данных поля |
|---------------|-----------------|
| Идентификатор | Long            |
| Имя           | Varchar         |
| Дата рождения | Date            |

*Котик*

| Название поля | Тип данных поля |
|---------------|-----------------|
| Идентификатор | Long            |
| Имя           | Varchar         |
| Дата рождения | Date            |
| Порода        | Varchar         |
| Цвет          | Varchar         |
| Владелец      | Long            |

- В таблице «Котик» атрибут «Хозяин» является вторичным ключом для атрибута «Идентификатор» из таблицы «Хозяин»;
- Атрибуты «Идентификатор» в обеих таблицах должны быть первичными ключами;
- Атрибут «Цвет» таблицы «Котик» должен представлять из себя ограниченное количество значений: белый, рыжий, коричневый, серый, черный.

---
### Скрипты для БД: ###

```
CREATE TABLE Owner (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL
);

CREATE TABLE Cat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    breed VARCHAR(100) NOT NULL,
    color ENUM('BLACK', 'WHITE', 'GREY', 'BROWN', 'ORANGE') NOT NULL,
    owner_id INT,
    FOREIGN KEY (owner_id) REFERENCES Owner(id) ON DELETE CASCADE
);
```