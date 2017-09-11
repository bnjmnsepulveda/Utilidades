package basedatos.template;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Crea operaciones de base de datos de forma dinamica, delegando solo la logica
 * de adaptacion de datos a un List,seteo de parametros y la consulta sql.
 *
 * @author benjamin
 * @param <T>
 */
public abstract class AbstractCRUDTemplate<T> {

    private Connection conexion;

    public abstract List<T> retrieveList(ResultSet rs) throws SQLException;
    public abstract T retrieveElement(ResultSet rs) throws SQLException;
    
    public List<T> select(String sql) {
        List<T> lista = null;
        try {
            PreparedStatement ps = null;
            ResultSet resultset = null;
            try {
                ps = conexion.prepareStatement(sql);
                resultset = ps.executeQuery();
                lista = retrieveList(resultset);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (resultset != null) {
                    resultset.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return lista;
    }

    public List<T> select(String sql, PreparedStatementListener parametros) {
        List<T> lista = null;
        try {
            PreparedStatement ps = null;
            ResultSet resultset = null;
            try {
                ps = conexion.prepareStatement(sql);
                ps = parametros.addParametros(ps);
                resultset = ps.executeQuery();
                lista = retrieveList(resultset);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (resultset != null) {
                    resultset.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return lista;
    }

    public T selectOne(String sql, PreparedStatementListener parametros) {
        T entity = null;
        try {
            PreparedStatement ps = null;
            ResultSet resultset = null;
            try {
                ps = conexion.prepareStatement(sql);
                ps = parametros.addParametros(ps);
                resultset = ps.executeQuery();
                entity = retrieveElement(resultset);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } finally {
                if (ps != null) {
                    ps.close();
                }
                if (resultset != null) {
                    resultset.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return entity;
    }
    
    public int update(String sql) {
        int resultado = -1;
        try {
            PreparedStatement ps = null;
            try {
                ps = conexion.prepareStatement(sql);
                resultado = ps.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return resultado;
    }

    public int update(String sql, PreparedStatementListener parametros) {
        int resultado = -1;
        try {
            PreparedStatement ps = null;
            try {
                ps = conexion.prepareStatement(sql);
                ps = parametros.addParametros(ps);
                resultado = ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } finally {
                if (ps != null) {
                    ps.close();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return resultado;
    }

    public void closeConnection(Connection conexion){
        try {
            conexion.close();
        } catch (SQLException ex) {
           ex.printStackTrace(System.out);
        }
    }
    
    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
}
