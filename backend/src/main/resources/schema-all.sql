DROP TABLE tracks IF EXISTS;

create table tracks
(
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    title varchar(512) not null,
    link varchar(512) not null,
    duration int not null,
    explicit_lyrics boolean not null,
    preview varchar(512) not null,
    artist varchar(512) not null,
    album varchar(512) not null
);