package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Movie;

import java.util.LinkedList;

/**
 * GET /movies - returns a list with all movies.
 */

public class GetMoviesResult extends BaseGetResult {

    int skip;
    int top;

    public GetMoviesResult(LinkedList<Movie> moviesList, int skip, int top) {
        super(moviesList);
        element = "Movies:\n";
        this.skip = skip;
        this.top = top;
    }

    public int getSkip() {
        return skip;
    }

    public int getTop() {
        return top;
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}