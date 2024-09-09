INSERT INTO users (username,password)
VALUES ('admin','$2a$10$9mdH0hw1vbukRPHX8GmuoOCWQPwHpHiiDiHin5eJej/0NoXbeH3oq');

INSERT INTO note ( user_id, title, content, access_type)
VALUES (1, 'Start note', 'Start content', 'PRIVATE');