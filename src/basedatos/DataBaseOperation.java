package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Crea operaciones de base de datos de forma dinamica, delegando solo la logica
 * de adaptacion de datos a un List,seteo de parametros y la consulta sql.
 *
 * @author benjamin
 * @param <T>
 */
public class DataBaseOperation<T> {

    private Connection conexion;
    private static final Logger logger = Logger.getLogger(DataBaseOperation.class.getName());

    public DataBaseOperation(Connection conexion) {
        this.conexion = conexion;
    }
    
    public List<T> select(String sql, ResultSetAdapter adapter) {
        List<T> lista = null;
        try {
            PreparedStatement ps = null;
            ResultSet resultset = null;
            try {
                ps = conexion.prepareStatement(sql);
                resultset = ps.executeQuery();
                lista = adapter.retrieveList(resultset);

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (resultset != null) {
                    resultset.close();
                }
                if (conexion != null) {
               //     conexion.close();
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
        return lista;
    }

    public List<T> select(String sql, ResultSetAdapter adapter, PrepareStatementListener parametros) {
        List<T> lista = null;
        try {
            PreparedStatement ps = null;
            ResultSet resultset = null;
            try {
                ps = conexion.prepareStatement(sql);
                ps = parametros.getParametros(ps);
                resultset = ps.executeQuery();
                lista = adapter.retrieveList(resultset);

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (resultset != null) {
                    resultset.close();
                }
                if (conexion != null) {
                 //   conexion.close();
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
        return lista;
    }

    public int update(String sql) {
        int resultado = -1;
        try {
            PreparedStatement ps = null;
            try {
                ps = conexion.prepareStatement(sql);
                resultado = ps.executeUpdate();

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
        return resultado;
    }

    public int update(String sql, PrepareStatementListener parametros) {
        int resultado = -1;
        try {
            //Connection connection = Postgres.conectar();
            PreparedStatement ps = null;
            try {
                ps = conexion.prepareStatement(sql);
                ps = parametros.getParametros(ps);
                resultado = ps.executeUpdate();

            } catch (SQLException ex) {
                logger.log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (conexion != null) {
                   // conexion.close();
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, null, e);
        }
        return resultado;
    }

    /**
     * Permite agregar parametros a el PreparementStatement de la operacion de
     * base de datos.
     */
    public interface PrepareStatementListener {
        PreparedStatement getParametros(PreparedStatement ps) throws SQLException;
    }

    public interface SqlOperationListener {
        ResultSet config(Connection conexion, PreparedStatement ps) throws SQLException;
    }
}
