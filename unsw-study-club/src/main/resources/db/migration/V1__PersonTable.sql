-- create uuid generator
CREATE EXTENSION "uuid-ossp";
CREATE EXTENSION "pgcrypto";

CREATE TABLE Person  (
	id 				UUID NOT NULL,
	first_name 		varchar(15) NOT NULL,
	last_name 		varchar(15) NOT NULL,
	email 			varchar(50) NOT NULL UNIQUE CHECK (email ~ '^\w+@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+$'),
	password 		varchar(100) NOT NULL,
	nick_name 		varchar(30) NOT NULL CHECK (nick_name ~ '^\w+(\s\w+)*$'),
	profile_image 	varchar(100) default NULL,
	gender 			char(1) CHECK (gender ~ '^[F|M]$') default NULL,
	exp 			integer NOT NULL default 0,
	join_date 		timestamp default CURRENT_TIMESTAMP,

	primary key(id)
);

CREATE TABLE Course (
	id				UUID,
	code			char(8) NOT NULL CHECK (code ~ '[A-Z]{4}\d{4}') UNIQUE,
	name 			varchar(80) NOT NULL CHECK (name ~ '^\w+(\s\w+)*$'),
	handbook        varchar(200) UNIQUE,

	primary key (id)
);

CREATE TABLE Student (
	id 				UUID,
	study			UUID,
	start_date		timestamp DEFAULT CURRENT_TIMESTAMP,

	foreign key (id) references Person(id),
	foreign key (study) references Course(id)
);

CREATE TABLE Tutor (
	id 				UUID,
	teach 			UUID,
	likes			integer CHECK (likes >= 0),
	start_date      timestamp DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (id, teach),
	foreign key (id) references Person(id),
	foreign key (teach) references Course(id)
);


CREATE TABLE Admin (
	id				UUID,
	email			varchar(30) NOT NULL CHECK (email ~ '^\w+@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+$'),
	password		varchar(100) NOT NULL,

	primary key (id)
);



CREATE TABLE Video (
	id 				UUID,
	link			varchar(100) NOT NULL,
	title			varchar(20) NOT NULL,
	description		varchar(100),
	course 			UUID,
	uploader  		UUID,
	upload_date 	timestamp DEFAULT CURRENT_TIMESTAMP,

	primary key (id),
	foreign key (course) references Course(id),
	foreign key (uploader) references Person(id)
);


CREATE TABLE VideoRank (
	person		UUID,
	video 		UUID,
	rank 		integer NOT NULL CHECK (rank > 0 and rank <= 5),
	comment		varchar(100),

	UNIQUE (person, video),
	foreign key (person) references Person(id),
	foreign key (video) references Video(id)
);

CREATE TABLE Attachment (
	id			UUID,
	title		varchar(20) NOT NULL,
	content		varchar(1000) NOT NULL,
	course      UUID,
	uploader	UUID,
	upload_date timestamp DEFAULT CURRENT_TIMESTAMP,

	UNIQUE (title, course),
	primary key (id),
	foreign key (course) references Course(id),
	foreign key (uploader) references Person(id)
);


-- check if the uploader is tutor
create or replace function checkTutor() returns trigger
as $$
declare
	tutor UUID;
begin
	select id into tutor
	from Tutor
	where id = NEW.uploader;

	if (tutor is null) then
		raise Exception 'Uploader with id % is not tutor', NEW.uploader;
	end if;

	return NEW;
end;
$$ language 'plpgsql';

-- encrypt password before inserting into db
create or replace function encryptPassword() returns trigger
as $$
begin
	NEW.password := crypt(NEW.password, gen_salt('bf'));
	return NEW;
end;
$$ language 'plpgsql';



-- triggers

create trigger encryptPerson
before insert on Person
for each row
execute procedure encryptPassword();

create trigger encryptAdmin
before insert on Admin
for each row
execute procedure encryptPassword();


create trigger insertVideo
before insert on Video
for each row
execute procedure checkTutor();

create trigger insertAttachment
before insert on Attachment
for each row
execute procedure checkTutor();

