package pt.isel.ls.handlers.dbhandlers.get;

import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;
import pt.isel.ls.model.Users;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.handlers.dbhandlers.DbHandler;
import pt.isel.ls.results.get.GetUserByIdResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.LinkedList;

/*GET /users/{uid} - returns the details for the user identified by uid*/

public class GetUsersByIdHandler extends DbHandler {

    public GetUsersByIdHandler() {
        super("GET/users/1");
    }

    @Override
    protected boolean checkParams(HashMap<String, String> p) {
        return false;
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws SQLException,
            MyInvalidPathException, MyInvalidParametersException {

        int idUser = Integer.parseInt(cmdReq.getPath().getParameters(1)[0]);
        //PreparedStatement prepStat = cns.prepareStatement("select * from users where idUser = ?;");
        PreparedStatement prepStat = cns.prepareStatement("select * from (select users.idUser as userId, "
                + "users.name, users.email ,review.idReview, review.idUser, review.reviewSummary "
                + "from users left join review on users.idUser = ? "
                + "and users.idUser = review.idUser) thisuser where thisuser.userId = ?;");

        prepStat.setInt(1, idUser);
        prepStat.setInt(2, idUser);

        ResultSet rst = prepStat.executeQuery();
        if (!rst.next()) {
            throw new MyInvalidPathException("No such idUser in DB");
        }

        LinkedList<ModelElement> list = new LinkedList<>();

        idUser = rst.getInt("userId");
        String name = rst.getString("name");
        String email = rst.getString("email");
        Users user = new Users(idUser, name, email);

        int idReview = rst.getInt("idReview");
        if (idReview != 0) {
            String reviewSummary = rst.getString("reviewSummary");
            user.addReview(new Review(idReview, -1, -1, reviewSummary, null, -1));

            while (rst.next()) {
                idReview = rst.getInt("idReview");
                reviewSummary = rst.getString("reviewSummary");
                user.addReview(new Review(idReview, -1, -1, reviewSummary, null, -1));
            }
        }
        list.add(user);

        return new GetUserByIdResult(list);
    }

    @Override
    public String getCommandDescription() {
        return "GET /users/{uid}:\n\t Returns the Information of User uid and his Reviews.";
    }
}