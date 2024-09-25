package pt.isel.ls.results.get;

import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Ratings;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * GET /tops/ratings - returns a list with the movies, given the following parameters:
 * n - max number of movies to list;
 * average - two possible values:
 * highest- movies with the highest average ratings
 * lowest- movies with the lowest average ratings
 * min - minimum number of votes
 */

public class GetTopRatingResult extends BaseGetResult {

    public GetTopRatingResult(PriorityQueue<Ratings> priorityQueue, int max) {
        super(null);
        LinkedList<ModelElement> ratings = new LinkedList<>();
        int n = 0;
        while (!priorityQueue.isEmpty() && n++ < max) {
            ratings.add(priorityQueue.poll());
        }
        result = ratings;
        element = "Top Ratings:\n";
    }


    @Override
    public LinkedList<ModelElement> getElements() {
        return result;
    }
}
