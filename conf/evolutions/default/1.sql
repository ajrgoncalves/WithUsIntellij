 --  Created by Ebean DDL
 -- To stop Ebean DDL generation, remove this comment and start using Evolutions

 -- !Ups
drop table if EXISTS user;

create table user (
  id                            integer not null  AUTO_INCREMENT,
  name                          varchar(50),
  lastName                      varchar(50),
  age                           DATE,
  email                         varchar(50),
  password                      varchar(50),
  constraint pk_user primary key (id)
);
-- create sequence user_seq;


-- !Downs

-- drop table if exists user;
-- drop sequence if exists user_seq;

