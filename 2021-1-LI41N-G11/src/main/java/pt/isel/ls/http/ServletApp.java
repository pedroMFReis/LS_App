package pt.isel.ls.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.ExitHandler;
import pt.isel.ls.handlers.ListenHandler;
import pt.isel.ls.handlers.OptionsHandler;
import pt.isel.ls.handlers.dbhandlers.delete.DeleteMovieReview;

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

import pt.isel.ls.results.get.GetMovieByIdResult;
import pt.isel.ls.results.get.GetMovieRatingResult;
import pt.isel.ls.results.get.GetMovieReviewResult;
import pt.isel.ls.results.get.GetMovieReviewByIdResult;
import pt.isel.ls.results.get.GetMoviesResult;
import pt.isel.ls.results.get.GetTopRatingResult;
import pt.isel.ls.results.get.GetUserByIdResult;
import pt.isel.ls.results.get.GetUserReviewByIdResult;
import pt.isel.ls.results.get.GetUserReviewResult;
import pt.isel.ls.results.get.GetUsersResult;

import pt.isel.ls.router.Router;
import pt.isel.ls.router.ViewsRouter;

import pt.isel.ls.views.TextPlainView;

import pt.isel.ls.views.htmlviews.GetMovieByIdHtmlView;
import pt.isel.ls.views.htmlviews.GetMovieRatingHtmlView;
import pt.isel.ls.views.htmlviews.GetMovieReviewByIdHtmlView;
import pt.isel.ls.views.htmlviews.GetMovieReviewsHtmlView;
import pt.isel.ls.views.htmlviews.GetMoviesHtmlView;
import pt.isel.ls.views.htmlviews.GetTopRatingHtmlView;
import pt.isel.ls.views.htmlviews.GetUserByIdHtmlView;
import pt.isel.ls.views.htmlviews.GetUserReviewByIdHtmlView;
import pt.isel.ls.views.htmlviews.GetUserReviewHtmlView;
import pt.isel.ls.views.htmlviews.GetUsersHtmlView;
import pt.isel.ls.views.htmlviews.RootHtmlView;

import java.util.Arrays;
import java.util.List;

public class ServletApp {

    private static final Logger log = LoggerFactory.getLogger(ServletApp.class);

    /*
     * TCP port where to listen.
     * Standard port for HTTP is 80 but might be already in use
     */
    private static int LISTEN_PORT = 8080;
    static String portDef = System.getenv("PORT");
    static int port = portDef != null ? Integer.parseInt(portDef) : LISTEN_PORT;
    private static Server server;
    private static Router router = new Router();
    private static ViewsRouter viewsRouter = new ViewsRouter();
    public static List<CommandHandler> handlers = null;
    private static boolean servletIsOnline = false;

    public static void startServlet(int inPort) throws Exception {

        if (inPort > 1023) {
            LISTEN_PORT = inPort;
            port = inPort;
        }

        server = new Server(port);

        log.info("Servlet Started");
        log.info("configured listening port is {}", port);

        //Start the Router and ViewsRouter
        startRouters();
        ServletHandler handler = new ServletHandler();
        RootServlet root = new RootServlet(router, viewsRouter);

        handler.addServletWithMapping(new ServletHolder(root), "/*");
        log.info("registered {} on all paths", root);

        server.setHandler(handler);
        server.start();
        servletIsOnline = true;

        log.info("server started listening on port {}", port);
    }

    public static void stopServlet() throws Exception {

        server.stop();
        servletIsOnline = false;
        log.info("Servlet is ending");
    }

    public static boolean getServletIsOnline() {
        return servletIsOnline;
    }


    public static void startRouters() {

        startHandlers();
        router.setRoutes(handlers);

        //views router
        viewsRouter = new ViewsRouter();
        startViews();
    }

    private static void startHandlers() {
        handlers = Arrays.asList(new ExitHandler(), new OptionsHandler(), new GetMovieByIdHandler(),
                new GetMovieRatingHandler(), new GetMovieReviewByIdHandler(), new GetMovieReviewsHandler(),
                new GetMoviesHandler(), new GetTopRatingsHandler(), new GetUserReviewByIdHandler(),
                new GetUserReviewsHandler(), new GetUsersByIdHandler(), new GetUsersHandler(),
                new PostMovieRatingHandler(), new PostMovieReviewHandler(), new PostMoviesHandler(),
                new PostUsersHandler(), new DeleteMovieReview(), new ListenHandler());
    }

    private static void startViews() {
        viewsRouter.addView("text/plain", null, new TextPlainView());
        viewsRouter.addView("text/html", null, new RootHtmlView());
        viewsRouter.addView("text/html", GetMovieByIdResult.class, new GetMovieByIdHtmlView());
        viewsRouter.addView("text/html", GetMovieRatingResult.class, new GetMovieRatingHtmlView());
        viewsRouter.addView("text/html", GetMovieReviewByIdResult.class, new GetMovieReviewByIdHtmlView());
        viewsRouter.addView("text/html", GetMovieReviewResult.class, new GetMovieReviewsHtmlView());
        viewsRouter.addView("text/html", GetMoviesResult.class, new GetMoviesHtmlView());
        viewsRouter.addView("text/html", GetTopRatingResult.class, new GetTopRatingHtmlView());
        viewsRouter.addView("text/html", GetUserByIdResult.class, new GetUserByIdHtmlView());
        viewsRouter.addView("text/html", GetUserReviewByIdResult.class, new GetUserReviewByIdHtmlView());
        viewsRouter.addView("text/html", GetUserReviewResult.class, new GetUserReviewHtmlView());
        viewsRouter.addView("text/html", GetUsersResult.class, new GetUsersHtmlView());
    }
}