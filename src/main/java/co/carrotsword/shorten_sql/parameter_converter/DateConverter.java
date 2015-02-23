package co.carrotsword.shorten_sql.parameter_converter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author carrotsword
 * @since 15/02/17
 */
public class DateConverter implements ParameterConverter<Date, Timestamp> {
  @Override
  public Timestamp convert(Date parameter) {
    return new Timestamp(parameter.getTime());
  }
}
