package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Movie;
import pt.isel.ls.results.get.GetMoviesResult;

public class GetMoviesHtmlView extends HtmlView {

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
        div1.addChild(table());
        Element div2 = new Element("div", "");
        body.addChild(div2);
        div2.addChild(form());

        createPageButtons(body);
        body.addChild(root());

        return body;
    }


    private Element form() {
        Element br = new Element("br", "");
        Element form = new Element("form", "", " action=\"/movies\" method=\"POST\" ");
        Element labelTitle = new Element("label", "Title: ", " for = \"title\"");
        Element inputTitle = new Element("input", "", " type=\"text\" id=\"title\" name=\"title\"");
        Element labelYear = new Element("label", "Release Year: ", " for = \"releaseYear\"");
        Element inputYear = new Element("input", "", " type=\"text\" id=\"releaseYear\" name=\"releaseYear\"");
        Element submit = new Element("input", "", " type=\"submit\" value=\"Submit\"");
        form.addChild(br);
        form.addChild(labelTitle);
        form.addChild(inputTitle);
        form.addChild(br);
        form.addChild(labelYear);
        form.addChild(inputYear);
        form.addChild(br);
        form.addChild(submit);
        form.addChild(br);

        return form;
    }

    private Element table() {
        Element table = new Element("table", "");
        String[] p = {"Movie Id", "Title", "Release Year"};
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
            Movie m = (Movie) e;
            t.addChild(new Element("td", createLink(m.getIdMovie())));
            t.addChild(new Element("td", m.getTitle()));
            t.addChild(new Element("td", String.valueOf(m.getReleaseYear())));
            table.addChild(t);
        }
    }

    private String createLink(int id) {
        return "<a href='/movies/" + id + "'> " + id + " </a>";
    }

    private void createPageButtons(Element body) {
        GetMoviesResult rst = (GetMoviesResult) result;
        int skip = rst.getSkip();
        int top = rst.getTop();

        if (skip >= 0 && top > 0) {
            if (skip != 0) {
                body.addChild(prevButton(skip, top));
            }
            body.addChild(nextButton(skip, top));
        }
    }

    private Element prevButton(int skip, int top) {
        int prevSkip = skip - top;
        if (prevSkip < 0) {
            prevSkip = 0;
        }
        return new Element("Button", "<a href='/movies?skip=" + prevSkip
                + "&top=" + top + "'> Previous Page </a>");
    }

    private Element nextButton(int skip, int top) {
        int nextSkip = skip + top;
        return new Element("Button", "<a href='/movies?skip=" + nextSkip
                + "&top=" + top + "'> Next Page </a>");
    }
}
