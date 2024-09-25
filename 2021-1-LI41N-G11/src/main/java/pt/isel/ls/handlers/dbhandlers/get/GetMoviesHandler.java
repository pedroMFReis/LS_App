package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandParam;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.model.Movie;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetMoviesResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * GET /movies - returns a list with all movies
 */

public class GetMoviesHandler extends DbHandler {


    public GetMoviesHandler() {
        super("GET/movies");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException {

        LinkedList<Movie> moviesList = new LinkedList<>();

        CommandParam params = cmdReq.getParameters();
        String str = "SELECT * from movie ";

        int skip = -1;
        int top = -1;
        if (params != null && checkPaging(params.getProprieties())) {
            HashMap<String, String> paramsHash = params.getProprieties();

            if (paramsHash.containsKey("skip")) {
                skip = Integer.parseInt(paramsHash.get("skip"));
                str += "where idMovie > " + paramsHash.get("skip");
            }
            if (paramsHash.containsKey("top")) {
                top = Integer.parseInt(paramsHash.get("top"));
                str += "ORDER BY idMovie ASC LIMIT " + paramsHash.get("top");
            }

        }
        str += ";";
        PreparedStatement prepStat = cns.prepareStatement(str);

        ResultSet rst = prepStat.executeQuery();

        while (rst.next()) {
            int idMovie = rst.getInt("idMovie");
            String title = rst.getString("title");
            int releaseYear = rst.getInt("releaseYear");
            moviesList.add(new Movie(idMovie, title, releaseYear));
        }
        return new GetMoviesResult(moviesList, skip, top);

    }

    private boolean checkPaging(HashMap<String, String> hashMap) {
        return (hashMap.containsKey("skip") || hashMap.containsKey("top"));
    }

    @Override
    public String getCommandDescription() {
        return "GET /movies:\n Returns the list of all Movies.\n"
                + "\t Optional Parameters: \n"
                + "\t\t skip - Skips the n first movies in the Db \n"
                + "\t\t top - Shows only n movies at a time.";
    }
}
