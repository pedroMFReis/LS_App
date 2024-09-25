package pt.isel.ls.command;

import pt.isel.ls.exceptions.MyInvalidParametersException;

public class CommandPath {

    final String splitter = "/";

    private String[] path;

    public CommandPath(String path) {
        this.path = path.split(splitter);
    }

    public String[] getPath() {
        return path;
    }

    public String get(int idx) {
        return path[idx];
    }

    public String[] getParameters(int numParameters) throws MyInvalidParametersException {
        if (numParameters == 0) {
            return null;
        }
        if (numParameters < 0 || numParameters > 2) {
            throw new MyInvalidParametersException("Invalid Number of parameters");
        }
        String[] parametersArray = new String[numParameters];
        if (numParameters == 2 && path[2] != null && path[4] != null) {
            parametersArray[0] = path[2];
            parametersArray[1] = path[4];
        } else if (numParameters == 1 && path[1] != null) {
            parametersArray[0] = path[2];
        }
        return parametersArray;
    }
}
