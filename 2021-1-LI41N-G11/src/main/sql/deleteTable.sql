begin transaction;

DELETE FROM review;
DELETE FROM rating;
DELETE FROM movie;
DELETE FROM users;

commit transaction;