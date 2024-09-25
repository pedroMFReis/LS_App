package pt.isel.ls.results;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public class ListenResult implements CommandResult {

    @Override
    public String toString() {
        return "Starting the Servlet...";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return null;
    }

}
