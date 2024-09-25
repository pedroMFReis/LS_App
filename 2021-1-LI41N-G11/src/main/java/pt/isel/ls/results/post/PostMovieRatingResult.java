package pt.isel.ls.results.post;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public class PostMovieRatingResult extends BasePostResult {
    public PostMovieRatingResult(String str) {
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

    @Override
    public int getId() {
        return -1;
    }
}
