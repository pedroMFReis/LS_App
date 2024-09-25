package pt.isel.ls;

import org.postgresql.ds.PGSimpleDataSource;

import pt.isel.ls.command.CommandHeaders;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;

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
import pt.isel.ls.handlers.dbhandlers.get.GetRatingsHandler;

import pt.isel.ls.handlers.dbhandlers.post.PostMovieRatingHandler;
import pt.isel.ls.handlers.dbhandlers.post.PostMovieReviewHandler;
import pt.isel.ls.handlers.dbhandlers.post.PostMoviesHandler;
import pt.isel.ls.handlers.dbhandlers.post.PostUsersHandler;
import pt.isel.ls.http.ServletApp;
import pt.isel.ls.model.ModelElement;
import pt.isel.ls.model.Ratings;
import pt.isel.ls.results.CommandResult;

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

import pt.isel.ls.views.TextPlainView;
import pt.isel.ls.router.ViewsRouter;
import pt.isel.ls.views.View;

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

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static pt.isel.ls.http.ServletApp.stopServlet;



/*
 * to run in cmd terminal
 * java -cp build\classes\java\main;vendor\main\* pt.isel.ls.MovieReviewApp
 * */

public class MovieReviewApp {

    private static PGSimpleDataSource ds;
    private static Connection cns;
    public static List<CommandHandler> handlers = null;
    private static HashMap<Integer, Ratings> ratings = new HashMap<>();
    private static final String FILE_PATH = "fileoutput/";

    private static final Scanner inputScanner = new Scanner(System.in);

    private static Router router;
    private static ViewsRouter viewsRouter;
    private static boolean terminator = false;

    public static void main(String[] args) throws SQLException {

        //Start the DataSource
        ds = new PGSimpleDataSource();
        String dbUrl = System.getenv("JDBC_DATABASE_URL");

        if (dbUrl == null) {
            System.out.println("JDBC_DATABASE_URL error");
            return;
        }

        ds.setUrl(dbUrl);

        //Start the Router and ViewsRouter
        startRouters();

        getRatingsFromDb();

        String port = System.getenv("PORT");

        if (port != null) {
            String command = "LISTEN / port=" + port;
            executeCommand(command);
            System.out.println("Heroku Mode....");
        } else {

            // App was called  with arguments
            if (args.length > 0) {
                String command = "";
                // Concat arguments into a single string
                for (String argument : args) {
                    command = command.concat(argument).concat(" ");
                }
                // Execute command and terminate
                executeCommand(command);
                return;
            }

            // Iterative Mode
            run();
        }
    }

    public static void run() throws SQLException {
        do {

            System.out.print("> ");
            String input = inputScanner.nextLine();

            // Execute command
            executeCommand(input);
        } while (!terminator);
    }

    public static void executeCommand(String command) throws SQLException {
        System.out.println(command);
        CommandRequest cmdReq;
        try {
            cmdReq = new CommandRequest(command);
        } catch (MyInvalidParametersException e) {
            System.out.println(e.print());
            return;
        }


        try {
            //Starts the Connection
            cns = ds.getConnection();
            cns.setAutoCommit(false);

            //Execute the Command
            CommandHandler cmdHandler = router.findRoute(cmdReq.getMethod(), cmdReq.getPath());
            CommandResult cmdResult = cmdHandler.execute(cmdReq, cns);
            CommandHeaders cmdHeaders = cmdReq.getHeaders();

            View view;
            if (cmdHeaders != null) {
                HashMap<String, String> headers = cmdHeaders.getProprieties();

                if (headers.containsKey("accept")) {
                    view = viewsRouter.findView(headers.get("accept"), cmdResult.getClass());
                } else {
                    view = new TextPlainView();
                }
                view.setResult(cmdResult);

                if (headers.containsKey("file-name")) {
                    writeInFile(view.print(), headers.get("file-name"));
                } else {
                    System.out.println(view.print());

                }
            } else {
                view = new TextPlainView();
                view.setResult(cmdResult);
                System.out.println(view.print());
            }


        } catch (SQLException e) {
            System.out.println("Database Error Ocurred");
        } catch (MyInvalidPathException | MyInvalidParametersException | MyInvalidCommandException e) {
            System.out.println(e.print());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cns.commit();
            cns.rollback();
            cns.close();
        }
    }

    public static void startRouters() {
        router = new Router();
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

    public static void terminate() throws Exception {
        if (ServletApp.getServletIsOnline()) {
            stopServlet();
        }
        terminator = true;
    }

    public static HashMap<Integer, Ratings> getRatings() {
        return ratings;
    }

    public static void writeInFile(String string, String filename) {
        try {
            String file = FILE_PATH + filename;
            FileWriter htmlFile = new FileWriter(file);
            htmlFile.write(string);
            htmlFile.close();
            System.out.println("Result Writen to file " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getRatingsFromDb() throws SQLException {
        try {
            //Start Connection
            cns = ds.getConnection();
            cns.setAutoCommit(false);

            CommandHandler handler = new GetRatingsHandler();
            CommandResult rst = handler.execute(null, cns);

            for (ModelElement e : rst.getElements()) {
                Ratings r = (Ratings) e;
                ratings.put(r.getMovie(), r);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cns.commit();
            cns.rollback();
            cns.close();
        }
    }

}
