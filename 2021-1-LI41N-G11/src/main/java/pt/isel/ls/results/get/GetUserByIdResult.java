package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;


public class GetUserByIdResult extends BaseGetResult {

    public GetUserByIdResult(LinkedList<ModelElement> user) {
        super(user);
        element = "User:\n";
    }


    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }

}
