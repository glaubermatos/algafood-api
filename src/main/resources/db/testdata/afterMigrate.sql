set foreign_key_checks = 0;

delete from city;
delete from cuisine;
delete from state;
delete from payment_method;
delete from user_group_permission;
delete from permission;
delete from product;
delete from restaurant;
delete from restaurant_payment_method;
delete from user;
delete from user_group;
delete from user_user_group;

set foreign_key_checks = 1;

alter table city auto_increment = 1;
alter table cuisine auto_increment = 1;
alter table state auto_increment = 1;
alter table payment_method auto_increment = 1;
alter table user_group auto_increment = 1;
alter table permission auto_increment = 1;
alter table product auto_increment = 1;
alter table restaurant auto_increment = 1;
alter table user auto_increment = 1;

INSERT INTO cuisine (id, name) values (1, 'Tailandesa');
INSERT INTO cuisine (id, name) values (2, 'Indiana');
INSERT INTO cuisine (id, name) values (3, 'Argentina');
INSERT INTO cuisine (id, name) values (4, 'Brasileira');

INSERT INTO state (id, name) VALUES (1, 'Bahia');
INSERT INTO state (id, name) VALUES (2, 'São Paulo');

INSERT INTO city (id, name, state_id) VALUES (1, 'Manoel Vitorino', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'Santa Inês', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'Santo André', 2);

INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, address_city_id, address_postal_code, address_street, address_number, address_district) VALUES (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at) VALUES (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at) VALUES (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at) VALUES (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at) VALUES (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at) VALUES (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp);

INSERT INTO payment_method (id, description) VALUES (1, 'Cartão de crédito');
INSERT INTO payment_method (id, description) VALUES (2, 'Cartão de débito');
INSERT INTO payment_method (id, description) VALUES (3, 'Dinheiro');

INSERT INTO permission (id, name, description) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);
