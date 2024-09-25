package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;


/**
 * GET /movies/{mid}/ratings - returns the rating information for the movie identified by mid.
 * This rating information include:
 * The rating average
 * The number of votes for each rating value
 */

public class GetMovieRatingResult extends BaseGetResult {


    public GetMovieRatingResult(LinkedList<ModelElement> rating) {
        super(rating);
        element = "Rating:\n";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}
