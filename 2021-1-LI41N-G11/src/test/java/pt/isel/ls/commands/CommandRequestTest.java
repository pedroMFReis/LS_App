package pt.isel.ls.commands;

import org.junit.Assert;
import org.junit.Test;

import pt.isel.ls.command.CommandParam;
import pt.isel.ls.command.CommandPath;
import pt.isel.ls.command.CommandRequest;

import pt.isel.ls.exceptions.MyInvalidParametersException;

public class CommandRequestTest {

    @Test
    public void oneArgumentTest() throws MyInvalidParametersException {
        String command = "EXIT /";
        CommandRequest cmdRequest = new CommandRequest(command);
        Assert.assertEquals("EXIT", cmdRequest.getMethod());
    }

    @Test
    public void twoArgumentTest() throws MyInvalidParametersException {
        String command = "GET /users";
        CommandRequest cmdRequest = new CommandRequest(command);
        Assert.assertEquals("GET", cmdRequest.getMethod());
        CommandPath pathExpected = new CommandPath("/users");
        Assert.assertTrue(cmdRequest.getPath().getClass().isInstance(pathExpected));

    }

    @Test
    public void threeArgumentTest() throws MyInvalidParametersException {
        String command = "GET /users name=First";
        CommandRequest cmdRequest = new CommandRequest(command);
        Assert.assertEquals("GET", cmdRequest.getMethod());
        CommandPath pathExpected = new CommandPath("/users");
        Assert.assertTrue(cmdRequest.getPath().getClass().isInstance(pathExpected));

        CommandParam paramExpected = new CommandParam("name=First");
        Assert.assertEquals(paramExpected.getProprieties().get("name"),
                cmdRequest.getParameters().getProprieties().get("name"));

    }

}
