package pt.isel.ls.handlers.dbhandlers.post;

import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.post.PostMovieRatingResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

/**
 * POST /movies/{mid}/ratings - submits a new rating for the movie identified by mid,
 * given the following parameters: rating - integer between 1 and 5
 * POST /movies/1/ratings rating=3
 */

public class PostMovieRatingHandler extends DbHandler {

    public PostMovieRatingHandler() {
        super("POST/movies/1/ratings");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) throws MyInvalidParametersException {
        //checking there are enough parameter
        if (p.size() != 1) {
            return true;
        }
        //checking if there is the correct parameters
        if (p.containsKey("rating")) {
            int rating = Integer.parseInt(p.get("rating"));
            if (rating > 5 || rating < 1) {
                throw new MyInvalidParametersException("Invalid Rating Value, only Accepts Values between 1 and 5!");
            }
        }
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidParametersException {

        HashMap<String, String> p = cmdReq.getParameters().getProprieties();

        if (p == null || checkParams(p)) {
            throw new MyInvalidParametersException();
        }

        int idMovie = Integer.parseInt(cmdReq.getPath().get(2));
        int rating = Integer.parseInt(p.get("rating"));
        String ratingStr = "star_" + rating;

        PreparedStatement prepStatSelectRating = cns.prepareStatement("SELECT * from rating where idMovie = ?;");
        prepStatSelectRating.setInt(1, idMovie);
        ResultSet rstSelectRating = prepStatSelectRating.executeQuery();

        PreparedStatement preparedStatementRating;
        if (!rstSelectRating.next()) { //this means that. the movie exists, but isn't in rating table
            preparedStatementRating = cns.prepareStatement(
                    "begin transaction; "
                            + "insert into rating (idMovie, " + ratingStr + ")VALUES (" + idMovie + ",1); "
                            + "commit;");
        } else {
            preparedStatementRating = cns.prepareStatement(
                    "begin transaction ;"
                            + "update rating SET " + ratingStr + " = " + ratingStr
                            + "+1 where idMovie = " + idMovie + ";"
                            + " commit;");
        }
        preparedStatementRating.executeUpdate();

        //Inserts data on model
        insertInRatings(idMovie, rating);


        return new PostMovieRatingResult("Posted new Rating:\n Movie ID " + idMovie
                + ".\n Rating - " + rating);
    }

    public String getCommandDescription() {
        return "POST /movies/{mid}/ratings:"
                + "\n\t Creates a Review for Movie mid, Must be Given the Following Parameters:"
                + "\n\t\t rating - number between 1 and 5 that represents the rating.";
    }
}
