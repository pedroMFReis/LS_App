package pt.isel.ls.command;

import pt.isel.ls.exceptions.MyInvalidParametersException;

import java.util.HashMap;

public abstract class CommandParser {

    final String splitters;//lower case because of checkstyle
    final char space;

    protected HashMap<String, String> proprieties = new HashMap<>();

    protected CommandParser(String splitters, char space, String toParse) throws MyInvalidParametersException {
        this.splitters = splitters;
        this.space = space;

        toParse = toParse.replace(space, ' ');
        String[] temp = toParse.split(splitters);

        for (int i = 0; i < temp.length; i = i + 2) {
            proprieties.put(temp[i], temp[i + 1]);
        }

    }
}
