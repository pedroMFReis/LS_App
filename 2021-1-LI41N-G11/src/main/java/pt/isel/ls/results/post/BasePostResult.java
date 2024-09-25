package pt.isel.ls.results.post;

import pt.isel.ls.results.CommandResult;

public abstract class BasePostResult implements CommandResult {
    protected String str;

    public BasePostResult(String str) {
        this.str = str;
    }

    public int getId() {
        String[] aux0 = str.split("ID ");
        String[] aux1 = aux0[1].split("\\.");
        return Integer.parseInt(aux1[0]);
    }
}
