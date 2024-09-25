package pt.isel.ls.views;

import pt.isel.ls.results.CommandResult;

public interface View {

    String print();

    void setResult(CommandResult result);

    boolean hasResponse();
}
