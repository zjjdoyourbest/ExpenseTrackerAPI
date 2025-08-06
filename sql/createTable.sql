CREATE TABLE users(
	id SERIAL PRIMARY KEY,
	username VARCHAR(100) not null,
	password VARCHAR(100) not null,
	createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	lock BOOLEAN DEFAULT FALSE,
	isDeleted BOOLEAN DEFAULT FALSE
)

CREATE TABLE typeslist (
	id SERIAL PRIMARY KEY,
	type_name VARCHAR(100)
)

CREATE TABLE expense (
	id SERIAL PRIMARY KEY,
	type_id Integer not null,
	amount numeric(18,4) not null default 0.0,
	detial VARCHAR(200),
	expense_date DATE,
	create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	CONSTRAINT fk_type_id FOREIGN KEY (type_id) REFERENCES typeslist(id)
)