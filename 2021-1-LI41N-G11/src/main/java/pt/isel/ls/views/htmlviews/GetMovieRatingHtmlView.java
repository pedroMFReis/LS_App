package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Ratings;

import java.util.HashMap;

public class GetMovieRatingHtmlView extends HtmlView {

    int movieId;

    @Override
    public boolean hasResponse() {
        return true;
    }

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");
        Element div1 = new Element("div", "");
        body.addChild(div1);

        if (elements.isEmpty()) {
            div1.addChild(new Element("h1", "No Ratings for this Movie Yet, Be the First to Post a Rating!"));
        } else {
            div1.addChild(table());
        }
        Element div2 = new Element("div", "");
        body.addChild(div2);
        div2.addChild(form());
        body.addChild(root());

        body.addChild(movieButton());
        body.addChild(root());
        return body;
    }

    private Element form() {
        Element br = new Element("br", "");
        Element form = new Element("form", "", " action=\"./ratings\" method=\"POST\" ");

        Element labelRating = new Element("label", "Rating: ", " for = \"rating\"");
        Element inputRating = new Element("input", "", " type=\"text\" id=\"rating\" name=\"rating\"");
        Element submit = new Element("input", "", " type=\"submit\" value=\"Submit\"");
        form.addChild(br);
        form.addChild(labelRating);
        form.addChild(inputRating);
        form.addChild(br);
        form.addChild(submit);

        return form;
    }

    private Element table() {
        Element table = new Element("table", "");

        String[] p = {"Movie Id", "1*", "2*", "3*", "4*", "5*", "Rating"};
        Element th = new Element("tr", "");
        for (int i = 0; i < p.length; i++) {
            th.addChild(new Element("th", p[i]));
        }
        table.addChild(th);

        fillTable(table);
        return table;
    }

    private void fillTable(Element table) {
        for (ModelElement e : elements) {
            Element t = new Element("tr", "");
            Ratings r = (Ratings) e;
            movieId = r.getMovie();
            t.addChild(new Element("td", String.valueOf(movieId)));
            HashMap<String, Integer> rs = r.getStars();
            t.addChild(new Element("td", String.valueOf(rs.get("star_1"))));
            t.addChild(new Element("td", String.valueOf(rs.get("star_2"))));
            t.addChild(new Element("td", String.valueOf(rs.get("star_3"))));
            t.addChild(new Element("td", String.valueOf(rs.get("star_4"))));
            t.addChild(new Element("td", String.valueOf(rs.get("star_5"))));
            t.addChild(new Element("td", String.valueOf(r.getAverage())));
            table.addChild(t);
        }
    }

    private Element movieButton() {
        String t = "<a href='/movies/" + movieId + "'> To Movie </a>";
        return new Element("Button", t);
    }
}
