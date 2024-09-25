package pt.isel.ls.router;

import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.command.CommandPath;
import pt.isel.ls.handlers.CommandHandler;

import java.util.List;


public class Router {

    //The Tree that holds all the possible Paths
    private final RouterTree htree = new RouterTree();

    /**
     * Sets The Path for all the Given Handlers
     *
     * @param handlers Given Handlers
     */
    public void setRoutes(List<CommandHandler> handlers) {
        for (CommandHandler h : handlers) {
            htree.add(h, h.getPathTemplate());
        }
    }

    /**
     * Adds a path to the Given Handler
     *
     * @param handler Given Handler
     */
    public void addRoute(CommandHandler handler) {
        htree.add(handler, handler.getPathTemplate());
    }

    public CommandHandler findRoute(String method, CommandPath path) throws MyInvalidCommandException,
            MyInvalidPathException {
        CommandHandler cmdHandler = htree.find(method, path);
        if ((cmdHandler == null)) {
            throw new MyInvalidCommandException("Invalid Command, could not find handler");
        }
        return cmdHandler;
    }

}