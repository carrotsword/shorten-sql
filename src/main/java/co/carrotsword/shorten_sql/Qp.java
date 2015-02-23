package co.carrotsword.shorten_sql;

import co.carrotsword.shorten_sql.parameter_converter.ParameterConverter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author carrotsword
 * @since 15/02/07
 */
public class Qp<T> {

  final T parameter;

  final ParameterConverter<T, ?> converter;

  public Qp(T param) {
    parameter = param;
    converter = null;
  }

  public <U> Qp(T param, ParameterConverter<T, U> conv){
    parameter = param;
    converter = conv;
  }

  void setToStatement(int index, PreparedStatement statement) throws SQLException {

    if(converter == null) {
      statement.setObject(index, parameter);
    }else{
      statement.setObject(index, converter.convert(parameter));
    }
  }
}
