INSERT INTO plans(id, name, description, monthly_price, annual_price, client_quota, lawyer_quota, file_quota_per_client, active, deleted)
VALUES('83d2b297-66dd-40a9-aba2-c537b4749e02', 'Plan 1', 'Plan 1', 10, 100, 10, 10, 10, 'true', 'false');

INSERT INTO plans(id, name, description, monthly_price, annual_price, client_quota, lawyer_quota, file_quota_per_client, active, deleted) 
VALUES('f6dc6b55-e4cf-4bfc-bade-38ef7aff178c', 'Plan 2', 'Plan 2', 20, 200, 20, 20, 20, 'true', 'false');

INSERT INTO plans(id, name, description, monthly_price, annual_price, client_quota, lawyer_quota, file_quota_per_client, active, deleted) 
VALUES('d99ae73b-c262-402d-9708-3c7df8d2cce4', 'Plan 3', 'Plan 3', 30, 300, 30, 30, 30, 'true', 'false');

INSERT INTO roles(id, name) 
VALUES('d7386c89-c612-4230-98fb-ca419ecf183b', 'ROLE_ADMIN');

INSERT INTO roles(id, name) 
VALUES('36108f4b-7ea2-4c4d-9d67-dfa6da1f1758', 'ROLE_CLIENT');

INSERT INTO roles(id, name) 
VALUES('c1d900d3-799a-42bb-bd68-0fdb1bdacb45', 'ROLE_LAWYER');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('e7f9ef2b-c8b9-4a52-937b-53cdb9d923ee', 'admin@gmail.com', 'admin', '123456', 'd7386c89-c612-4230-98fb-ca419ecf183b', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('fb0c653e-5789-41b3-928b-213c42af802e', 'admin2@gmail.com', 'admin2', '123456', 'd7386c89-c612-4230-98fb-ca419ecf183b', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('5af5b243-31fb-4ae2-a342-74c14ed16104', 'lawyer@gmail.com', 'lawyer', '123456', 'c1d900d3-799a-42bb-bd68-0fdb1bdacb45', 'true', 'false', 'true', 'false');

INSERT INTO users(id, email, username, password, role_id, active, deleted, is_new, locked) 
VALUES('8056e4bc-1060-430b-912a-90126876b06d', 'client@gmail.com', 'client', '123456', '36108f4b-7ea2-4c4d-9d67-dfa6da1f1758', 'true', 'false', 'true', 'false');

INSERT INTO companies(id, name, active, deleted) 
VALUES('ace5ae43-6060-49c2-9f74-0bfdcf8a7d9d', 'Company', 'true', 'false');

INSERT INTO lawyers(id, company_id, user_id, title, identification_number, first_name, last_name, phone, active, deleted) 
VALUES('8056e4bc-1060-430b-912a-90126876b06d', 'ace5ae43-6060-49c2-9f74-0bfdcf8a7d9d', '5af5b243-31fb-4ae2-a342-74c14ed16104', 'Avukat', '33274102316', 'Ali', 'Akın', '05348540650', 'true', 'false');

INSERT INTO clients(id, user_id, identification_number, first_name, last_name, phone, active, deleted) 
VALUES('1f16438f-7f27-4056-a20c-ea71bca9d6a5', '8056e4bc-1060-430b-912a-90126876b06d', '33274102316', 'Ahmet', 'Yaşa', '05435698456', 'true', 'false');

INSERT INTO courts(id, name, active, deleted) 
VALUES('5ad44942-4c22-4e84-98f0-a307c81c9fdb', 'Court 1', 'true', 'false');

INSERT INTO courts(id, name, active, deleted) 
VALUES('1b3a2a5b-fc68-4d88-9a93-fb630cb6aae5', 'Court 2', 'true', 'false');

INSERT INTO courts(id, parent_id, name, active, deleted) 
VALUES('d4247b80-a1b7-4254-a5d3-02c7dfe42c2b', '1b3a2a5b-fc68-4d88-9a93-fb630cb6aae5', 'Court 3', 'true', 'false');

INSERT INTO courts(id, parent_id, name, active, deleted) 
VALUES('e1eb6931-610b-42c2-8463-4dad90f9af68', '1b3a2a5b-fc68-4d88-9a93-fb630cb6aae5', 'Court 4', 'true', 'false');

INSERT INTO courts(id, parent_id, name, active, deleted) 
VALUES('424991f1-9b4b-47a3-b2eb-9d4ce8789b40', '1b3a2a5b-fc68-4d88-9a93-fb630cb6aae5', 'Court 5', 'false', 'false');

INSERT INTO courts(id, parent_id, name, active, deleted) 
VALUES('3a89f7c0-e6dc-4f57-a20f-f9a24e9ba0aa', '1b3a2a5b-fc68-4d88-9a93-fb630cb6aae5', 'Court 6', 'true', 'true');

INSERT INTO courts(id, parent_id, name, active, deleted) 
VALUES('b1f1d3a8-a0bf-49f4-ad0d-8b839f213929', '1b3a2a5b-fc68-4d88-9a93-fb630cb6aae5', 'Court 7', 'false', 'true');
