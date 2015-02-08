package co.carrotsword.shorten_sql;

import java.sql.Connection;

/**
 * @author carrotsword
 * @since 15/02/08
 */
public interface ConnectionProvider {

  Connection getConnection();

}
