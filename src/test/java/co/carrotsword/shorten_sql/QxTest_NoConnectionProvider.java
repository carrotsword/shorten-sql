package co.carrotsword.shorten_sql;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static co.carrotsword.shorten_sql.Qx.P;
import static co.carrotsword.shorten_sql.Qx.SQL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author carrotsword
 * @since 15/02/08
 */
public class QxTest_NoConnectionProvider {

  @Test
  public void paging() throws Exception{
    String url = "jdbc:derby:memory:target/test-resources/.javadb/sample;create=true";
    try(Connection connection = DriverManager.getConnection(url)){
      SQL("create table test_table ( col1 varchar(20) )").execute(connection);

      for(int i=0;i<50; i++){
        SQL("insert into test_table(col1) values (?)" , P(String.format("%03d", i))).update(connection);
      }
      List<ResultMap> list = SQL("select * from test_table").list(connection, 0, 10);

      assertThat(list.size(), is(10));
      assertThat(list.get(0).getString("COL1"), is("000"));
      assertThat(list.get(list.size()-1).getString("COL1"), is("009"));

      List<ResultMap> list2 = SQL("select * from test_table").list(connection, 5, 10);

      assertThat(list2.size(), is(10));
      assertThat(list2.get(0).getString("COL1"), is("005"));
      assertThat(list2.get(list2.size()-1).getString("COL1"), is("014"));

      SQL("drop table test_table").execute(connection);
    }
  }

  @Test
  public void noPaging() throws Exception{
    String url = "jdbc:derby:memory:target/test-resources/.javadb/sample;create=true";
    try(Connection connection = DriverManager.getConnection(url)){
      SQL("create table test_table ( col1 varchar(20) )").execute(connection);

      for(int i=0;i<50; i++){
        SQL("insert into test_table(col1) values (?)" , P(String.format("%03d", i))).update(connection);
      }

      List<ResultMap> list = SQL("select * from test_table").list(connection);

      assertThat(list.size(), is(50));
      assertThat(list.get(0).getString("COL1"), is("000"));
      assertThat(list.get(list.size()-1).getString("COL1"), is("049"));

      SQL("drop table test_table").execute();
    }
  }

  @Test
  public void selectWithASKeyword() throws Exception{
    String url = "jdbc:derby:memory:target/test-resources/.javadb/sample;create=true";
    try(Connection connection = DriverManager.getConnection(url)){
      SQL("create table test_table ( col1 varchar(20) )").execute(connection);
      SQL("insert into test_table(col1) values (?)" , P("001")).update(connection);

      List<ResultMap> list = SQL("select col1 as col2 from test_table").list(connection);

      assertThat(list.size(), is(1));
      assertThat(list.get(0).getString("COL1"), is(nullValue()));
      assertThat(list.get(0).getString("COL2"), is("001"));

      SQL("drop table test_table").execute();
    }
  }
}
