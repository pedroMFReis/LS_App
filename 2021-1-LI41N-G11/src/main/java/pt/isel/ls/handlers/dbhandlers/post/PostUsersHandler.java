package pt.isel.ls.handlers.dbhandlers.post;

import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.command.CommandParam;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.post.PostUsersResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;

public class PostUsersHandler extends DbHandler {

    //POST /users name=First+Last&email=example@email.com
    public PostUsersHandler() {

        super("POST/users");
    }


    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        //checking there are enough parameter
        if (p.size() != 2) {
            return false;
        }

        //checking if there is the correct parameters
        if (p.containsKey("name") && p.containsKey("email")) {
            return true;
        }

        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidParametersException {

        int uid = -1;
        CommandParam params = cmdReq.getParameters();
        if (params == null) {
            throw new MyInvalidParametersException("No parameters passed in command");
        }
        HashMap<String, String> p = params.getProprieties();
        if (!(checkParams(p))) {
            throw new MyInvalidParametersException();
        }

        String sql = "begin transaction; "
                + "INSERT INTO users(name,email) VALUES (?,?); "
                + "commit; ";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        prepStat.setString(1, p.get("name"));
        prepStat.setString(2, p.get("email"));
        prepStat.executeUpdate();

        //got this command, by doing "\d user", in psql terminal
        String sqlSelect = "select currval('users_iduser_seq'::regclass);";
        PreparedStatement preparedStatementSelectReview = cns.prepareStatement(sqlSelect);
        ResultSet rst = preparedStatementSelectReview.executeQuery();
        if (rst.next()) {
            uid = rst.getInt(1);
        }
        return new PostUsersResult(
                "Created a new User with the ID " + uid + ".\n Name - " + p.get("name")
                        + "\n Email - " + p.get("email"));
    }

    @Override
    public String getCommandDescription() {
        return "POST /users:\n"
                + "\t Creates a new User, Must be Given the Following Parameters:\n"
                + "\t\t name - the user's name\n"
                + "\t\t email - the user's email";
    }
}
