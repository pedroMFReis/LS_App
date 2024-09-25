package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Movie;
import pt.isel.ls.model.Review;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.get.GetMovieByIdResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * GET /movies/{mid} - returns the detailed information for the movie identified by mid.
 */

public class GetMovieByIdHandler extends DbHandler {

    public GetMovieByIdHandler() {
        super("GET/movies/1");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException {
        int idMovie = Integer.parseInt(cmdReq.getPath().getParameters(1)[0]);

        String sql = "select * from (select movie.idMovie as movieId, movie.title, movie.releaseYear, "
                + "review.idReview, review.idMovie, review.idUser from movie left join review "
                + "on movie.idMovie = ? and movie.idMovie = review.idMovie) thismovie "
                + "where thismovie.movieId = ?;";

        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idMovie);
        prepStat.setInt(2, idMovie);

        ResultSet rst = prepStat.executeQuery();
        if (!rst.next()) {
            throw new MyInvalidPathException("No such idMovie in DB");
        }

        idMovie = rst.getInt("movieId");
        String title = rst.getString("title");
        int releaseYear = rst.getInt("releaseYear");

        LinkedList<ModelElement> list = new LinkedList<>();
        Movie movie = new Movie(idMovie, title, releaseYear);


        int idReview = rst.getInt("idReview");
        int idUser = rst.getInt("idUser");
        if (!(idReview == 0 || idUser == 0)) {
            movie.addReview(new Review(idReview, idUser, movie.getIdMovie(), null, null, -1));
            while (rst.next()) {
                idReview = rst.getInt("idReview");
                idUser = rst.getInt("idUser");
                movie.addReview(new Review(idReview, idUser, movie.getIdMovie(), null, null, -1));
            }
        }

        list.add(movie);

        return new GetMovieByIdResult(list);
    }

    @Override
    public String getCommandDescription() {

        return "GET /movies/{mid}:\n\t Returns Information on the Movie with mid.";
    }
}
