--liquibase formatted postgresql
--changeset jsikora:1
CREATE TABLE user_entity
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    uuid            VARCHAR(255),
    is_active       BOOLEAN,
    activation_link VARCHAR(255),
    username        VARCHAR(255),
    password        VARCHAR(255),
    email           VARCHAR(255),
    firstname       VARCHAR(255),
    lastname        VARCHAR(255),
    age             INTEGER,
    phone           VARCHAR(255),
    CONSTRAINT pk_user_entity PRIMARY KEY (id)
);

--changeset jsikora:2
ALTER TABLE user_entity ADD avatar_link VARCHAR(255);

--changeset jsikora:3
ALTER TABLE user_entity ADD is_enabled BOOLEAN;