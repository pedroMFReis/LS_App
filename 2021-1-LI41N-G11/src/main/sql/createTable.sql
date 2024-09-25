
begin transaction;

    drop table if exists review;
    drop table if exists rating;
    drop table if exists movie;
    drop table if exists users;

    CREATE TABLE users (
        idUser BIGSERIAL PRIMARY KEY,
 	    name char(50) NOT NULL,
 	    email char(50) NOT NULL,
 	    UNIQUE(email)
    );

    CREATE TABLE movie (
        idMovie BIGSERIAL PRIMARY KEY,
        title char(50) NOT NULL,
        releaseYear integer NOT NULL CHECK(releaseYear BETWEEN 1900 AND 2020 ),
        UNIQUE(title,releaseYear)
    );

    CREATE TABLE rating(
        idMovie integer PRIMARY KEY,
        star_1 integer DEFAULT 0,
        star_2 integer DEFAULT 0,
        star_3 integer DEFAULT 0,
        star_4 integer DEFAULT 0,
        star_5 integer DEFAULT 0,
        FOREIGN KEY (idMovie) REFERENCES movie(idMovie)
    );

    CREATE TABLE review (
        idReview BIGSERIAL,
        idUser integer,
 	    idMovie integer,
 	    reviewSummary char(50) NOT NULL,
 	    review char(200) NOT NULL,
 	    rating integer NOT NULL CHECK(rating BETWEEN 1 AND 5 ),
 	    PRIMARY KEY(idMovie,idUser,idReview),
 	    FOREIGN KEY (idUser) REFERENCES users(idUser),
 	    FOREIGN KEY (idMovie) REFERENCES movie(idMovie)
    );

    commit transaction;