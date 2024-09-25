package pt.isel.ls.handlers.dbhandlers.delete;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.delete.DeleteMovieReviewResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DeleteMovieReview extends DbHandler {

    public DeleteMovieReview() {
        super("DELETE/movies/1/reviews/1");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns)
            throws SQLException, MyInvalidPathException, MyInvalidParametersException {

        String[] aux = cmdReq.getPath().getParameters(2);
        int idMovie = Integer.parseInt(aux[0]);
        int idReview = Integer.parseInt(aux[1]);

        String sql = "select rating from review where idMovie = ? " + "and idReview = ?;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idMovie);
        prepStat.setInt(2, idReview);
        ResultSet rst = prepStat.executeQuery();
        rst.next();
        int rating = rst.getInt("rating");
        if (rst == null) {
            throw new MyInvalidPathException("No such idMovie or idReview in Db.");
        }

        removeFromRatings(idMovie, rating);

        sql = "delete from review where idMovie = ? " + "and idReview = ?;";
        prepStat = cns.prepareStatement(sql);
        prepStat.setInt(1, idMovie);
        prepStat.setInt(2, idReview);
        prepStat.execute();

        return new DeleteMovieReviewResult(idMovie, idReview);
    }

    @Override
    public String getCommandDescription() {
        return "DELETE /movies/{mid}/reviews/{rid}:\n\t Deletes Review rid of Movie mid.";
    }
}
