package pt.isel.ls.results.delete;

import pt.isel.ls.model.ModelElement;
import pt.isel.ls.results.CommandResult;

import java.util.LinkedList;


public class DeleteMovieReviewResult implements CommandResult {

    int idMovie;
    int idReview;

    public DeleteMovieReviewResult(int idMovie, int idReview) {
        this.idMovie = idMovie;
        this.idReview = idReview;
    }

    @Override
    public String toString() {
        return "Deleted a Review from movie with ID " + idMovie + " with Review Id " + idReview;
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return null;
    }


}
