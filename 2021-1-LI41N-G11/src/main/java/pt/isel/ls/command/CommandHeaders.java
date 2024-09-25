package pt.isel.ls.command;

import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.exceptions.MyInvalidParametersException;

import java.util.HashMap;

public class CommandHeaders extends CommandParser {

    public CommandHeaders(String headers) throws MyInvalidParametersException {
        super("[:|]", ' ', headers);
    }

    public boolean checkHeaders() {
        //checking if there is the correct parameters
        if ((proprieties.containsKey("accept") || proprieties.containsKey("file-name"))
                && proprieties.size() <= 2) {
            return true;
        }
        return false;
    }


    public boolean isHtml() throws MyInvalidCommandException {
        if (!checkHeaders()) {
            throw new MyInvalidCommandException("Invalid Header!!");
        }
        //returns false if is "text/plain"
        if (!proprieties.containsKey("accept")) {
            return false;
        }
        return proprieties.get("accept").equals("text/html");
    }

    public HashMap<String, String> getProprieties() {
        return proprieties;
    }
}
