package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.views.View;

import java.util.LinkedList;

public abstract class HtmlView implements View {

    LinkedList<ModelElement> elements;
    CommandResult result;

    protected final String style = "table, th, td {\n"
            + "border: 1px solid black;\n"
            + "border-collapse: collapse;\n"
            + "font-family: Arial;\n"
            + "text-align: center;\n"
            + "\n"
            + "}";

    @Override
    public void setResult(CommandResult result) {
        this.result = result;
        elements = result.getElements();
    }

    protected Element writeHtml(Element body) {
        Element html = new Element("html", "");
        html.addChild(new Element("head", "", new Element("style", style)));
        html.addChild(body);
        return html;
    }

    protected Element root() {
        String t = "<a href='/'> Return Home </a>";
        Element root = new Element("Button", t);

        return root;
    }

    @Override
    public boolean hasResponse() {
        return result.getElements().size() > 0;
    }
}
