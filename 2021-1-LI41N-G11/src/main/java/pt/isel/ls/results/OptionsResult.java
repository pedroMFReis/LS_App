package pt.isel.ls.results;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public class OptionsResult implements CommandResult {

    String result = "";

    public void addCommand(String cmdDescription) {
        result = result + cmdDescription + "\n\n";
    }

    @Override
    public String toString() {
        return result;
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return null;
    }
}
