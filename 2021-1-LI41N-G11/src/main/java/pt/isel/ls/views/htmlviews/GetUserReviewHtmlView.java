package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Review;

public class GetUserReviewHtmlView extends HtmlView {

    @Override
    public String print() {

        Element body = composeBody();
        Element html = writeHtml(body);

        return html.toString();
    }

    public Element composeBody() {
        Element body = new Element("body", "");

        body.addChild(table());
        return body;
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
        for (ModelElement e : elements) {
            Element t = new Element("tr", "");
            Review r = (Review) e;
            t.addChild(new Element("td", String.valueOf(r.getIdReview())));
            t.addChild(new Element("td", String.valueOf(r.getIdMovie())));
            t.addChild(new Element("td", String.valueOf(r.getReviewSummary())));
            t.addChild(new Element("td", String.valueOf(r.getRating())));
            table.addChild(t);
        }
    }

}
