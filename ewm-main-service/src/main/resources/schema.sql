CREATE TABLE IF NOT EXISTS users
(
    user_id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(256)                            NOT NULL,
    email VARCHAR(256)                            NOT NULL,
    CONSTRAINT pk_user       PRIMARY KEY (user_id),
    CONSTRAINT uq_user_email UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS categories
(
    category_id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  VARCHAR(256)                            NOT NULL,
    CONSTRAINT pk_category      PRIMARY KEY (category_id),
    CONSTRAINT uq_category_name UNIQUE (name)
    );


CREATE TABLE IF NOT EXISTS events
(
    event_id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL,
    confirmed_requests INT,
    created_on         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    description        VARCHAR(7000)                           NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL,
    location_lat       FLOAT                                   NOT NULL,
    location_lon       FLOAT                                   NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  INT                                     NOT NULL,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN                                 NOT NULL,
    state              VARCHAR(32)                             NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    CONSTRAINT pk_event                PRIMARY KEY (event_id),
    CONSTRAINT uq_event_title          UNIQUE (title),
    CONSTRAINT fk_event_to_category_id FOREIGN KEY (category_id)  REFERENCES categories (category_id),
    CONSTRAINT fk_event_to_user_id     FOREIGN KEY (initiator_id) REFERENCES users (user_id)
    );

CREATE TABLE IF NOT EXISTS requests
(
    request_id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     BIGINT                                  NOT NULL,
    requester_id BIGINT                                  NOT NULL,
    status       VARCHAR(32)                             NOT NULL,
    CONSTRAINT pk_request                 PRIMARY KEY (request_id),
    CONSTRAINT fk_request_to_event_id     FOREIGN KEY (event_id)     REFERENCES events (event_id),
    CONSTRAINT fk_request_to_requester_id FOREIGN KEY (requester_id) REFERENCES users (user_id)
    );

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    pinned BOOLEAN,
    title  VARCHAR(120)                            NOT NULL,
    CONSTRAINT pk_compilation       PRIMARY KEY (compilation_id),
    CONSTRAINT uq_compilation_title UNIQUE (title)
    );

CREATE TABLE IF NOT EXISTS compilations_events
(
    event_id       BIGINT,
    compilation_id BIGINT,
    CONSTRAINT pk_events_compilations    PRIMARY KEY (event_id, compilation_id),
    CONSTRAINT fk_between_event_id       FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_between_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (compilation_id)
    );

CREATE TABLE IF NOT EXISTS comments
(
    comment_id      BIGINT      GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_on      TIMESTAMP WITHOUT TIME ZONE                  NOT NULL,
    text            VARCHAR(1024)                                NOT NULL,
    event_id        BIGINT                                       NOT NULL,
    user_id         BIGINT                                       NOT NULL,
    CONSTRAINT pk_comments    PRIMARY KEY (comment_id),
    CONSTRAINT fk_between_event_id       FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_between_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);