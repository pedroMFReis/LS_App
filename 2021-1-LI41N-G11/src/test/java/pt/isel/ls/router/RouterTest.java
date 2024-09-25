package pt.isel.ls.router;

import org.junit.Test;

import pt.isel.ls.command.CommandPath;

import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.exceptions.MyInvalidPathException;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.ExitHandler;

import pt.isel.ls.handlers.dbhandlers.get.GetMovieByIdHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetMovieRatingHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetMovieReviewByIdHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetMovieReviewsHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetMoviesHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetTopRatingsHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetUserReviewByIdHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetUserReviewsHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetUsersByIdHandler;
import pt.isel.ls.handlers.dbhandlers.get.GetUsersHandler;

import pt.isel.ls.handlers.dbhandlers.post.PostMovieRatingHandler;
import pt.isel.ls.handlers.dbhandlers.post.PostMovieReviewHandler;
import pt.isel.ls.handlers.dbhandlers.post.PostMoviesHandler;
import pt.isel.ls.handlers.dbhandlers.post.PostUsersHandler;

import java.util.Arrays;
import java.util.List;

public class RouterTest {
    private Router router = new Router();
    private CommandHandler cmdHandler;
    private static List<CommandHandler> handlers = null;

    private static void startHandlers() {
        handlers = Arrays.asList(new ExitHandler(), new GetMovieByIdHandler(),
                new GetMovieRatingHandler(), new GetMovieReviewByIdHandler(), new GetMovieReviewsHandler(),
                new GetMoviesHandler(), new GetTopRatingsHandler(), new GetUserReviewByIdHandler(),
                new GetUserReviewsHandler(), new GetUsersByIdHandler(), new GetUsersHandler(),
                new PostMovieRatingHandler(), new PostMovieReviewHandler(), new PostMoviesHandler(),
                new PostUsersHandler());
    }

    private void init() {
        startHandlers();
        router.setRoutes(handlers);
    }

    @Test
    public void postUsersRouterTest() throws MyInvalidPathException, MyInvalidCommandException {
        init();

        CommandHandler expectedHandler = new PostUsersHandler();
        cmdHandler = router.findRoute("POST", new CommandPath(" /users"));

        assert (cmdHandler.getClass().isInstance(expectedHandler));

    }

    @Test
    public void getMovieByIdHandler() throws MyInvalidPathException, MyInvalidCommandException {
        init();

        CommandHandler expectedHandler = new GetMovieByIdHandler();
        cmdHandler = router.findRoute("GET", new CommandPath(" /movies/2"));

        assert (cmdHandler.getClass().isInstance(expectedHandler));

    }
}