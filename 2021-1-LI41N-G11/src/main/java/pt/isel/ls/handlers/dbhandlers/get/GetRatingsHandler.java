package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Ratings;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.get.GetRatingsResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;

public class GetRatingsHandler extends DbHandler {

    public GetRatingsHandler() {
        super("");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException {
        String sql = "select * from rating;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        ResultSet rst = prepStat.executeQuery();

        LinkedList<ModelElement> ratings = new LinkedList<>();
        while (rst.next()) {
            HashMap<String, Integer> stars = new HashMap<>();
            stars.put("star_1", rst.getInt("star_1"));
            stars.put("star_2", rst.getInt("star_2"));
            stars.put("star_3", rst.getInt("star_3"));
            stars.put("star_4", rst.getInt("star_4"));
            stars.put("star_5", rst.getInt("star_5"));
            int idMovie = rst.getInt("idMovie");

            Ratings r = new Ratings(idMovie, stars);

            ratings.add(r);
        }
        return new GetRatingsResult(ratings);
    }

    @Override
    public String getCommandDescription() {

        return "GET /movies/{mid}/ratings:\n\t Returns the Rating of the Movie with mid.";
    }
}
