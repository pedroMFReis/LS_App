package pt.isel.ls.html;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Element {

    private String tag;
    private String attrs = "";
    private String text;

    private List<Element> children = new LinkedList<>();

    public Element(String tag, String text) {
        this.tag = tag;
        this.text = text;

    }

    public Element(String tag, String text, String attrs) {
        this.tag = tag;
        this.text = text;
        this.attrs = attrs;
    }

    public Element(String tag, String text, Element... elements) {
        this.tag = tag;
        this.text = text;
        children.addAll(Arrays.asList(elements));
    }

    public void addChild(Element e) {
        children.add(e);
    }

    public void addText(String txt) {
        text = txt;
    }

    public Element addId(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append(attrs).append(" id = \"").append(id + "\" ");
        this.attrs = sb.toString();
        return this;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("<").append(tag);
        sb.append(attrs).append(">");
        if (!tag.equals("br")) {
            for (Element elem : children) {
                sb.append(elem.toString());
            }
            sb.append(text);
            sb.append("</").append(tag).append(">");
        }
        return sb.toString();
    }
}

