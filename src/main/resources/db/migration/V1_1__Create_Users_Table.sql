create table users (
	id int not null auto_increment,
	name varchar(128) not null,
	password varchar(256) not null,
	email varchar(256) not null unique,
	created_at datetime default current_timestamp,
	updated_at datetime default current_timestamp on update current_timestamp,
	primary key(id)
)Engine=InnoDB, Charset=utf8;