package co.carrotsword.shorten_sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class PreparedQx implements AutoCloseable {

  PreparedStatement statement;

  PreparedQx(Connection connection, String sql) throws SQLException {
    statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
  }

  @Override
  public void close() throws SQLException {
    if (statement == null)
      return;
    statement.close();
  }

  public void setParameters(List<Qp<?>> parameters) throws SQLException {
    for (int i = 0; i < parameters.size(); i++) {
      parameters.get(i).setToStatement(i + 1, statement);
    }

  }

  public int update() throws SQLException {
    return statement.executeUpdate();
  }

  public List<ResultMap> list(int offset, int length) throws SQLException {
    statement.setFetchDirection(ResultSet.FETCH_FORWARD);
    statement.setFetchSize(length);
    ResultSet resultSet = statement.executeQuery();
    resultSet.setFetchDirection(ResultSet.FETCH_FORWARD);
    resultSet.absolute(offset);
    List<ResultMap> result = new ArrayList<>(length);
    int limit = (length == 0) ? Integer.MAX_VALUE : length; // ?
    for (int i = 0; i < limit && resultSet.next(); i++) {
      ResultMap map = new ResultMap();
      ResultSetMetaData metaData = resultSet.getMetaData();
      for (int j = 0; j < metaData.getColumnCount(); j++) {
        String colName = metaData.getColumnName(j + 1);
        map.put(colName, resultSet.getObject(colName));
      }
      result.add(map);
    }
    return result;
  }

  public boolean execute() throws SQLException {
    return statement.execute();
  }
}
