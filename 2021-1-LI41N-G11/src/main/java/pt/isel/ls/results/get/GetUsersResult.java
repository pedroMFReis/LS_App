package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Users;

import java.util.LinkedList;

/**
 * GET /users - returns the list of users.
 */

public class GetUsersResult extends BaseGetResult {


    public GetUsersResult(LinkedList<Users> result) {
        super(result);
        element = "Users:\n";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}
