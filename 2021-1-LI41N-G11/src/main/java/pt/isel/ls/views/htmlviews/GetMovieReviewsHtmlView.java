package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;

public class GetMovieReviewsHtmlView extends HtmlView {

    @Override
    public boolean hasResponse() {
        return true;
    }

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);
        html.addChild(root());

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");
        Element div1 = new Element("div", "");
        body.addChild(div1);

        if (elements.isEmpty()) {
            div1.addChild(new Element("h1", "No Reviews for this Movie Yet, Be the First to Post a Review!"));
        } else {
            div1.addChild(table());
        }
        Element div2 = new Element("div", "");
        body.addChild(div2);
        div2.addChild(form());
        body.addChild(root());

        return body;
    }

    private Element form() {
        Element br = new Element("br", "");
        Element form = new Element("form", "", " action=\"./reviews\" method=\"POST\" ");
        Element labelTitle = new Element("label", "User id: ", " for = \"uid\"");
        Element inputTitle = new Element("input", "", " type=\"text\" id=\"uid\" name=\"uid\"");
        Element labelYear = new Element("label", "Review Summary: ", " for = \"reviewSummary\"");
        Element inputYear = new Element("input", "", " type=\"text\" id=\"reviewSummary\" name=\"reviewSummary\"");
        Element labelReview = new Element("label", "Review: ", " for = \"review\"");
        Element inputReview = new Element("input", "", " type=\"text\" id=\"review\" name=\"review\"");
        Element labelRating = new Element("label", "Rating: ", " for = \"rating\"");
        Element inputRating = new Element("input", "", " type=\"text\" id=\"rating\" name=\"rating\"");
        Element submit = new Element("input", "", " type=\"submit\" value=\"Submit\"");

        form.addChild(br);
        form.addChild(labelTitle);
        form.addChild(inputTitle);
        form.addChild(br);
        form.addChild(labelYear);
        form.addChild(inputYear);
        form.addChild(br);
        form.addChild(labelReview);
        form.addChild(inputReview);
        form.addChild(br);
        form.addChild(labelRating);
        form.addChild(inputRating);
        form.addChild(br);
        form.addChild(submit);

        return form;
    }

    private Element table() {
        Element table = new Element("table", "");

        String[] p = {"Review Id", "Movie Id", "Review Summary", "Rating"};
        Element th = new Element("tr", "");
        for (int i = 0; i < p.length; i++) {
            th.addChild(new Element("th", p[i]));
        }
        table.addChild(th);

        fillTable(table);
        return table;
    }

    private void fillTable(Element table) {
        int movieId;
        for (ModelElement e : elements) {
            Element t = new Element("tr", "");
            Review r = (Review) e;
            movieId = r.getIdMovie();
            t.addChild(new Element("td", createLink(r.getIdReview(), "reviews", movieId)));
            t.addChild(new Element("td", createLink(movieId, "movies", movieId)));
            t.addChild(new Element("td", String.valueOf(r.getReviewSummary())));
            t.addChild(new Element("td", String.valueOf(r.getRating())));
            table.addChild(t);
        }
    }

    private String createLink(int id, String path, int movieId) {
        if (path.equals("reviews")) {
            return "<a href='/movies/" + movieId + "/reviews/" + id
                    + "'> " + id + " </a>";
        } else {
            return "<a href='/movies/" + id
                    + "'> " + id + " </a>";
        }
    }
}
