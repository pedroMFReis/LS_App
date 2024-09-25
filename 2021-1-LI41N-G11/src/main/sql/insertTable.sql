begin transaction;

INSERT INTO users(name,email) VALUES
	('Bernardo','user1@gmail.com'),
	('Catia','hash@isel.pt.com'),
	('Pedro','windows@google.com');


INSERT INTO movie(title,releaseYear) VALUES
	('The godfather',1972),
	('2 Fast 2 Furious', 2003),
	('The Lord of The Rings: The Two Towers', 2002);

--INSERT INTO rating VALUES (1,0,0,0,1,0),(2,0,0,0,1,0),(3,0,0,0,0,1);

INSERT INTO review (idUser,idMovie,reviewSummary,review,rating) VALUES
    (1,1,'Best Movie Ever', 'Best Movie Ever, because bla bla, bla',4),
    (2, 2, 'Amazing movie', 'One of the  bests movies I have watch about cars', 4),
    (3,3,'Perfect.', 'Best Fantasy Movie Ever.',5);

commit transaction;