-- Добавление роли
INSERT INTO roles (role)
VALUES ('ADMIN');

-- Добавление пользователя с паролем admin
INSERT INTO users (id, username, email, password)
VALUES ('2', 'viktor', 'viktor@example.com', '$2a$10$yWmw2GdQPlzDzrUvH8jsEOQtiZHYkRg0j3up6b1izPbd7JdRcHxqK');


-- Связывание пользователя с ролью
INSERT INTO user_roles (user_id, role_id)
VALUES ('2', 2);

-- Добавление refresh token
INSERT INTO refresh_token (id, user_id, value)
VALUES ('2', '2', 'refresh_token_value_2');
