CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id VARCHAR(255),
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
CREATE TABLE IF NOT EXISTS refresh_token (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255),
    value VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
