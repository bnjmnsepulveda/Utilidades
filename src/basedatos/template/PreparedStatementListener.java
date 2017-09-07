
package basedatos.template;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Roberto
 */
public interface PreparedStatementListener {
    PreparedStatement addParametros(PreparedStatement ps) throws SQLException;
}
