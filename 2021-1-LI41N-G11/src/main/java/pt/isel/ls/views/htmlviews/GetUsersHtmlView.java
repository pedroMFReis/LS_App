package pt.isel.ls.views.htmlviews;

import pt.isel.ls.html.Element;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Users;

public class GetUsersHtmlView extends HtmlView {

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

        body.addChild(root());

        return body;
    }

    private Element form() {
        Element br = new Element("br", "");
        Element form = new Element("form", "", " action=\"/users\" method=\"POST\" ");
        Element labelTitle = new Element("label", "Name: ", " for = \"name\"");
        Element inputTitle = new Element("input", "", " type=\"text\" id=\"name\" name=\"name\"");
        Element labelYear = new Element("label", "Email: ", " for = \"email\"");
        Element inputYear = new Element("input", "", " type=\"text\" id=\"email\" name=\"email\"");
        Element submit = new Element("input", "", " type=\"submit\" value=\"Submit\"");

        form.addChild(br);
        form.addChild(labelTitle);
        form.addChild(inputTitle);
        form.addChild(br);
        form.addChild(labelYear);
        form.addChild(inputYear);
        form.addChild(br);
        form.addChild(submit);

        return form;
    }

    private Element table() {
        Element table = new Element("table", "");

        String[] p = {"User Id", "Name", "E-Mail"};
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
            Users u = (Users) e;
            t.addChild(new Element("td", createLink(u.getIdUser())));
            t.addChild(new Element("td", u.getName()));
            t.addChild(new Element("td", u.getEmail()));
            table.addChild(t);
        }
    }

    private String createLink(int id) {
        return "<a href='/users/" + id + "'> " + id + " </a>";
    }

}
