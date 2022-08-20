alter table payment_method add updated_at datetime null;
update payment_method set updated_at = utc_timestamp;
alter table payment_method modify updated_at datetime not null;