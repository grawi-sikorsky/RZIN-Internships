CREATE TABLE user_posts
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title    VARCHAR(255),
    content  VARCHAR(255),
    username VARCHAR(255),
    postdate TIMESTAMP with time zone,
    user_id  BIGINT,
    CONSTRAINT pk_user_posts PRIMARY KEY (id)
);

ALTER TABLE user_posts
    ADD CONSTRAINT FK_USER_POSTS_ON_USERID FOREIGN KEY (user_id) REFERENCES user_entity (id);