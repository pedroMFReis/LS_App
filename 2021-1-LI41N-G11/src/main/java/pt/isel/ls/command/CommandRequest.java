package pt.isel.ls.command;

import pt.isel.ls.exceptions.MyInvalidParametersException;

public class CommandRequest {

    final int methodIdx = 0;
    final int pathIdx = 1;
    final int headersIdx = 2;
    final int paramIdx = 3;
    final String splitter = " ";

    private String method;
    private CommandPath path;
    private CommandHeaders headers = null;
    private CommandParam parameters = null;

    public CommandRequest(String command) throws MyInvalidParametersException {
        String[] args = command.split(splitter);
        method = args[methodIdx];
        if (args.length >= 2) {
            path = new CommandPath(args[pathIdx]);
        }
        if (args.length == 4) {
            headers = new CommandHeaders(args[headersIdx]);
            parameters = new CommandParam(args[paramIdx]);
        }
        if (args.length == 3) {
            if (args[2].indexOf('=') > 0) {
                parameters = new CommandParam(args[paramIdx - 1]);
            } else {
                headers = new CommandHeaders(args[headersIdx]);
            }
        }
    }

    public CommandHeaders getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public CommandPath getPath() {
        return path;
    }

    public CommandParam getParameters() {
        return parameters;
    }

}