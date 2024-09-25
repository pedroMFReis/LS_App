package pt.isel.ls.model;

import java.util.HashMap;
import java.util.LinkedList;

public class Ratings extends ModelElement {

    protected int idMovie;
    protected HashMap<String, Integer> stars;
    protected double average;
    protected double counterVotes;

    public Ratings(int idMovie, HashMap<String, Integer> stars) {
        super("Movie Id/1*/2*/3*/4*/5*/Average");
        this.idMovie = idMovie;
        this.stars = stars;
        initializeStars();
    }

    private void initializeStars() {

        stars.putIfAbsent("star_1", 0);
        stars.putIfAbsent("star_2", 0);
        stars.putIfAbsent("star_3", 0);
        stars.putIfAbsent("star_4", 0);
        stars.putIfAbsent("star_5", 0);
    }

    public void incrementStar(String key) {

        stars.put(key, stars.get(key) + 1);
    }

    public void decrementStar(String key) {

        stars.put(key, stars.get(key) - 1);
    }

    protected void calculateAverage() {

        countVotes();
        average = (1 * stars.get("star_1") + 2 * stars.get("star_2")
                + 3 * stars.get("star_3") + 4 * stars.get("star_4")
                + 5 * stars.get("star_5")) / counterVotes;
    }

    protected void countVotes() {

        counterVotes = stars.get("star_1") + stars.get("star_2") + stars.get("star_3")
                + stars.get("star_4") + stars.get("star_5");
    }

    public int getMovie() {

        return idMovie;
    }

    public double getAverage() {

        calculateAverage();
        return average;
    }

    public double getCounterVotes() {

        countVotes();
        return counterVotes;
    }

    public HashMap<String, Integer> getStars() {
        return stars;
    }


    public String toString() {
        return idMovie
                + "\n1 star: " + stars.get("star_1") + "\n2 stars: " + stars.get("star_2")
                + "\n3 stars: " + stars.get("star_3") + "\n4 stars: " + stars.get("star_4")
                + "\n5 stars: " + stars.get("star_5") + "\naverage:" + getAverage() + "\n";
    }

    @Override
    public LinkedList getProprieties() {
        LinkedList toReturn = new LinkedList();
        toReturn.add(idMovie);
        toReturn.add(stars.get("star_1"));
        toReturn.add(stars.get("star_2"));
        toReturn.add(stars.get("star_3"));
        toReturn.add(stars.get("star_4"));
        toReturn.add(stars.get("star_5"));
        toReturn.add(getAverage());
        return toReturn;
    }
}
