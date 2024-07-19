CREATE TABLE participants
(
    id           uniqueidentifier NOT NULL,
    is_confirmed bit              NOT NULL,
    name         varchar(255)     NOT NULL,
    email        varchar(255)     NOT NULL,
    trip_id      uniqueidentifier NOT NULL,
    CONSTRAINT pk_participants PRIMARY KEY (id)
)
    GO

ALTER TABLE participants
    ADD CONSTRAINT FK_PARTICIPANTS_ON_TRIP FOREIGN KEY (trip_id) REFERENCES trips (id)
    GO