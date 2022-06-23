INSERT INTO cuisine (id, name) values (1, 'Tailandesa');
INSERT INTO cuisine (id, name) values (2, 'Indiana');

INSERT INTO restaurant (name, freight_rate, cuisine_id) values ('Thai Gourmet', 10, 1);
INSERT INTO restaurant (name, freight_rate, cuisine_id) values ('Thai Delivery', 9.50, 1);
INSERT INTO restaurant (name, freight_rate, cuisine_id) values ('Tuk Tuk Comida Indiana', 15, 2);

INSERT INTO state (id, name) VALUES (1, 'Bahia');
INSERT INTO state (id, name) VALUES (2, 'São Paulo');

INSERT INTO city (id, name, state_id) VALUES (1, 'Manoel Vitorino', 1);
INSERT INTO city (id, name, state_id) VALUES (2, 'Santa Inês', 1);
INSERT INTO city (id, name, state_id) VALUES (3, 'Santo André', 2);

INSERT INTO payment_method (id, description) VALUES (1, 'Cartão de crédito');
INSERT INTO payment_method (id, description) VALUES (2, 'Cartão de débito');
INSERT INTO payment_method (id, description) VALUES (3, 'Dinheiro');

INSERT INTO permission (id, name, description) VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
INSERT INTO permission (id, name, description) VALUES (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');
