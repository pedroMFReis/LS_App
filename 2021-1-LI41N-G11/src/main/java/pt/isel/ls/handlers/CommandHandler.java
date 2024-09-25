package pt.isel.ls.handlers;

import pt.isel.ls.command.CommandRequest;

import pt.isel.ls.results.CommandResult;

import java.sql.Connection;

public interface CommandHandler {

    CommandResult execute(CommandRequest cmdReq, Connection cns) throws Exception;

    String getCommandDescription();

    String getPathTemplate();

}
