INSERT INTO roles(id, name) 
VALUES('d7386c89-c612-4230-98fb-ca419ecf183b', 'ROLE_ADMIN');

INSERT INTO roles(id, name) 
VALUES('36108f4b-7ea2-4c4d-9d67-dfa6da1f1758', 'ROLE_CLIENT');

INSERT INTO roles(id, name) 
VALUES('c1d900d3-799a-42bb-bd68-0fdb1bdacb45', 'ROLE_LAWYER');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('e7f9ef2b-c8b9-4a52-937b-53cdb9d923ee', 'admin@gmail.com', 'admin', '123456', 'd7386c89-c612-4230-98fb-ca419ecf183b', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('5af5b243-31fb-4ae2-a342-74c14ed16104', 'lawyer@gmail.com', 'lawyer', '123456', 'c1d900d3-799a-42bb-bd68-0fdb1bdacb45', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('8056e4bc-1060-430b-912a-90126876b06d', 'client@gmail.com', 'client', '123456', '36108f4b-7ea2-4c4d-9d67-dfa6da1f1758', 'true', 'false', 'true', 'false');
