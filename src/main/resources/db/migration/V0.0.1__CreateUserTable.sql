CREATE TABLE user (
                      user_id     varchar(36)     not null,
                      user_name   varchar(100)    not null unique,
                      password    varchar(32)    not null,
                      primary key(user_id)
) ENGINE = InnoDB CHARSET=utf8mb4;

INSERT INTO user(user_id, user_name, password) VALUES ("1", "test", "test");