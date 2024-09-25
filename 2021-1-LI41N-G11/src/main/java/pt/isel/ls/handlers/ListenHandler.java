package pt.isel.ls.handlers;

import pt.isel.ls.command.CommandParam;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.http.ServletApp;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.ListenResult;

import java.sql.Connection;
import java.util.HashMap;

public class ListenHandler implements CommandHandler {

    private final String pathTemplate = "LISTEN/";

    private boolean checkParams(HashMap<String, String> p) {
        //checking there are enough parameter
        if (p.size() != 1) {
            return false;
        }
        //checking if there is the correct parameters
        return (p.containsKey("port"));
    }

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws Exception {
        CommandParam params = cmdReq.getParameters();
        int port = -1;
        if (params != null) {
            HashMap<String, String> p = params.getProprieties();

            if (checkParams(p)) {
                port = Integer.parseInt(p.get("port"));
            }
        }
        ServletApp.startServlet(port);
        return new ListenResult();
    }

    @Override
    public String getCommandDescription() {
        return "Listen:\n\t Starts the HTTP Servlet";
    }

    @Override
    public String getPathTemplate() {
        return pathTemplate;
    }
}
