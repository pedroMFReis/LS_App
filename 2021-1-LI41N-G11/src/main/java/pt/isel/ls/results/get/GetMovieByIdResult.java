package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

/**
 * GET /movies/{mid} - returns the detailed information for the movie identified by mid.
 */

public class GetMovieByIdResult extends BaseGetResult {

    public GetMovieByIdResult(LinkedList<ModelElement> movie) {

        super(movie);
        element = "Movie:\n";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}