package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetUserReviewByIdResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * GET /users/{uid}/reviews/{rid} - returns the full
 * information for the review rid made by the user identified by uid.
 */

public class GetUserReviewByIdHandler extends DbHandler {

    public GetUserReviewByIdHandler() {
        super("GET/users/1/reviews/1");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException {

        String[] aux = cmdReq.getPath().getParameters(2);
        int idUser = Integer.parseInt(aux[0]);
        int idReview = Integer.parseInt(aux[1]);

        String sql = "select * from review where idUser = ? AND idReview = ?;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idUser);
        prepStat.setInt(2, idReview);
        ResultSet rst = prepStat.executeQuery();
        if (!rst.next()) {
            throw new MyInvalidPathException("No such idUser or idReview in BD.");
        }

        idUser = rst.getInt("idUser");
        int idMovie = rst.getInt("idMovie");
        String reviewSummary = rst.getString("reviewSummary");
        String review = rst.getString("review");
        int rating = rst.getInt("rating");

        LinkedList<ModelElement> list = new LinkedList<>();
        list.add(new Review(idReview, idUser, idMovie, reviewSummary, review, rating));

        return new GetUserReviewByIdResult(list);

        //return new GetUserReviewByIdResult(rst);
    }

    @Override
    public String getCommandDescription() {
        return "GET /users/{uid}/reviews/{rid}:\n\t Returns the Review rid made by the User uid.";
    }
}
