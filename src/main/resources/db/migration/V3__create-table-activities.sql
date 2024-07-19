CREATE TABLE activities
(
    id        uniqueidentifier NOT NULL,
    title     varchar(255)     NOT NULL,
    occurs_at datetime         NOT NULL,
    trip_id   uniqueidentifier NOT NULL,
    CONSTRAINT pk_activities PRIMARY KEY (id)
)
    GO

ALTER TABLE activities
    ADD CONSTRAINT FK_ACTIVITIES_ON_TRIP FOREIGN KEY (trip_id) REFERENCES trips (id) ON DELETE CASCADE
    GO