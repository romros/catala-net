drop table T_SEARCH_RESULTS if exists;
drop table T_SEARCH if exists;
drop table T_URIs if exists;
drop table T_QUERY if exists;
drop table T_DEVICE if exists;

-- Taula de dispositius
create table T_DEVICE (
    ID integer identity primary key,
    DEVICE_NAME varchar(255) not null,
    OPERATING_SYSTEM varchar(255) not null,
    UNIQUE_IDENTIFIER VARCHAR(255) NOT NULL UNIQUE
);

-- Taula de queries (cerques)
create table T_QUERY (
    ID integer identity primary key,
    QUERY_TEXT varchar(255) not null unique, 
    ACTIVE boolean default true  not null   
);



-- Taula de registres de cerca
create table T_SEARCH (
    ID integer identity primary key,
    QUERY_ID integer,
    DEVICE_ID integer,
    TIMESTAMP datetime not null,
    IP_ORIGIN varchar(45),
    ACCEPT_LANGUAGE varchar(255),
    SCREENSHOT BLOB
);

-- Taula de URIs
create table T_URIs (
    ID integer identity primary key,
    URL varchar(1000) not null unique, 
    TITLE varchar(500) not null, 
    DESCRIPTION varchar(2000), 
    LANGUAGE varchar(10),
    FIRST_FOUND datetime not null,
    LAST_MODIFIED datetime not null
);

-- Taula de resultats
create table T_SEARCH_RESULTS (
    ID integer identity primary key,
    SEARCH_ID integer,
    URI_ID integer,
    POSITION integer not null
);

-- Claus foranes
alter table T_SEARCH add constraint FK_SEARCH_QUERY foreign key (QUERY_ID) references T_QUERY(ID) on delete cascade;
alter table T_SEARCH add constraint FK_SEARCH_DEVICE foreign key (DEVICE_ID) references T_DEVICE(ID) on delete cascade;
alter table T_SEARCH_RESULTS add constraint FK_SEARCH_RESULTS_SEARCH foreign key (SEARCH_ID) references T_SEARCH(ID) on delete cascade;
alter table T_SEARCH_RESULTS add constraint FK_SEARCH_RESULTS_URIS foreign key (URI_ID) references T_URIs(ID) on delete cascade;
