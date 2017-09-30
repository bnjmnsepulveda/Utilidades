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
public abstract class AbstractCRUDTemplate2<T> {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    protected abstract Connection getConection() throws SQLException;

    protected abstract List<T> retrieveEntityList(ResultSet rs) throws SQLException;

    protected abstract T retrieveEntity(ResultSet rs) throws SQLException;

    protected List<T> select(String sql) {
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

    protected List<T> select2(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        List<T> entities = retrieveEntityList(resultSet);
        return entities;
    }

    protected List<T> select2(String sql, PreparedStatementListener parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement = parametros.addParametros(preparedStatement);
        resultSet = preparedStatement.executeQuery();
        List<T> entities = retrieveEntityList(resultSet);
        return entities;
    }

    protected T selectOne2(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        T entity = retrieveEntity(resultSet);
        return entity;
    }

    protected T selectOne2(String sql, PreparedStatementListener parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement = parametros.addParametros(preparedStatement);
        resultSet = preparedStatement.executeQuery();
        T entity = retrieveEntity(resultSet);
        return entity;
    }
    
    protected int save(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        int resultado = preparedStatement.executeUpdate();
        return resultado;
    }

    protected int save(String sql, PreparedStatementListener parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement = parametros.addParametros(preparedStatement);
        int resultado = preparedStatement.executeUpdate();
        return resultado;
    }

    protected List<T> select(String sql, PreparedStatementListener parametros) {
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

    protected T selectOne(String sql, PreparedStatementListener parametros) {
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

    protected int update(String sql) {
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

    

    protected int update(String sql, PreparedStatementListener parametros) {
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

    public void closeResources() {
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
            if (connection != null) {
                connection.close();
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