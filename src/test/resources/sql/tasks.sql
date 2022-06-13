INSERT INTO categories (id, name)
VALUES (1, '카테고리1');

INSERT INTO tasks (id, name, status, category_id, created_dtm)
VALUES (1L, 'Task1', 'COMPLETE', 1L, '20210318 11:00:00'),
       (2L, 'Task2', 'PLANNING', 1L, '20210618 13:00:00');
