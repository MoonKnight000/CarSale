insert into position(name,created_time,updated_at) values ('ProjectManager','2023-02-02','2023-05-05');

insert into position_permissions(position_id, permissions) values (1, 'DELETE_USERS');
insert into position_permissions(position_id, permissions) values (1, 'UPDATE_POSITION');
insert into position_permissions(position_id, permissions) values (1, 'UPDATE_USERS');
insert into position_permissions(position_id, permissions) values (1, 'GET_USERS_BY_ID');
insert into position_permissions(position_id, permissions) values (1, 'GET_ALL_USERS_BY_COMPANY');
insert into position_permissions(position_id, permissions) values (1, 'ENABLE_USERS');
insert into position_permissions(position_id, permissions) values (1, 'ADD_POSITION');
insert into position_permissions(position_id, permissions) values (1, 'GET_POSITION');

insert into users(full_name, login, parol, position_id, enabled) values ('Bobobekov Murodjon', 'murodjon', '$2a$04$lz6wA590/PjoM.1GmMU1oeCe3zGKzrVK2xlzIWje2V4zEVrUaElxK', 1, true);