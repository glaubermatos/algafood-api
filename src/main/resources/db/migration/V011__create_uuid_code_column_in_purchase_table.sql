alter table purchase add column code varchar(36) not null after id;
update purchase set code = uuid();

alter table purchase add constraint uk_purchase_code unique (code);