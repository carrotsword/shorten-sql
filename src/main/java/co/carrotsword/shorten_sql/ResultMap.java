package co.carrotsword.shorten_sql;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class ResultMap extends HashMap<String, Object> {

  private static final long serialVersionUID = 6197505132318684701L;

  public String getString(String key) {
    return (String) get(key.toLowerCase());
  }

  public int getInt(String key) {
    return (int) get(key.toLowerCase());
  }

  public Date getDate(String key) {
    return (Date) get(key.toLowerCase());
  }

  public BigDecimal getBigDecimal(String key) {
    return (BigDecimal) get(key.toLowerCase());
  }

}
