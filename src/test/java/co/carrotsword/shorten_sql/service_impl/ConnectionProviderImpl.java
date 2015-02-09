package co.carrotsword.shorten_sql.service_impl;

import co.carrotsword.shorten_sql.ConnectionProvider;
import co.carrotsword.shorten_sql.SQLRuntimeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author carrotsword
 * @since 15/02/08
 */
public class ConnectionProviderImpl implements ConnectionProvider {

  static Connection connection;

  final String jdbcurl = "jdbc:derby:memory:target/test-resources/.javadb/sample;create=true";

  @Override
  public Connection getConnection() {

    if(connection != null){
      return connection;
    }

    try {
      connection = DriverManager.getConnection(jdbcurl);
      return connection;
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }
}
