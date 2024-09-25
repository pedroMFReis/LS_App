package pt.isel.ls.model;

import java.util.LinkedList;

public class Users extends ModelElement {
    int idUser;
    String name;
    String email;
    LinkedList<Review> reviews = new LinkedList<>();

    public Users(int idUser, String name, String email) {
        super("User Id/Name/E-mail");
        this.idUser = idUser;
        this.name = name;
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String toString() {
        return "User Id - " + idUser
                + "\n\tName - " + name
                + "\n\tE-mail -" + email + "\n";
    }

    @Override
    public LinkedList getProprieties() {
        LinkedList toReturn = new LinkedList();
        toReturn.add(idUser);
        toReturn.add(name);
        toReturn.add(email);
        return toReturn;
    }

    public void addReview(Review rvw) {
        reviews.add(rvw);
    }

    public LinkedList<Review> getReviews() {
        return reviews;
    }
}
