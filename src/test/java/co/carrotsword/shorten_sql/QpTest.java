package co.carrotsword.shorten_sql;

import org.junit.Test;

import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static co.carrotsword.shorten_sql.Qx.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author rotsword
 * @since 15/02/17
 */
public class QpTest {

  @Test
  public void mainTargetTypes() throws Exception{

    String url = "jdbc:derby:memory:target/test-resources/.javadb/sample;create=true";

    try(Connection connection = DriverManager.getConnection(url)) {
      Qx ta = new Qx();
      ta.add("create table ta(");
      ta.add("  col1 varchar(20),");
      ta.add("  col2 integer,");
      ta.add("  col3 decimal(4,2),");
      ta.add("  col4 timestamp,");
      ta.add("  col5 clob,");
      ta.add("  col6 blob)");
      ta.execute(connection);

      // insert test data
      Calendar cal = new GregorianCalendar(2000,1,1,2,2,2);
      Qx insert = new Qx();
      insert.add("insert into ta(col1,col2,col3,col4,col5,col6)");
      insert.add("values (?,?,?,?,?,?)");

      insert.add(P("test"));
      insert.add(P(1));
      insert.add(P(11.11));
      insert.add(TIMESTAMP(cal.getTime()));
      insert.add(P("large text object"));
      insert.add(P("large binary object".getBytes()));

      insert.execute(connection);

      // get data
      ResultMap resultMap = SQL("select * from ta").get(connection);

      assertThat(resultMap.getString("col1"), is("test"));
      assertThat(resultMap.getInt("col2"), is(1));
      assertThat(resultMap.getBigDecimal("col3").doubleValue(), is(11.11));
      assertThat(resultMap.getDate("col4"), is(cal.getTime()));

      // clob
      try(InputStream stream = SQL("select col5 from ta").stream(connection, 0)) {
        StringWriter writer = new StringWriter();
        int c;
        while ((c = stream.read()) != -1) {
          writer.append((char) c);
        }
        assertThat(writer.toString(), is("large text object"));
      }

      // blob
      try(InputStream stream = SQL("select col6 from ta").stream(connection, 0)){
        StringWriter writer = new StringWriter();
        int c;
        while ((c = stream.read()) != -1) {
          writer.append((char) c);
        }
        assertThat(writer.toString(), is("large binary object"));
      }

      // drop table
      SQL("drop table ta").execute(connection);
    }

  }

}
