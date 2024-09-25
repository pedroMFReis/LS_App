package pt.isel.ls.model;

import java.util.LinkedList;

public abstract class ModelElement {

    String params;

    protected ModelElement(String params) {

        this.params = params;
    }

    public String getParams() {

        return params;
    }

    public abstract LinkedList getProprieties();
}
