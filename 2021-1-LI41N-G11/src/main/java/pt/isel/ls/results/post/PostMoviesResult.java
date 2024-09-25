package pt.isel.ls.results.post;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public class PostMoviesResult extends BasePostResult {
    public PostMoviesResult(String str) {
        super(str);
    }

    @Override
    public String toString() {
        return str;
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return null;
    }
}
