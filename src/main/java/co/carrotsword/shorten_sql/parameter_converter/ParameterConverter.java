package co.carrotsword.shorten_sql.parameter_converter;

/**
 * @author carrotsword
 * @since 15/02/17
 */
public interface ParameterConverter<T, U> {
  U convert(T parameter);
}
