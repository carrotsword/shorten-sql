package co.carrotsword.shorten_sql.parameter_converter;

import java.math.BigDecimal;

/**
 * @author carrotsword
 * @since 15/02/19
 */
public class NumberConverter implements ParameterConverter<Number, BigDecimal> {
  @Override
  public BigDecimal convert(Number parameter) {
    return new BigDecimal(String.valueOf(parameter));
  }
}
