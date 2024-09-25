package pt.isel.ls.command;

import pt.isel.ls.exceptions.MyInvalidParametersException;

import java.util.HashMap;

public class CommandParam extends CommandParser {

    public CommandParam(String parameters) throws MyInvalidParametersException {
        super("[&=]", '+', parameters);
    }

    public HashMap<String, String> getProprieties() {
        return proprieties;
    }
}