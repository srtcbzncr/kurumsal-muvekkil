INSERT INTO roles(id, name) 
VALUES('3338886c-ea93-47cc-97a5-42ffce4dcd6e', 'ROLE_ADMIN');

INSERT INTO roles(id, name) 
VALUES('4feb97ad-bbeb-49de-8407-de336d74230e', 'ROLE_CLIENT');

INSERT INTO roles(id, name) 
VALUES('e5874eb0-b6a3-49bd-a6df-f67c60c85a2c', 'ROLE_LAWYER');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('e7f9ef2b-c8b9-4a52-937b-53cdb9d923ee', 'admin@gmail.com', 'admin', '123456', '3338886c-ea93-47cc-97a5-42ffce4dcd6e', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('5af5b243-31fb-4ae2-a342-74c14ed16104', 'lawyer@gmail.com', 'lawyer', '123456', 'e5874eb0-b6a3-49bd-a6df-f67c60c85a2c', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('8056e4bc-1060-430b-912a-90126876b06d', 'client@gmail.com', 'client', '123456', '4feb97ad-bbeb-49de-8407-de336d74230e', 'true', 'false', 'true', 'false');