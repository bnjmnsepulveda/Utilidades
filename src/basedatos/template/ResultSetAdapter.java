
package basedatos.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Adaptador de java.sql.ResulSet a java.util.List.
 * 
 * @author benjamin
 * @param <T> Tipo de Objeto para adaptar.
 */
public abstract class ResultSetAdapter <T>{
    protected List<T> list;
    protected T elemento;
    public abstract List<T> retrieveList(ResultSet resultSet)throws SQLException;
    public abstract T retrieveElement(ResultSet resultSet)throws SQLException;
}

