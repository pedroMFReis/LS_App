package pt.isel.ls.http;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.command.CommandRequest;
import pt.isel.ls.exceptions.MyInvalidCommandException;
import pt.isel.ls.exceptions.MyInvalidParametersException;
import pt.isel.ls.exceptions.MyInvalidPathException;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.results.CommandResult;
import pt.isel.ls.results.post.BasePostResult;
import pt.isel.ls.router.Router;
import pt.isel.ls.router.ViewsRouter;
import pt.isel.ls.views.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RootServlet extends HttpServlet {
    //    LISTEN / port=9999


    private static final Logger log = LoggerFactory.getLogger(RootServlet.class);

    private static final String COMMAND_ROOT = "GET /";

    private ViewsRouter viewsRouter;

    private Router router;

    public RootServlet(Router router, ViewsRouter viewsRouter) {
        super();
        this.router = router;
        this.viewsRouter = viewsRouter;
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("incoming request: queryString={}, method={}, uri={}, accept={}",
                req.getQueryString(),
                req.getMethod(),
                req.getRequestURI(),
                req.getHeader("Accept"));

        String dbUrl = System.getenv("JDBC_DATABASE_URL");

        CommandRequest cmdReq;

        String command = req.getMethod() + " " + req.getRequestURI();
        if (!(req.getQueryString() == null || req.getQueryString().equals("null"))) {
            command += " " + req.getQueryString();
        }


        try {
            cmdReq = new CommandRequest(command);
        } catch (MyInvalidParametersException e) {
            log.error(e.print());
            return;
        }
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(dbUrl);
        Connection cns = null;
        View view;

        try {
            //Starts the Connection
            cns = ds.getConnection();
            cns.setAutoCommit(false);

            CommandResult cmdResult = null;
            if (!command.equals(COMMAND_ROOT)) {
                CommandHandler cmdHandler = router.findRoute(cmdReq.getMethod(), cmdReq.getPath());
                cmdResult = cmdHandler.execute(cmdReq, cns);
            }


            String[] accept = req.getHeader("Accept").split(",", 2);

            view = viewsRouter.findView(accept[0], cmdResult == null ? null : cmdResult.getClass());

            view.setResult(cmdResult);


            Charset utf8 = StandardCharsets.UTF_8;
            resp.setContentType(String.format("text/html; charset=%s", utf8.name()));

            String respBody = "";
            if (view.hasResponse()) {
                respBody = view.print();
                resp.setStatus(200);
            } else {
                resp.setStatus(404);
            }


            //String respBody = String.format(html);
            byte[] respBodyBytes = respBody.getBytes(utf8);
            resp.setContentLength(respBodyBytes.length);
            OutputStream os = resp.getOutputStream();
            os.write(respBodyBytes);
            os.flush();
            log.info("outgoing response: method={}, uri={}, status={}, Content-Type={}",
                    req.getMethod(),
                    req.getRequestURI(),
                    resp.getStatus(),
                    resp.getHeader("Content-Type"));

        } catch (MyInvalidPathException e) {
            resp.setStatus(404);
            log.error(e.toString());
        } catch (MyInvalidParametersException | MyInvalidCommandException e) {
            log.error(e.toString());
        } catch (Exception e) {
            resp.setStatus(500);
        } finally {
            try {
                cns.commit();
                cns.rollback();
                cns.close();
            } catch (SQLException throwables) {
                // DO NOT DO THIS throwables.printStackTrace();
                log.error(throwables.toString());
                resp.setStatus(500);
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request: method={}, uri={}, accept={}",
                req.getMethod(),
                req.getRequestURI(),
                req.getHeader("Accept"));
        // Read from request
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }
        String data = buffer.toString().trim();


        String dbUrl = System.getenv("JDBC_DATABASE_URL");

        CommandRequest cmdReq;

        String command = req.getMethod() + " " + req.getRequestURI() + " " + data;
        try {
            cmdReq = new CommandRequest(command);
        } catch (MyInvalidParametersException e) {
            System.out.println(e.print());
            return;
        }
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setURL(dbUrl);
        Connection cns = null;
        try {
            //Starts the Connection
            cns = ds.getConnection();
            cns.setAutoCommit(false);

            CommandResult cmdResult = null;
            if (!command.equals(COMMAND_ROOT)) {
                CommandHandler cmdHandler = router.findRoute(cmdReq.getMethod(), cmdReq.getPath());
                cmdResult = cmdHandler.execute(cmdReq, cns);
            }


            Charset utf8 = StandardCharsets.UTF_8;
            resp.setContentType(String.format("text/html; charset=%s", utf8.name()));

            resp.setStatus(303);
            String uri = req.getRequestURI();
            if (((BasePostResult) cmdResult).getId() != -1) {
                uri += "/" + ((BasePostResult) cmdResult).getId();
            }

            resp.sendRedirect(uri);
        } catch (MyInvalidPathException e) {
            resp.setStatus(404);
            log.error(e.toString());
        } catch (MyInvalidParametersException | MyInvalidCommandException e) {
            log.error(e.toString());
        } catch (Exception e) {
            resp.setStatus(500);
        } finally {
            try {
                cns.commit();
                cns.rollback();
                cns.close();
            } catch (SQLException throwables) {
                // DO NOT DO THIS throwables.printStackTrace();
                log.error(throwables.toString());
                resp.setStatus(500);
            }
        }
    }
}
