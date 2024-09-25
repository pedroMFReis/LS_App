package pt.isel.ls.views;

import pt.isel.ls.results.CommandResult;

public class TextPlainView implements View {
    CommandResult result;

    @Override
    public void setResult(CommandResult result) {
        this.result = result;
    }

    @Override
    public boolean hasResponse() {
        return result.getElements().size() > 0;
    }

    @Override
    public String print() {
        return result.toString();
    }
}
