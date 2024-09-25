package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Movie;
import pt.isel.ls.model.Review;


public class GetMovieByIdHtmlView extends HtmlView {

    int movieId;

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");

        body.addChild(bulletPoint());
        body.addChild(redirectButton("reviews"));
        body.addChild(redirectButton("ratings"));
        body.addChild(moviesButton());
        body.addChild(root());
        return body;
    }

    private Element bulletPoint() {
        Element bp = new Element("ul", "");
        Element rtable = new Element("table", "");
        for (ModelElement e : elements) {
            Movie m = (Movie) e;
            movieId = m.getIdMovie();
            bp.addChild(new Element("li", "Movie ID - " + movieId));
            bp.addChild(new Element("li", "Title - " + m.getTitle()));
            bp.addChild(new Element("li", "Release Year - " + m.getReleaseYear()));


            //Table for Reviews
            if (!m.getReviews().isEmpty()) {
                Element th = new Element("tr", "");
                th.addChild(new Element("th", "Review Id"));
                th.addChild(new Element("th", "User Id"));
                rtable.addChild(th);

                Element review;
                for (Review r : m.getReviews()) {
                    review = new Element("tr", "");
                    review.addChild(new Element("td", createLink(r.getIdReview(), "reviews")));
                    review.addChild(new Element("td", createLink(r.getIdUser(), "users")));
                    rtable.addChild(review);
                }
            }
        }
        bp.addChild(rtable);

        return bp;
    }

    private String createLink(int id, String path) {
        String link;
        if (path.equals("users")) {
            link = "<a href='/users/" + id + "'> " + id + " </a>";
        } else {
            link = "<a href='/movies/" + movieId + "/reviews/" + id + "'> " + id + " </a>";
        }
        return link;
    }

    private Element redirectButton(String path) {
        String t = "<a href='/movies/" + movieId + "/" + path + "'> To " + path + " </a>";
        Element button = new Element("button", t);
        return button;
    }

    private Element moviesButton() {
        return new Element("Button", "<a href='/movies?skip=0&top=5'>"
                + " To movies </a>");
    }

    /* NEEDED LINKS
     * GET /movies/{mid}/reviews/{rid}
     */
}
