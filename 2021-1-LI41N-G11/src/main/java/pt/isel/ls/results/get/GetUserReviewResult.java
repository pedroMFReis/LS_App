package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;

import java.util.LinkedList;

/**
 * GET /users/{uid}/reviews - returns all the reviews made from the user identified by uid.
 * The information for each review must not include the full review text.
 */

public class GetUserReviewResult extends BaseGetResult {


    public GetUserReviewResult(LinkedList<Review> reviewsList) {

        super(reviewsList);
        element = "Reviews:\n";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }

}
