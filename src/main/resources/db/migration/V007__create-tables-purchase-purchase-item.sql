create table purchase (
  id bigint not null auto_increment,
  subtotal decimal(10,2) not null,
  freight_rate decimal(10,2) not null,
  total_amount decimal(10,2) not null,
  status varchar(10) not null,
  
  created_at datetime not null,
  confirmation_date datetime,
  cancellation_date datetime,
  delivery_date datetime,
  
  restaurant_id bigint not null,
  user_client_id bigint not null,
  payment_method_id bigint not null,
  
  address_complement varchar(60),
  address_district varchar(60) not null,
  address_number varchar(20) not null,
  address_postal_code varchar(9) not null,
  address_street varchar(100) not null,
  
  address_city_id bigint not null,
  
  primary key(id)  
) engine=INNODB default charset=UTF8;	

create table purchase_item (
  id bigint not null auto_increment,
  quantity int not null,
  unit_price decimal(10,2) not null,
  total_price decimal(10,2) not null,
  note varchar(255),
  purchase_id bigint not null,
  product_id bigint not null,
  
  primary key(id),
  unique key uk_purchase_item_product (purchase_id, product_id)
) engine=InnoDB default charset=UTF8;

alter table purchase add constraint fk_purchase_restaurant foreign key (restaurant_id) references restaurant (id);
alter table purchase add constraint fk_purchase_user foreign key (user_client_id) references user (id);
alter table purchase add constraint fk_purchase_payment_method foreign key (payment_method_id) references payment_method (id);
alter table purchase add constraint fk_purchase_address_city foreign key (address_city_id) references city (id);

alter table purchase_item add constraint fk_purchase_item_purchase foreign key (purchase_id) references purchase (id);
alter table purchase_item add constraint fk_purchase_item_product foreign key (product_id) references product (id);
