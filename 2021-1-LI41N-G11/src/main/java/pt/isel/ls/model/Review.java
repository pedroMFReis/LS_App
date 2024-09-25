package pt.isel.ls.model;

import java.util.LinkedList;

public class Review extends ModelElement {
    int idReview;
    int idUser;
    int idMovie;
    String reviewSummary;
    String review;
    int rating;

    public Review(int idReview, int idUser, int idMovie,
                  String reviewSummary, String review, int rating) {
        super(null);
        this.idReview = idReview;
        this.idUser = idUser;
        this.idMovie = idMovie;
        this.reviewSummary = reviewSummary;
        this.review = review;
        this.rating = rating;
    }


    public String toString() {
        String str = "";
        if (idReview > 0) {
            str += "\nReview Id - " + idReview;
        }
        if (idUser > 0) {
            str += "\nUser Id - " + idUser;
        }
        if (idMovie > 0) {
            str += "\nMovie Id - " + idMovie;
        }
        if (reviewSummary != null) {
            str += "\nReview Summary: " + reviewSummary;
        }
        if (review != null) {
            str += "\nReview: " + review;
        }
        if (rating > 0) {
            str += "\nRating - " + rating;
        }
        return str + "\n";
    }

    public void setParams() {
        String str = "";
        if (idReview > 0) {
            str += "Review Id/";
        }
        if (idUser > 0) {
            str += "User Id/";
        }
        if (idMovie > 0) {
            str += "Movie Id/";
        }
        if (reviewSummary != null) {
            str += "Review Summary/";
        }
        if (review != null) {
            str += "Review/";
        }
        if (rating > 0) {
            str += "Rating/";
        }
        params = str;
    }

    @Override
    public LinkedList getProprieties() {
        LinkedList toReturn = new LinkedList();
        toReturn.add(idReview);
        toReturn.add(idUser);
        toReturn.add(idMovie);
        toReturn.add(reviewSummary);
        toReturn.add(review);
        toReturn.add(rating);
        return toReturn;
    }

    public int getIdReview() {
        return idReview;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public String getReviewSummary() {
        return reviewSummary;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }
}

