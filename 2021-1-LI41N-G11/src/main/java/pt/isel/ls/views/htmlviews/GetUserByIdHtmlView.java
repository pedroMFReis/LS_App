package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;
import pt.isel.ls.model.Users;


public class GetUserByIdHtmlView extends HtmlView {

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");

        body.addChild(bulletPoint());
        body.addChild(usersButton());
        body.addChild(root());

        return body;
    }

    private Element bulletPoint() {
        Element bp = new Element("ul", "");
        Element rtable = new Element("table", "");
        int userId;
        for (ModelElement e : elements) {
            Users u = (Users) e;
            userId = u.getIdUser();
            bp.addChild(new Element("li", "User Id - " + userId));
            bp.addChild(new Element("li", "Name - " + u.getName()));
            bp.addChild(new Element("li", "E-Mail - " + u.getEmail()));

            //Table for Reviews
            if (!u.getReviews().isEmpty()) {
                Element th = new Element("tr", "");
                th.addChild(new Element("th", "Review Id"));
                th.addChild(new Element("th", "Review Summary"));
                rtable.addChild(th);

                Element review;
                for (Review r : u.getReviews()) {
                    review = new Element("tr", "");
                    review.addChild(new Element("td", createLink(r.getIdReview(), userId)));
                    review.addChild(new Element("td", r.getReviewSummary()));
                    rtable.addChild(review);
                }
            }
        }
        bp.addChild(rtable);
        //bp.addChild(new Element("li", p[i] + " - " + tmp.get(i).toString()));


        return bp;
    }

    private String createLink(int id, int userId) {
        return "<a href='/users/" + userId + "/reviews/" + id + "'> " + id + " </a>";
    }

    private Element usersButton() {
        String t = "<a href='/users'> To Users </a>";
        return new Element("Button", t);
    }
}
