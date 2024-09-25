package pt.isel.ls.results;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public class ExitResult implements CommandResult {

    @Override
    public String toString() {
        return "Exiting...";
    }

    @Override
    public LinkedList<ModelElement> getElements() {
        return null;
    }
}
