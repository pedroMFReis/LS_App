package pt.isel.ls.handlers.dbhandlers.post;

import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.post.PostMoviesResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

public class PostMoviesHandler extends DbHandler {
    //POST/movies
    public PostMoviesHandler() {
        super("POST/movies");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        //checking there are enough parameter
        if (p.size() != 2) {
            return false;
        }

        //checking if there is the correct parameters
        if (p.containsKey("title") && p.containsKey("releaseYear")) {
            return true;
        }

        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidParametersException {

        int mid = -1;//idMovie
        HashMap<String, String> p = cmdReq.getParameters().getProprieties();
        if (p == null || !checkParams(p)) {
            throw new MyInvalidParametersException();
        }
        String sql = "begin transaction; "
                + "INSERT INTO movie(title,releaseYear) VALUES (?,?); "
                + "commit;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setString(1, p.get("title"));
        prepStat.setInt(2, Integer.parseInt(p.get("releaseYear")));
        prepStat.executeUpdate();

        //got this command, by doing "\d movie", in psql terminal
        String sqlSelect = "select currval('movie_idmovie_seq'::regclass);";
        PreparedStatement preparedStatementSelectReview = cns.prepareStatement(sqlSelect);
        ResultSet rst = preparedStatementSelectReview.executeQuery();
        if (rst.next()) {
            mid = rst.getInt(1);
        }

        return new PostMoviesResult(
                "Created a new Movie with the ID " + mid + ".\n Title - " + p.get("title")
                        + "\n Release Year - " + p.get("releaseYear"));
    }

    public String getCommandDescription() {
        return "POST /movies:\n"
                + "\t Creates a new Movie, must be given the Following Parameters:\n"
                + "\t\t title - the movie's title\n"
                + "\t\t releaseYear - the movie's releaseYear";
    }
}
