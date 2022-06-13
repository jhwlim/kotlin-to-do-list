INSERT INTO categories (id, name)
VALUES (1, '카테고리1');

INSERT INTO tasks (id, name, status, category_id)
VALUES (1L, 'Task1', 'COMPLETE', 1L),
       (2L, 'Task2', 'PLANNING', 1L);
