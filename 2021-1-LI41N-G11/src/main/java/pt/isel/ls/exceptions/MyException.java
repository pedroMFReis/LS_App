package pt.isel.ls.exceptions;

public abstract class MyException extends Exception {

    private String msg;

    public MyException(String msg) {
        this.msg = msg;
    }

    public String print() {
        return msg;
    }
}



