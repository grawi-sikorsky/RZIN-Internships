INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone)
    VALUES (1, 'uuid1', 'testuser1', 'testpass1', 'testemail1', 'firstname', 'lastname', 66, '555555555');
INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone)
    VALUES (2, 'uuid2', 'testuser2', 'testpass2', 'testemail2', 'firstname', 'lastname', 66, '555555555');
INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone)
    VALUES (3, 'uuid3', 'testuser3', 'testpass3', 'testemail3', 'firstname', 'lastname', 66, '555555555');
INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone)
    VALUES (4, 'uuid4', 'testuser4', 'testpass4', 'testemail4', 'firstname', 'lastname', 66, '555555555');
INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone)
    VALUES (5, 'uuid5', 'testuser5', 'testpass5', 'testemail5', 'firstname', 'lastname', 66, '555555555');
INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone)
    VALUES (6, 'uuid6', 'testuser6', 'testpass6', 'testemail6', 'firstname', 'lastname', 66, '555555555');
INSERT INTO user_entity(id, uuid, username, password, email, firstname, lastname, age, phone, is_active, activation_link, avatar_link)
    VALUES (7, 'uuid7', 'testuser7', 'testpass7', 'testemail7', 'firstname', 'lastname', 66, '555555555', false, 'activationlink', 'http://costam.com/default-avatar.jpg');

INSERT INTO user_posts(id, content,title,user_id,username) VALUES (1, '', '', 1, 'testuser1');
INSERT INTO user_posts(id, content,title,user_id,username) VALUES (2, '', '', 1, 'testuser1');
INSERT INTO user_posts(id, content,title,user_id,username) VALUES (3, '', '', 2, 'testuser2');
INSERT INTO user_posts(id, content,title,user_id,username) VALUES (4, '', '', 2, 'testuser2');
