package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;

import java.util.LinkedList;

/**
 * GET /movies/{mid}/reviews - returns all the reviews for
 * the movie identified by mid. The information for each review
 * must not include the full review text.
 */

public class GetMovieReviewResult extends BaseGetResult {


    public GetMovieReviewResult(LinkedList<Review> moviesReviewsList) {
        super(moviesReviewsList);
        element = "Reviews:\n";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}
