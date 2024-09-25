package pt.isel.ls.exceptions;

public class MyInvalidParametersException extends pt.isel.ls.exceptions.MyException {

    public MyInvalidParametersException() {
        super("!Invalid Parameters!");
    }

    public MyInvalidParametersException(String str) {
        super(str);
    }

}
