CREATE table IF NOT EXISTS USERS
(
    USER_ID       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_EMAIL    VARCHAR(100) UNIQUE NOT NULL,
    USER_LOGIN    VARCHAR(50) UNIQUE  NOT NULL,
    USER_NAME     VARCHAR(50) NOT NULL,
    USER_BIRTHDAY DATE
);

CREATE table IF NOT EXISTS GENRES
(
    GENRE_ID   BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE_NAME VARCHAR(64) NOT NULL
);

CREATE table IF NOT EXISTS MPA
(
    MPA_ID   INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    MPA_NAME VARCHAR(50) NOT NULL
);

CREATE table IF NOT EXISTS FILMS
(
    FILM_ID           INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    FILM_NAME         VARCHAR(500) NOT NULL,
    FILM_RELEASE_DATE DATE,
    FILM_DESCRIPTION  VARCHAR(200),
    FILM_DURATION     INT,
    FILM_MPA          BIGINT REFERENCES MPA(MPA_ID) ON DELETE NO ACTION
);

CREATE table IF NOT EXISTS FILM_GENRES
(
    FILM_ID  BIGINT REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    GENRE_ID BIGINT REFERENCES GENRES (GENRE_ID) ON DELETE CASCADE,
    UNIQUE (FILM_ID, GENRE_ID)
);

CREATE table IF NOT EXISTS FRIENDS
(
    USER_ID   INT REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FRIEND_ID INT REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    UNIQUE (USER_ID, FRIEND_ID)
);

CREATE TABLE IF NOT EXISTS LIKES
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_ID BIGINT REFERENCES USERS (USER_ID) ON DELETE CASCADE,
    FILM_ID BIGINT REFERENCES FILMS (FILM_ID) ON DELETE CASCADE,
    UNIQUE (USER_ID, FILM_ID)
);
