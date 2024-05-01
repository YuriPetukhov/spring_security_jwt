-- Добавление роли
INSERT INTO roles (role)
VALUES ('USER');

-- Добавление пользователя с паролем password
INSERT INTO users (id, username, email, password)
VALUES ('1', 'john', 'john@example.com', '$2a$10$yWmw2GdQPlzDzrUvH8jsEOQtiZHYkRg0j3up6b1izPbd7JdRcHxqK');

-- Связывание пользователя с ролью
INSERT INTO user_roles (user_id, role_id)
VALUES ('1', 1);

-- Добавление refresh token
INSERT INTO refresh_token (id, user_id, value)
VALUES ('1', '1', 'refresh_token_value_1');
