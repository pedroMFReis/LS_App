package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;

import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Ratings;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.get.GetMovieRatingResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * GET /movies/{mid}/ratings - returns the rating information for the movie identified by mid.
 * This rating information include:
 * The rating average
 * The number of votes for each rating value
 */

public class GetMovieRatingHandler extends DbHandler {

    public GetMovieRatingHandler() {
        super("GET/movies/1/ratings");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException {
        int idMovie = Integer.parseInt(cmdReq.getPath().getParameters(1)[0]);
        String sql = "select * from rating where idMovie = ?;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idMovie);
        ResultSet rst = prepStat.executeQuery();

        LinkedList<ModelElement> list = new LinkedList<>();
        if (rst.next()) {

            idMovie = rst.getInt("idMovie");
            HashMap<String, Integer> stars = new HashMap<>();
            stars.put("star_1", rst.getInt("star_1"));
            stars.put("star_2", rst.getInt("star_2"));
            stars.put("star_3", rst.getInt("star_3"));
            stars.put("star_4", rst.getInt("star_4"));
            stars.put("star_5", rst.getInt("star_5"));
            list.add(new Ratings(idMovie, stars));
        }

        return new GetMovieRatingResult(list);

    }

    @Override
    public String getCommandDescription() {

        return "GET /movies/{mid}/ratings:\n\t Returns the Rating of the Movie with mid.";
    }
}