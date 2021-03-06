---liquibase formatted sql
--changeset jsikora:1
CREATE TABLE user_posts (
                            id BIGINT,
                            title varchar(255),
                            content varchar(255),
                            username varchar(255),
                            postdate varchar(255),
                            user_id BIGINT
                        );
--changeset jsikora:2
ALTER TABLE user_posts ALTER COLUMN content TYPE varchar(512);

-- big change
--changeset jsikora:3
ALTER TABLE user_posts ALTER COLUMN id SET NOT NULL;
ALTER TABLE user_posts ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY;
ALTER TABLE user_posts ADD CONSTRAINT pk_user_posts PRIMARY KEY (id);
ALTER TABLE user_posts ADD CONSTRAINT FK_USER_POSTS_ON_USERID FOREIGN KEY (user_id) REFERENCES user_entity (id);

--changeset jsikora:4
-- ALTER TABLE user_posts USING postdate::timestamp with time zone postdate TYPE TIMESTAMP with time zone;