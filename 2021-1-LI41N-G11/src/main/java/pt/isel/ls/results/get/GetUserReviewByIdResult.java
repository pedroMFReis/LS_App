package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

/**
 * GET /users/{uid}/reviews/{rid} - returns the full
 * information for the review rid made by the user identified by uid.
 */

public class GetUserReviewByIdResult extends BaseGetResult {


    public GetUserReviewByIdResult(LinkedList<ModelElement> review) {

        super(review);
        element = "Review:\n";
    }


    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}
