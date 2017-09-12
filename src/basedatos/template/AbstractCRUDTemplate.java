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
 * @param <T> Objeto de dominio relacionado con modelo ER de base de datos.
 */
public abstract class AbstractCRUDTemplate<T> {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    protected abstract Connection getConection() throws SQLException;

    protected abstract List<T> retrieveEntityList(ResultSet rs) throws SQLException;

    protected abstract T retrieveEntity(ResultSet rs) throws SQLException;

    public List<T> select(String sql) {
        List<T> entities = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection cx = null;
        try {
            cx = getConection();
            ps = cx.prepareStatement(sql);
            rs = ps.executeQuery();
            entities = retrieveEntityList(rs);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            closeResources(ps, rs, cx);
        }
        return entities;
    }

    public List<T> select(String sql, PreparedStatementListener parametros) {
        List<T> entities = null;
        PreparedStatement ps = null;
        ResultSet resultset = null;
        Connection cx = null;
        try {
            cx = getConection();
            ps = cx.prepareStatement(sql);
            ps = parametros.addParametros(ps);
            resultset = ps.executeQuery();
            entities = retrieveEntityList(resultset);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            closeResources(ps, resultset, cx);
        }
        return entities;
    }

    public T selectOne(String sql, PreparedStatementListener parametros) {
        T entity = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection cx = null;
        try {
            cx = getConection();
            ps = cx.prepareStatement(sql);
            ps = parametros.addParametros(ps);
            rs = ps.executeQuery();
            entity = retrieveEntity(rs);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            closeResources(ps, rs, cx);
        }
        return entity;
    }

    public int update(String sql) {
        int resultado = -1;
        Connection cx = null;
        PreparedStatement ps = null;
        try {
            cx = getConection();
            ps = cx.prepareStatement(sql);
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            closeResources(ps, null, cx);
        }
        return resultado;
    }

    public int update(String sql, PreparedStatementListener parametros) {
        int resultado = -1;
        Connection cx = null;
        PreparedStatement ps = null;
        try {
            cx = getConection();
            ps = cx.prepareStatement(sql);
            ps = parametros.addParametros(ps);
            resultado = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            closeResources(ps, null, cx);
        }
        return resultado;
    }

    public void closeResources(PreparedStatement preparedStatement, ResultSet resultSet, Connection conexion) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

}
