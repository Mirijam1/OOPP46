package nl.tudelft.gogreen.shared;

public class StatusCodes {
    // Auth related
    public static final int AUTHENTICATED = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;

    // Request related
    public static final int NOT_FOUND = 404;

    // Submit related
    public static final int MISSING_FIELDS = 400;
    public static final int ALREADY_EXISTS = 409;
}
