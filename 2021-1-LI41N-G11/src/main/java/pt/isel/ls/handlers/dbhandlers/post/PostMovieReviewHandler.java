package pt.isel.ls.handlers.dbhandlers.post;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.post.PostMovieReviewResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

/*
* POST /movies/{mid}/reviews - creates a new review for the movie identified by mid, given the following parameters

        uid - user identifier
        reviewSummary - the review summary
        review - the complete review
        rating - the review rating
* */

public class PostMovieReviewHandler extends DbHandler {
    //POST /movies/1/reviews uid=3&reviewSummary=good+movie&review=good+good+very+good&rating=4
    public PostMovieReviewHandler() {
        super("POST/movies/1/reviews");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {

        //checking there are enough parameter
        if (p.size() != 4) {
            return false;
        }

        //checking if there is the correct parameters
        if (p.containsKey("uid") && p.containsKey("reviewSummary")
                && p.containsKey("review") && p.containsKey("rating")) {
            return true;
        }
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidParametersException {

        int idReview = -1;
        HashMap<String, String> p = cmdReq.getParameters().getProprieties();
        if (p == null || !checkParams(p)) {
            throw new MyInvalidParametersException();
        }

        int idMovie = Integer.parseInt(cmdReq.getPath().getParameters(1)[0]);
        int rating = Integer.parseInt(p.get("rating"));
        String str = "star_" + rating;

        PreparedStatement prepStatSelectRating = cns.prepareStatement(
                "SELECT * from rating where idMovie =" + idMovie + ";");
        ResultSet rstSelectRating = prepStatSelectRating.executeQuery();

        //add constraint
        PreparedStatement preparedStatementRating;
        //insert into rating (idMovie,star_1)VALUES (4,1);
        if (!rstSelectRating.next()) { //this means that. the movie exists, but isn't in rating table
            preparedStatementRating = cns.prepareStatement(
                    "begin transaction; "
                            + "insert into rating (idMovie, " + str + ")VALUES (" + idMovie + ",1); "
                            + "commit;");
        } else {
            preparedStatementRating = cns.prepareStatement(
                    "begin transaction; "
                            + "update rating SET " + str + " = " + str + "+1 where idMovie = " + idMovie + ";"
                            + " commit;");
        }
        preparedStatementRating.executeUpdate();

        //Add new Review
        String sql = "begin transaction; "
                + "insert into review (idUser,idMovie,reviewSummary,review,rating)"
                + "VALUES (?,?,?,?,?); commit;";
        PreparedStatement preparedStatementReview = cns.prepareStatement(sql);
        preparedStatementReview.setInt(1, Integer.parseInt(p.get("uid")));
        preparedStatementReview.setInt(2, idMovie);
        preparedStatementReview.setString(3, p.get("reviewSummary"));
        preparedStatementReview.setString(4, p.get("review"));
        preparedStatementReview.setInt(5, rating);
        preparedStatementReview.executeUpdate();
        //got this command, by doing "\d review", in psql terminal
        String sqlSelect = "select currval('review_idreview_seq'::regclass);";

        PreparedStatement preparedStatementSelectReview = cns.prepareStatement(sqlSelect);
        ResultSet rst = preparedStatementSelectReview.executeQuery();
        if (rst.next()) {
            idReview = rst.getInt(1);
        }

        //Inserts data on model
        insertInRatings(idMovie, rating);

        return new PostMovieReviewResult("Created a new Review With the ID " + idReview + ".");
    }

    @Override
    public String getCommandDescription() {
        return "POST /movie/{mid}/reviews:\n"
                + "\t Creates a Review for Movie mid, must be Given the Following Parameters:\n"
                + "\t\t idUser - the Review's idUser\n"
                + "\t\t idMovie - the Review's idMovie\n"
                + "\t\t reviewSummary - the Review's reviewSummary\n"
                + "\t\t review - the Review's review\n"
                + "\t\t idRating - the Review's idRating";
    }
}