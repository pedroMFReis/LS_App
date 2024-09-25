package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.model.Review;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetUserReviewResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/*
 * GET /users/{uid}/reviews - returns all the reviews made from the user identified by uid.
 * The information for each review must not include the full review text.
 * */

public class GetUserReviewsHandler extends DbHandler {

    public GetUserReviewsHandler() {
        super("GET/users/1/reviews");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException {
        LinkedList<Review> reviewsList = new LinkedList<>();
        int idUser = Integer.parseInt(cmdReq.getPath().getParameters(1)[0]);

        String sql = "select * from review where idUser = ?;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idUser);
        ResultSet rst = prepStat.executeQuery();
        if (rst == null) {
            throw new MyInvalidPathException("No such idUser in BD.");
        }


        while (rst.next()) {
            int idReview = rst.getInt("idReview");
            idUser = rst.getInt("idUser");
            int idMovie = rst.getInt("idMovie");
            String reviewSummary = rst.getString("reviewSummary");
            int rating = rst.getInt("rating");
            Review r = new Review(idReview, idUser, idMovie, reviewSummary, null, rating);

            reviewsList.add(r);
        }
        return new GetUserReviewResult(reviewsList);
    }

    @Override
    public String getCommandDescription() {
        return "GET /user/{uid}/reviews:\n\t Returns all Reviews made by User uid.";
    }
}
