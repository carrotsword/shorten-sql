package co.carrotsword.shorten_sql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static co.carrotsword.shorten_sql.Qx.P;
import static co.carrotsword.shorten_sql.Qx.SQL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class QxTest {

  @Test
  public void simpleUseCase() throws Exception {

    String jdbcurl = "jdbc:derby:memory:target/test-resources/.javadb/sample;create=true";

    try(Connection connection = DriverManager.getConnection(jdbcurl)) {
      boolean result = SQL("create table test_table (col1 varchar(10) not null, col2 int)").execute(connection);
      assertThat(result, is(false)); // No ResultSets

      int result2 = SQL("insert into test_table(col1, col2) values (?, ?)", P("test1"), P(10)).update(connection);
      assertThat(result2, is(1));

      List<ResultMap> list = SQL("select col1, col2 from test_table").list(connection);
      assertThat(list.size(), is(1));
      // Derby returns column names with upper case
      assertThat(list.get(0).getString("COL1"), is("test1"));
      assertThat(list.get(0).getInt("COL2"), is(10));
    }
  }

  @Test
  public void simpleUseCaseWithConnectionProvider() throws Exception {

    boolean result = SQL("create table test_table (col1 varchar(10) not null, col2 int)").execute();
    assertThat(result, is(false)); // No ResultSets

    int result2 = SQL("insert into test_table(col1, col2) values (?, ?)", P("test1"), P(10)).update();
    assertThat(result2, is(1));

    List<ResultMap> list = SQL("select col1, col2 from test_table").list();
    assertThat(list.size(), is(1));
    // Derby returns column names with upper case
    assertThat(list.get(0).getString("COL1"), is("test1"));
    assertThat(list.get(0).getInt("COL2"), is(10));
  }

}
