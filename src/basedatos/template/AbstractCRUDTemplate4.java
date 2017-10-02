package basedatos.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Crea operaciones de base de datos de forma dinamica, delegando solo la logica
 * de adaptacion de datos a un List,seteo de parametros y la consulta sql.
 *
 * @author benjamin
 * @param <T> Objeto de dominio relacionado con modelo ER de base de datos.
 */
public abstract class AbstractCRUDTemplate4<T> {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    protected abstract Connection getConection() throws SQLException;

    protected abstract T adapterEntity(ResultSet rs) throws SQLException;

    protected List<T> select(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        List<T> entities = new ArrayList();
        while(resultSet.next()){
            entities.add(adapterEntity(resultSet));
        }
        return entities;
    }

    protected List<T> select(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        List<T> entities = new ArrayList();
        while(resultSet.next()){
            entities.add(adapterEntity(resultSet));
        }
        return entities;
    }

    protected T selectOne(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        T entity = null;
        if(resultSet.next()){
            entity = adapterEntity(resultSet);
        }
        return entity;
    }

    protected T selectOne(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        T entity = null;
        if(resultSet.next()){
            entity = adapterEntity(resultSet);
        }
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

    protected double selectDouble(String sql) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();
        double selectDouble = 0;
        if (resultSet.next()) {
            selectDouble = resultSet.getDouble(1);
        }
        return selectDouble;
    }

    protected double selectDouble(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        double selectDouble = 0;
        if (resultSet.next()) {
            selectDouble = resultSet.getDouble(1);
        }
        return selectDouble;
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

    protected boolean selectBoolean(String sql, Object... parametros) throws SQLException {
        connection = getConection();
        preparedStatement = connection.prepareStatement(sql);
        addParametros(parametros);
        resultSet = preparedStatement.executeQuery();
        boolean selectboolean = false;
        if (resultSet.next()) {
            selectboolean = resultSet.getBoolean(1);
        }
        return selectboolean;
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
                throw new IllegalArgumentException(parametro.getClass().getName() + " not supported for PreparedStatement, parameter nº " + index);
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
