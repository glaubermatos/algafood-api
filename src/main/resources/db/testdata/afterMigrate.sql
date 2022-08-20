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
delete from restaurant_user_responsible;
delete from purchase;
delete from purchase_item;
delete from product_photo;

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
alter table purchase auto_increment = 1;
alter table purchase_item auto_increment = 1;

INSERT INTO cuisine (id, name) values (1, 'Tailandesa');
INSERT INTO cuisine (id, name) values (2, 'Indiana');
INSERT INTO cuisine (id, name) values (3, 'Argentina');
INSERT INTO cuisine (id, name) values (4, 'Brasileira');

INSERT INTO state (id, name) VALUES (1, 'Bahia');
INSERT INTO state (id, name) VALUES (2, 'São Paulo');

INSERT INTO city (id, name, state_id) VALUES (1, 'Manoel Vitorino', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'Santa Inês', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'Santo André', 2);

INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, active, open, address_city_id, address_postal_code, address_street, address_number, address_district) VALUES (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, active, open) VALUES (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true, true);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, active, open) VALUES (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true, true);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, active, open) VALUES (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true, true);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, active, open) VALUES (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true, true);
INSERT INTO restaurant (id, name, freight_rate, cuisine_id, created_at, updated_at, active, open) VALUES (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true, true);

INSERT INTO payment_method (id, description, updated_at) VALUES (1, 'Cartão de crédito', utc_timestamp);
INSERT INTO payment_method (id, description, updated_at) VALUES (2, 'Cartão de débito', utc_timestamp);
INSERT INTO payment_method (id, description, updated_at) VALUES (3, 'Dinheiro', utc_timestamp);

INSERT INTO restaurant_payment_method (restaurant_id, payment_method_id) VALUES (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, false, 1);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, true, 1);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);

INSERT INTO product (name, description, price, active, restaurant_id) VALUES ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

INSERT INTO permission (id, name, description) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO user_group (id, name) VALUES (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

INSERT INTO user_group_permission (user_group_id, permission_id) VALUES (1, 1), (1, 2), (2, 1), (2, 2), (3, 1);

INSERT INTO user (id, name, email, password, created_at) VALUES 
(1,'João da Silva', 'joao.ger@algafood.com', '123', utc_timestamp),
(2, 'Maria Joaquina', 'maria.vnd@algafood.com', '123', utc_timestamp),
(3, 'José Souza', 'jose.aux@algafood.com', '123', utc_timestamp),
(4, 'Sebastião Martins', 'sebastiao.cad@algafood.com', '123', utc_timestamp),
(5, 'Manoel Lima', 'manoel.loja@gmail.com', '123', utc_timestamp),
(6, 'Débora Mendonça', 'glaub.oliveira@hotmail.com', '123', utc_timestamp),
(7, 'Manoel Lima', 'glaub.oliveira@hotmail.com', '123', utc_timestamp);   

INSERT INTO user_user_group (user_id, user_group_id) VALUES (1, 2), (1, 2), (2, 2);

INSERT INTO restaurant_user_responsible (restaurant_id, user_id) VALUES (1, 5), (3, 5);

INSERT INTO purchase (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_postal_code, 
    address_street, address_number, address_complement, address_district,
    status, created_at, subtotal, freight_rate, total_amount) VALUES (1, 'c3ce353a-03ec-4d21-96ea-1d44e3a8ef85', 1, 6, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
'CREATED', utc_timestamp, 298.90, 10, 308.90);

INSERT INTO purchase_item (id, purchase_id, product_id, quantity, unit_price, total_price, note) VALUES (1, 1, 1, 1, 78.9, 78.9, null);

INSERT INTO purchase_item (id, purchase_id, product_id, quantity, unit_price, total_price, note) VALUES (2, 1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO purchase (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_postal_code, 
    address_street, address_number, address_complement, address_district,
    status, created_at, subtotal, freight_rate, total_amount) VALUES (2, 'b95811e1-583a-4cbb-9bb6-01fe2bc90793', 4, 6, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
'CREATED', utc_timestamp, 79, 0, 79);

INSERT INTO purchase_item (id, purchase_id, product_id, quantity, unit_price, total_price, note) VALUES (3, 2, 6, 1, 79, 79, 'Ao ponto');

INSERT INTO purchase (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_postal_code, 
    address_street, address_number, address_complement, address_district,
    status, created_at, confirmation_date, delivery_date, subtotal, freight_rate, total_amount) VALUES (3, 'b5741512-8fbc-47fa-9ac1-b530354fc0ff', 1, 7, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil',
        'DELIVERED', '2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120);

INSERT INTO purchase_item (id, purchase_id, product_id, quantity, unit_price, total_price, note) VALUES (4, 3, 2, 1, 110, 110, null);

INSERT INTO purchase (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_postal_code, 
    address_street, address_number, address_complement, address_district,
    status, created_at, confirmation_date, delivery_date, subtotal, freight_rate, total_amount) VALUES (4, '5c621c9a-ba61-4454-8631-8aabefe58dc2', 1, 7, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro',
        'DELIVERED', '2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4);

INSERT INTO purchase_item (id, purchase_id, product_id, quantity, unit_price, total_price, note) VALUES (5, 4, 3, 2, 87.2, 174.4, null);

INSERT INTO purchase (id, code, restaurant_id, user_client_id, payment_method_id, address_city_id, address_postal_code, 
    address_street, address_number, address_complement, address_district,
    status, created_at, confirmation_date, delivery_date, subtotal, freight_rate, total_amount) VALUES (5, '8d774bcf-b238-42f3-aef1-5fb388754d63', 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins',
        'DELIVERED', '2019-11-02 21:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2);

INSERT INTO purchase_item (id, purchase_id, product_id, quantity, unit_price, total_price, note) VALUES (6, 5, 3, 1, 87.2, 87.2, null);
