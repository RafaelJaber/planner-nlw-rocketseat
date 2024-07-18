CREATE TABLE trips
(
    id           uniqueidentifier NOT NULL,
    destination  varchar(255)     NOT NULL,
    starts_at    datetime         NOT NULL,
    ends_at      datetime         NOT NULL,
    is_confirmed bit              NOT NULL,
    owner_name   varchar(255)     NOT NULL,
    owner_email  varchar(255)     NOT NULL,
    CONSTRAINT pk_trips PRIMARY KEY (id)
)
    GO