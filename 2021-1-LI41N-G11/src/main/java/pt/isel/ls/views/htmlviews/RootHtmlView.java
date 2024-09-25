package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.results.CommandResult;

public class RootHtmlView extends HtmlView {
    CommandResult result;

    @Override
    public void setResult(CommandResult result) {
        this.result = result;
    }

    @Override
    public String print() {
        Element body = composeBody();
        Element html = writeHtml(body);
        return html.toString();
    }


    private Element composeBody() {
        Element body = new Element("body", "");
        body.addChild(redirectButton("movies?skip=0&top=5", "Movies"));
        body.addChild(redirectButton("users", "Users"));
        body.addChild(redirectButton("tops/ratings?n=5&average=highest&min=1", "Top Ratings"));
        return body;
    }

    private Element redirectButton(String path, String txt) {
        String t = "<a href='/" + path + "'> To " + txt + " </a>";
        Element button = new Element("button", t);
        return button;
    }

    @Override
    public boolean hasResponse() {
        return true;
    }
}

