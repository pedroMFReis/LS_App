package pt.isel.ls.results.get;

import pt.isel.ls.results.CommandResult;

import java.util.LinkedList;

public abstract class BaseGetResult<T> implements CommandResult {

    String element = "";

    protected LinkedList<T> result;
    protected final String style = "table, th, td {\n"
            + "border: 1px solid black;\n"
            + "border-collapse: collapse;\n"
            + "font-family: Arial;\n"
            + "text-align: center;\n"
            + "\n"
            + "}";

    public BaseGetResult(LinkedList<T> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        String s = element;
        for (T e : result) {
            s += "\t" + e.toString();
        }
        return s;
    }
}
