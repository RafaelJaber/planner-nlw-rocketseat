CREATE TABLE links
(
    id      uniqueidentifier NOT NULL,
    url     varchar(255)     NOT NULL,
    title   varchar(255)     NOT NULL,
    trip_id uniqueidentifier NOT NULL,
    CONSTRAINT pk_links PRIMARY KEY (id)
)
    GO

ALTER TABLE links
    ADD CONSTRAINT FK_LINKS_ON_TRIP FOREIGN KEY (trip_id) REFERENCES trips (id) ON DELETE CASCADE
    GO