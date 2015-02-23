package co.carrotsword.shorten_sql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author carrotsword
 * @since 15/02/24
 */
public class ProxyInputStream extends InputStream {

  InputStream baseStream;
  Statement baseStatement;

  public ProxyInputStream(InputStream stream, Statement statement) {
    try {
      if (stream == null || statement == null || statement.isClosed()) {
        throw new IllegalArgumentException();
      }
    } catch (SQLException e) {
      throw new SQLRuntimeException(e);
    }
    this.baseStream = stream;
    this.baseStatement = statement;
  }

  @Override
  public int available() throws IOException {
    return baseStream.available();
  }

  @Override
  public void close() throws IOException {
    try {
      baseStream.close();
      baseStatement.close();
    } catch (SQLException e) {
      throw new SQLRuntimeException();
    }

  }

  @Override
  public boolean markSupported() {
    return baseStream.markSupported();
  }

  @Override
  public synchronized void mark(int readlimit) {
    baseStream.mark(readlimit);
  }

  @Override
  public int read() throws IOException {
    return baseStream.read();
  }

  @Override
  public int read(byte[] b) throws IOException {
    return baseStream.read(b);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    return baseStream.read(b, off, len);
  }

  @Override
  public synchronized void reset() throws IOException {
    baseStream.reset();
  }

  @Override
  public long skip(long n) throws IOException {
    return baseStream.skip(n);
  }
}
