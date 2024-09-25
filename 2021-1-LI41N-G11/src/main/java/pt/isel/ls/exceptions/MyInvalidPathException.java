package pt.isel.ls.exceptions;

public class MyInvalidPathException extends MyException {

    public MyInvalidPathException() {
        super("PATH NOT FOUND, INVALID COMMAND!");
    }

    public MyInvalidPathException(String str) {
        super(str);
    }

}
