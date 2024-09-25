package pt.isel.ls.commands;

import org.junit.Assert;
import org.junit.Test;

import pt.isel.ls.command.CommandPath;

import pt.isel.ls.exceptions.MyInvalidParametersException;

public class CommandPathTest {
    CommandPath path;

    @Test
    public void zeroPathParameterTest() throws MyInvalidParametersException {
        path = new CommandPath("/users/movies");

        Assert.assertNull(path.getParameters(0));
    }

    @Test
    public void onePathParameterTest() throws MyInvalidParametersException {
        path = new CommandPath("/users/1/movies");
        //should return pathParameter = 1
        Assert.assertEquals(1, Integer.parseInt(path.getParameters(1)[0]));
    }

    @Test
    public void twoPathParameterTest() throws MyInvalidParametersException {
        path = new CommandPath("/users/1/movies/1");
        //should return pathParameter = {1,1}
        String[] expected = {"1", "1"};
        Assert.assertEquals(expected, path.getParameters(2));
    }

    @Test(expected = MyInvalidParametersException.class)
    public void moreThanTwoPathParameterTest() throws MyInvalidParametersException {
        path = new CommandPath("/users/1/movies/1/review/1/rating/1");
        //should throw an exception
        path.getParameters(4);
    }
}
