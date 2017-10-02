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
 * @param <T> Objeto de dominio relacionado con entidad modelo ER de base de
 * datos.
 */
public abstract class AbstractCRUDTemplate3<T> {

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

    protected List<T> select2(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
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

    protected T selectOne2(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        T entity = retrieveEntity(resultSet);
        return entity;
    }

    protected int selectInt(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        int selectInt = 0;
        if (resultSet.next()) {
            selectInt = resultSet.getInt(1);
        }
        return selectInt;
    }

    protected int selectInt(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        int selectInt = 0;
        if (resultSet.next()) {
            selectInt = resultSet.getInt(1);
        }
        return selectInt;
    }

    protected long selectLong(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        long selectLong = 0;
        if (resultSet.next()) {
            selectLong = resultSet.getLong(1);
        }
        return selectLong;
    }

    protected long selectLong(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        long selectLong = 0;
        if (resultSet.next()) {
            selectLong = resultSet.getLong(1);
        }
        return selectLong;
    }

    protected String selectString(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        String selectString = null;
        if (resultSet.next()) {
            selectString = resultSet.getString(1);
        }
        return selectString;
    }

    protected String selectString(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        String selectString = null;
        if (resultSet.next()) {
            selectString = resultSet.getString(1);
        }
        return selectString;
    }

    protected int save(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        int resultado = preparedStatement.executeUpdate();
        return resultado;
    }

    protected int save(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
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

    /**
     * Agrega parametros a un preparedstatement dinamicamente identificando el
     * tipo de objeto y asignando su indice.
     *
     * @param parametros Ojetos a incluir en parametros de consulta, se agregan
     * sin importar la cantidad.
     * @throws SQLException
     */
    private void addParametros(Object... parametros) throws SQLException {
        int index = 1;
        for (Object parametro : parametros) {
            if (parametro instanceof String) {
                preparedStatement.setString(index, (String) parametro);
            } else if (parametro instanceof Integer) {
                preparedStatement.setInt(index, (int) parametro);
            } else if (parametro instanceof Long) {
                preparedStatement.setLong(index, (long) parametro);
            } else if (parametro instanceof Double) {
                preparedStatement.setDouble(index, (double) parametro);
            } else if (parametro instanceof java.util.Date) {
                java.util.Date time = (java.util.Date) parametro;
                preparedStatement.setTimestamp(index, new java.sql.Timestamp(time.getTime()));
            } else {
                throw new IllegalArgumentException(parametro.getClass().getName() + " not supported for PreparedStatement.");
            }
            index++;
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
