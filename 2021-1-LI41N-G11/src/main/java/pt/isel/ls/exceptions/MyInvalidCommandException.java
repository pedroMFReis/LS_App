package pt.isel.ls.exceptions;

public class MyInvalidCommandException extends MyException {

    public MyInvalidCommandException(String msg) {
        super(msg);
    }

    public MyInvalidCommandException() {
        super("!Invalid Command!");
    }
}
