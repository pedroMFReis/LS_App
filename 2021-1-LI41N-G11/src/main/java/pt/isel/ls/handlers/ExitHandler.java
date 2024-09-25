package pt.isel.ls.handlers;

import pt.isel.ls.MovieReviewApp;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.ExitResult;

import java.sql.Connection;

public class ExitHandler implements CommandHandler {

    private final String pathTemplate = "EXIT";

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) throws Exception {
        MovieReviewApp.terminate();
        return new ExitResult();
    }

    @Override
    public String getCommandDescription() {
        return "EXIT:\n\t Exits the App";
    }

    @Override
    public String getPathTemplate() {
        return pathTemplate;
    }
}
