package co.carrotsword.shorten_sql;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class SQLRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -863543250505142845L;

    public SQLRuntimeException() {
    }

    public SQLRuntimeException(String message) {
        super(message);
    }

    public SQLRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLRuntimeException(Throwable cause) {
        super(cause);
    }

    public SQLRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
