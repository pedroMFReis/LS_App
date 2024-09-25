package pt.isel.ls.db;

import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertTrue;

public class DatabaseTests {
    private static final String URL = "jdbc:postgresql://%s:%d/%s?user=%s&password=%s&currentSchema=%s";
    private static final String SERVER_NAME = "localhost";
    private static final int PORT = 5432;
    private static final String DATA_BASE = "moviereview";
    private static final String SCHEMA = "public";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";


    private PGSimpleDataSource ds;
    private Connection cns = null;
    private ResultSet rst = null;
    private Statement stm = null;


    @Test
    public void startConnection() throws SQLException {
        ds = new PGSimpleDataSource();
        ds.setURL(String.format(URL, SERVER_NAME, PORT, DATA_BASE, USERNAME, PASSWORD, SCHEMA));

        cns = ds.getConnection();
        cns.setAutoCommit(false);

        System.out.println("Connection Established!");
    }

    public void printTable() throws SQLException {
        rst = stm.executeQuery("select * from users;");
        while (rst.next()) {
            int idUser = rst.getInt("idUser");
            String name = rst.getString("name");
            String email = rst.getString("email");

            System.out.println(idUser + " - " + name + "-" + email);
        }
    }

    @Test
    public void selectTest() throws SQLException {
        try {
            startConnection();

            stm = cns.createStatement();
            boolean result = stm.execute("select * from users;");
            printTable();

            assertTrue(result);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cns.rollback();
            cns.close();
        }
    }

    @Test
    public void insertTest() throws SQLException {

        try {
            startConnection();
            stm = cns.createStatement();
            stm.execute("INSERT INTO users(name,email) VALUES\n"
                    + "\t('Teste1','Teste1@gmail.com'),\n"
                    + "\t('Teste2','hash@isel.pt.Teste1'),\n"
                    + "\t('Teste3','Teste3@Teste1.com');");
            printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cns.rollback();
            cns.close();
        }
    }

    @Test
    public void deleteTest() throws SQLException {
        try {
            startConnection();
            stm = cns.createStatement();
            stm.execute("DELETE FROM review;");
            rst = stm.executeQuery("select * from review;");
            if (!rst.next()) {
                System.out.println("Table is empty");
            } else {
                printTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cns.rollback();
            cns.close();
        }
    }

    @Test
    public void updateTest() throws SQLException {
        try {
            startConnection();
            stm = cns.createStatement();
            stm.execute("UPDATE users SET name = 'Miguel', email = 'email@isel.com' WHERE idUser = 3;");
            printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cns.rollback();
            cns.close();
        }
    }
}
