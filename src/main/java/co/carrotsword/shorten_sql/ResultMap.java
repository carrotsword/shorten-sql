package co.carrotsword.shorten_sql;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class ResultMap extends HashMap<String, Object> {

    public String getString(String key) {
        return (String) get(key);
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    public Date getDate(String key){
        return (Date) get(key);
    }

    public BigDecimal getBigDecimal(String key){
        return (BigDecimal) get(key);
    }

}
