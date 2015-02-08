package co.carrotsword.shorten_sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class Qp<T> {

    T parameter;

    public Qp(T param) {
        parameter = param;
    }

    void setToStatement(int index, PreparedStatement statement) throws SQLException {
      statement.setObject(index, parameter);
    }
}
