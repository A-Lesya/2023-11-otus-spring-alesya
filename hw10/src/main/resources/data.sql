insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3),
       ('Девять принцев Амбера', 3),
       ('Ружья Авалона', 3),
       ('Знак Единорога', 3),
       ('Рука Оберона', 3),
       ('Владения Хаоса', 3),
       ('Карты судьбы', 3),
       ('Кровь Амбера', 3),
       ('Знак Хаоса', 3),
       ('Рыцарь Теней', 3),
       ('Принц Хаоса', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6),
       (4, 5),   (4, 6),
       (5, 5),   (5, 6),
       (6, 1),   (6, 6),
       (7, 5),   (7, 6),
       (8, 2),   (8, 6),
       (9, 5),   (9, 6),
       (10, 6),
       (11, 5),   (11, 6),
       (12, 5),   (12, 6),
       (13, 1),   (13, 6);                                     ;

insert into comments(book_id, text)
values (2, 'abacaba'),   (2, 'my comment'),
       (10, 'comment for book 10'),
       (10, 'second comment for book 10'),
       (10, 'third comment for book 10');