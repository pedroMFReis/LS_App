package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandHeaders;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.model.Users;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetUsersResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/*
 * GET /users - returns the list of users.
 * */

public class GetUsersHandler extends DbHandler {

    public GetUsersHandler() {
        super("GET/users");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException, MyInvalidCommandException {
        CommandHeaders headers = cmdReq.getHeaders();
        if (headers != null) {
            if (!(headers.checkHeaders())) {
                throw new MyInvalidCommandException("Invalid Headers");
            }
        }

        LinkedList<Users> usersList = new LinkedList<>();
        String sql = "select * from users;";
        PreparedStatement prepStat = cns.prepareStatement(sql);
        ResultSet rst = prepStat.executeQuery();
        while (rst.next()) {
            int idUser = rst.getInt("idUser");
            String name = rst.getString("name");
            String email = rst.getString("email");
            usersList.add(new Users(idUser, name, email));
        }

        return new GetUsersResult(usersList);
    }

    @Override
    public String getCommandDescription() {
        return "GET /users:\n\t Returns a List of all Users.";
    }
}
