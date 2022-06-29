create table payment_method (
  id bigint not null auto_increment,
  description varchar(60) not null,
  
  primary key (id)
) engine=InnoDB default charset=UTF8;

create table permission (
  id bigint not null auto_increment,
  description varchar(60),
  name varchar(100) not null,
  
  primary key (id)
) engine=InnoDB default charset=UTF8;

create table product (
  id bigint not null auto_increment,
  active tinyint(1) not null,
  description text not null,
  name varchar(80) not null,
  price decimal(10,2) not null,
  restaurant_id bigint not null,
  
  primary key (id)
) engine=InnoDB default charset=UTF8;

create table restaurant (
  id bigint not null auto_increment,
  address_complement varchar(60),
  address_district varchar(60),
  address_number varchar(20),
  address_postal_code varchar(9),
  address_street varchar(100),
  created_at datetime not null,
  freight_rate decimal(10,2) not null,
  name varchar(80) not null,
  updated_at datetime not null,
  address_city_id bigint,
  cuisine_id bigint not null,
  
  primary key (id)
) engine=InnoDB default charset=UTF8;

create table restaurant_payment_method (
  restaurant_id bigint not null,
  payment_method_id bigint not null
) engine=InnoDB default charset=UTF8;

create table user (
  id bigint not null auto_increment,
  created_at datetime not null,
  email varchar(255) not null,
  name varchar(80) not null,
  password varchar(255) not null,
  
  primary key (id)
) engine=InnoDB default charset=UTF8;

create table user_group_permission (
  user_group_id bigint not null,
  permission_id bigint not null
) engine=InnoDB default charset=UTF8;

create table user_user_group (
  user_id bigint not null,
  user_group_id bigint not null
) engine=InnoDB default charset=UTF8;

create table user_group (
  id bigint not null auto_increment,
  name varchar(255) not null,
  
  primary key (id)
) engine=InnoDB default charset=UTF8;

alter table product add constraint fk_product_restaurant foreign key (restaurant_id) references restaurant (id);
alter table restaurant add constraint fk_restaurant_city foreign key (address_city_id) references city (id);
alter table restaurant add constraint fk_restaurant_cuisine foreign key (cuisine_id) references cuisine (id);
alter table restaurant_payment_method add constraint fk_restaurant_payment_method_payment_method foreign key (payment_method_id) references payment_method (id);
alter table restaurant_payment_method add constraint fk_restaurant_payment_method_restaurant foreign key (restaurant_id) references restaurant (id);
alter table user_group_permission add constraint fk_user_group_permission_permission foreign key (permission_id) references permission (id);
alter table user_group_permission add constraint fk_user_group_permission_user_group foreign key (user_group_id) references user_group (id);
alter table user_user_group add constraint fk_user_user_group_user_group foreign key (user_group_id) references user_group (id);
alter table user_user_group add constraint fk_user_user_group_user foreign key (user_id) references user (id);
