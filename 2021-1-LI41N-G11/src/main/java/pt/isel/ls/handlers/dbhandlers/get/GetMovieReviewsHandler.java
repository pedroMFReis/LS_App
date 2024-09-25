package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.model.Review;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetMovieReviewResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/*GET /movies/{mid}/reviews - returns all the reviews for the movie identified by mid.
 The information for each review must not include the full review text.
* */
public class GetMovieReviewsHandler extends DbHandler {

    public GetMovieReviewsHandler() {
        super("GET/movies/1/reviews");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException {
        LinkedList<Review> moviesReviewsList = new LinkedList<>();
        int idMovie = Integer.parseInt(cmdReq.getPath().getParameters(1)[0]);

        String sql = "select idReview,idUser,idMovie,reviewSummary,rating "
                + "from review where idMovie = ?;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idMovie);
        ResultSet rst = prepStat.executeQuery();
        if (rst == null) {
            throw new MyInvalidPathException("No such idMovie in BD.");
        }

        String str = "";
        Review r;
        while (rst.next()) {
            int idReview = rst.getInt("idReview");
            int idUser = rst.getInt("idUser");
            idMovie = rst.getInt("idMovie");
            String reviewSummary = rst.getString("reviewSummary");
            int rating = rst.getInt("rating");
            r = new Review(idReview, idUser, idMovie, reviewSummary, null, rating);
            moviesReviewsList.add(r);
        }
        return new GetMovieReviewResult(moviesReviewsList);
    }

    @Override
    public String getCommandDescription() {
        return "GET /movies/{mid}/reviews:\n\t Returns all the Reviews of Movie mid.";
    }
}
