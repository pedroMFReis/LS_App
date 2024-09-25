package pt.isel.ls.results;

import pt.isel.ls.model.ModelElement;

import java.util.LinkedList;

public interface CommandResult {

    String toString();

    LinkedList<ModelElement> getElements();

}

