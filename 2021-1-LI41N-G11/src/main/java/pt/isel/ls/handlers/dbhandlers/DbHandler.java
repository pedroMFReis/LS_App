package pt.isel.ls.handlers.dbhandlers;

import pt.isel.ls.MovieReviewApp;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.model.Ratings;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.CommandHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class DbHandler implements CommandHandler {

    String pathTemplate;

    public DbHandler(String p) {
        pathTemplate = p;
    }

    protected abstract boolean checkParams(HashMap<String, String> p) throws MyInvalidParametersException;

    public abstract CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException, MyInvalidCommandException;

    public String getPathTemplate() {
        return pathTemplate;
    }

    protected void insertInRatings(int idMovie, int rating) {
        HashMap<Integer, Ratings> hashMapRatings = MovieReviewApp.getRatings();
        Ratings ratings = searchRatings(hashMapRatings, idMovie);

        ratings.incrementStar("star_" + rating);
    }

    protected void removeFromRatings(int idMovie, int rating) {
        HashMap<Integer, Ratings> hashMapRatings = MovieReviewApp.getRatings();
        Ratings ratings = searchRatings(hashMapRatings, idMovie);

        ratings.decrementStar("star_" + rating);
    }

    protected Ratings searchRatings(HashMap<Integer, Ratings> ratingsMap, int idMovie) {
        if (ratingsMap.containsKey(idMovie)) {
            return ratingsMap.get(idMovie);
        } else {
            Ratings ratings = new Ratings(idMovie, new HashMap<>());
            ratingsMap.put(idMovie, ratings);
            return ratings;
        }
    }
}
