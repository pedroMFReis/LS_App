package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;


/**
 * GET /movies/{mid}/reviews/{rid} - returns the full information
 * for the review rid of the movie identified by mid.
 */

public class GetMovieReviewByIdResult extends BaseGetResult {

    public GetMovieReviewByIdResult(LinkedList<ModelElement> review) {

        super(review);
        element = "Review:\n";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}