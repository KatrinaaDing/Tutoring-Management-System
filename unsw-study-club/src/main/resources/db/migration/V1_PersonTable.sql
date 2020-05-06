CREATE TABLE Person  (
	id 				UUID NOT NULL,
	first_name 		varchar(10) NOT NULL,
	last_name 		varchar(10) NOT NULL,
	email 			varchar(50) NOT NULL UNIQUE CHECK (email ~ '^\w+@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+$'),
	password 		varchar(100) NOT NULL,
	nick_name 		varchar(30) NOT NULL CHECK (nick_name ~ '^\w+(\s\w+)*$'),
	profile_image 	varchar(100) default NULL,
	gender 			char(1) CHECK (gender ~ '^[F|M]$') default NULL,
	exp 			integer NOT NULL default 0,
	join_date 		timestamp default CURRENT_TIMESTAMP,

	primary key(id)
);