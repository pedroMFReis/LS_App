package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;

public class GetUserReviewByIdHtmlView extends HtmlView {

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");

        body.addChild(bulletPoint());
        body.addChild(backButton());
        body.addChild(root());

        return body;
    }

    private Element bulletPoint() {
        Element bp = new Element("ul", "");
        for (ModelElement e : elements) {
            Review r = (Review) e;
            bp.addChild(new Element("li", "Review Id - " + r.getIdReview()));
            bp.addChild(new Element("li", "User Id - " + createLink(r.getIdUser(), "users")));
            bp.addChild(new Element("li", "Movie Id - " + createLink(r.getIdMovie(), "movies")));
            bp.addChild(new Element("li", "Review Summary - " + r.getReviewSummary()));
            bp.addChild(new Element("li", "Review - " + r.getReview()));
            bp.addChild(new Element("li", "Rating - " + r.getRating()));
        }

        return bp;
    }

    private String createLink(int id, String path) {
        return "<a href='/" + path + "/" + id + "'> " + id + " </a>";
    }

    private Element backButton() {
        Review r = (Review) elements.getFirst();
        int mid = r.getIdMovie();
        String t = "<a href='/movies/" + mid + "/reviews'> To Reviews </a>";
        Element b = new Element("Button", t);

        return b;
    }

    /**
     * NEEDED LINKS
     * GET /users/{id}
     * GET /movies/{id}
     * GET /movies/{id}/reviews
     */
}
