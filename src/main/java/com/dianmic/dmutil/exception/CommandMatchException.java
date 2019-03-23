package com.dianmic.dmutil.exception;

public class CommandMatchException extends Exception {

    private static final long serialVersionUID = -2603880946364222202L;

    public CommandMatchException() {
        super("command match failed");
    }

    public CommandMatchException(String message) {
        super(message);
    }

    public CommandMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandMatchException(Throwable cause) {
        super(cause);
    }
}
