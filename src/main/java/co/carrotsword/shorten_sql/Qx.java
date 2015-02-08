package co.carrotsword.shorten_sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class Qx {

  StringBuilder sql = new StringBuilder();
  List<Qp<?>> parameters = new ArrayList<>();

  public Qx(String... sqlpart) {
    add(sqlpart);
  }

  public Qx(String sqlpart) {
    this.sql.append(sqlpart);
  }

  public Qx(String sqlpart, Qp<?>... params) {
    add(sqlpart, params);
  }

  public static Qx SQL(String... sqlpart) {
    return new Qx(sqlpart);
  }

  public static Qx SQL(String sqlpart) {
    return new Qx(sqlpart);
  }

  public static Qx SQL(String sqlpart, Qp<?>... params) {
    return new Qx(sqlpart, params);
  }

  public static <T> Qp<T> P(T parameter) {
    return new Qp(parameter);
  }

  public void add(String... sqlpart) {
    for (String q : sqlpart) {
      sql.append(q).append(" ");
    }
  }

  public void add(Qp<?>... params) {
    for (Qp<?> param : params) {
      this.parameters.add(param);
    }
  }

  public void add(String sqlpart, Qp<?>... params) {
    sql.append(sqlpart);
    add(params);
  }

  ConnectionProvider getConnectionProvider() {
    ServiceLoader<ConnectionProvider> loader = ServiceLoader.load(ConnectionProvider.class);
    Iterator<ConnectionProvider> iterator = loader.iterator();
    return iterator.next();
  }

  public List<ResultMap> list() {
    ConnectionProvider provider = getConnectionProvider();
    return list(provider.getConnection());
  }

  public List<ResultMap> list(Connection connection) {
    return list(connection, 0, 0);
  }

  public List<ResultMap> list(int offset, int length) {
    ConnectionProvider provider = getConnectionProvider();
    return list(provider.getConnection(), offset, length);
  }

  public List<ResultMap> list(Connection connection, int offset, int length) {
    try (PreparedQx qx = new PreparedQx(connection, sql.toString())) {
      qx.setParameters(parameters);
      return qx.list(offset, length);

    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  public ResultMap get() {
    ConnectionProvider provider = getConnectionProvider();
    return get(provider.getConnection());
  }

  public ResultMap get(Connection connection) {
    List<ResultMap> list = list(connection, 0, 1);
    if (list == null || list.size() == 0) {
      return new ResultMap();
    }
    return list.get(0);
  }

  public PreparedQx prepare() throws SQLRuntimeException {
    ConnectionProvider provider = getConnectionProvider();
    return prepare(provider.getConnection());
  }

  public PreparedQx prepare(Connection connection) throws SQLRuntimeException {
    try {
      return new PreparedQx(connection, sql.toString());
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  public int update() throws SQLRuntimeException {
    ConnectionProvider provider = getConnectionProvider();
    return update(provider.getConnection());
  }

  public int update(Connection connection) throws SQLRuntimeException {
    try (PreparedQx qx = new PreparedQx(connection, sql.toString())) {
      qx.setParameters(parameters);
      return qx.update();
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }

  public boolean execute() throws SQLRuntimeException {
    ConnectionProvider provider = getConnectionProvider();
    return execute(provider.getConnection());
  }

  public boolean execute(Connection connection) throws SQLRuntimeException {
    try (PreparedQx qx = new PreparedQx(connection, sql.toString())) {
      qx.setParameters(parameters);
      return qx.execute();
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
  }
}
