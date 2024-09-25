package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Ratings;

public class GetTopRatingHtmlView extends HtmlView {

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");

        body.addChild(table());
        body.addChild(moviesButton());
        body.addChild(root());
        return body;
    }

    private Element table() {
        Element table = new Element("table", "");

        String[] p = {"Movie Id", "Rating"};
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
            t.addChild(new Element("td", createLink(r.getMovie())));
            t.addChild(new Element("td", String.valueOf(r.getAverage())));
            table.addChild(t);
        }
    }

    private String createLink(int id) {
        return "<a href='/movies/" + id + "'> " + id + " </a>";
    }

    private Element moviesButton() {
        return new Element("Button", "<a href='/movies?skip=0&top=5'> To Movies </a>");
    }
}
