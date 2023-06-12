INSERT INTO users(id, email, username, password, role) 
VALUES("e7f9ef2b-c8b9-4a52-937b-53cdb9d923ee", "admin@gmail.com", "123456", "ROLE_ADMIN");

INSERT INTO users(id, email, username, password, role) 
VALUES("5af5b243-31fb-4ae2-a342-74c14ed16104", "lawyer@gmail.com", "123456", "ROLE_LAWYER");

INSERT INTO users(id, email, username, password, role) 
VALUES("8056e4bc-1060-430b-912a-90126876b06d", "client@gmail.com", "123456", "ROLE_CLIENT");

INSERT INTO companies(id, name) 
VALUES("ace5ae43-6060-49c2-9f74-0bfdcf8a7d9d", "Company");

INSERT INTO lawyers(id, company_id, user_id, title, identification_number, first_name, last_name, phone) 
VALUES("8056e4bc-1060-430b-912a-90126876b06d", "ace5ae43-6060-49c2-9f74-0bfdcf8a7d9d", "5af5b243-31fb-4ae2-a342-74c14ed16104", "Avukat", "33274102316", "Ali", "Akın", "05348540650");

INSERT INTO clients(id, user_id, identification_number, first_name, last_name, phone) 
VALUES("1f16438f-7f27-4056-a20c-ea71bca9d6a5", "8056e4bc-1060-430b-912a-90126876b06d", "33274102316", "Ahmet", "Yaşa", "05435698456");
