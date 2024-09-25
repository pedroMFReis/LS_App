package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public class GetRatingsResult extends BaseGetResult {

    public GetRatingsResult(LinkedList result) {
        super(result);
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}
