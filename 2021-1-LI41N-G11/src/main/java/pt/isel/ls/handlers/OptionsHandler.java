package pt.isel.ls.handlers;

import pt.isel.ls.MovieReviewApp;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.OptionsResult;

import java.sql.Connection;

public class OptionsHandler implements CommandHandler {

    private final String pathTemplate = "OPTION";

    @Override
    public CommandResult execute(CommandRequest cmdReq, Connection cns) {
        OptionsResult opts = new OptionsResult();
        for (CommandHandler h : MovieReviewApp.handlers) {
            opts.addCommand(h.getCommandDescription());
        }
        return opts;
    }

    @Override
    public String getCommandDescription() {
        return "OPTION:\n\t Displays all Available Commands";
    }

    @Override
    public String getPathTemplate() {
        return pathTemplate;
    }
}
