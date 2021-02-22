CREATE TABLE alarms
(
    id         integer auto_increment not null,
    hour       integer                not null,
    minute     integer                not null,
    enabled    boolean                not null,
    repetition integer                not null default 0
);