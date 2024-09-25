package pt.isel.ls.model;

import java.util.LinkedList;

public class Movie extends ModelElement {

    int idMovie;
    String title;
    int releaseYear;
    LinkedList<Review> reviews = new LinkedList<>();

    public Movie(int idMovie, String title, int releaseYear) {
        super("Movie ID/Title/Release Year");
        this.idMovie = idMovie;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String toString() {
        return "Movie Id - " + idMovie
                + "\n\tTitle - " + title
                + "\n\tRelease Year - " + releaseYear;
    }

    @Override
    public LinkedList getProprieties() {
        LinkedList toReturn = new LinkedList();
        toReturn.add(idMovie);
        toReturn.add(title);
        toReturn.add(releaseYear);
        return toReturn;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void addReview(Review rvw) {
        reviews.add(rvw);
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }
}
