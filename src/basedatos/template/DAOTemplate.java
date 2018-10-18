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
public abstract class DAOTemplate<T> {

    private boolean closeConnection;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public DAOTemplate() {
        closeConnection = true;
    }

    public DAOTemplate(Connection connection) {
        this.connection = connection;
        closeConnection = false;
    }

    protected abstract Connection buildConnection() throws SQLException;

    protected abstract T adapterEntity(ResultSet rs) throws SQLException;

    protected List<T> select(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        List<T> entities = new ArrayList();
        while (resultSet.next()) {
            entities.add(adapterEntity(resultSet));
        }
        return entities;
    }
    
    protected List<T> readAll(String sql, Object... parametros) {
        List<T> list = null;
        try {
            list = select(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return list;
    }
     
    protected T selectOne(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        T entity = null;
        if (resultSet.next()) {
            entity = adapterEntity(resultSet);
        }
        return entity;
    }
    
    protected T read(String sql, Object... parametros) {
        T entity = null;
        try {
            entity = selectOne(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return entity;
    }

    protected int selectInt(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        int selectInt = 0;
        if (resultSet.next()) {
            selectInt = resultSet.getInt(1);
        }
        return selectInt;
    }
    
    protected int readInt(String sql, Object... parametros) {
        int numeroInt = -1;
        try {
            numeroInt = selectInt(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return numeroInt;
    }

    protected long selectLong(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        long selectLong = 0;
        if (resultSet.next()) {
            selectLong = resultSet.getLong(1);
        }
        return selectLong;
    }
    
    protected long readLong(String sql, Object... parametros) {
        long numeroLong = -1;
        try {
            numeroLong = selectLong(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return numeroLong;
    }

    protected double selectDouble(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        double selectDouble = 0;
        if (resultSet.next()) {
            selectDouble = resultSet.getDouble(1);
        }
        return selectDouble;
    }
    
    protected double readDouble(String sql, Object... parametros) {
        double numeroDouble = -1;
        try {
            numeroDouble = selectDouble(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return numeroDouble;
    }

    protected String selectString(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        String selectString = null;
        if (resultSet.next()) {
            selectString = resultSet.getString(1);
        }
        return selectString;
    }
    
    protected String readString(String sql, Object... parametros) {
        String stringResultado = null;
        try {
            stringResultado = selectString(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return stringResultado;
    }
    
    protected List<String> selectListString(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        List<String> stringList = new ArrayList();
        while (resultSet.next()) {
            stringList.add(resultSet.getString(1));
        }
        return stringList;
    }
    
    protected List<String> readListString(String sql, Object... parametros) {
        List<String> listString = null;
        try {
            listString = selectListString(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return listString;
    }

    protected boolean selectBoolean(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        resultSet = preparedStatement.executeQuery();
        boolean selectboolean = false;
        if (resultSet.next()) {
            selectboolean = resultSet.getBoolean(1);
        }
        return selectboolean;
    }
    
    protected boolean readBoolean(String sql, Object... parametros) {
        boolean booleanResultado = false;
        try {
            booleanResultado = selectBoolean(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return booleanResultado;
    }

    protected int save(String sql, Object... parametros) throws SQLException {
        if(closeConnection) {
            connection = buildConnection();
        }
        preparedStatement = connection.prepareStatement(sql);
        addParams(parametros);
        int resultado = preparedStatement.executeUpdate();
        return resultado;
    }
    
    /**
     * Actualiza el modelo de base de datos, este metodo controla la apertura 
     * y cierre de recursos JDBC.
     * @param sql
     * @param parametros
     * @return 
     */
    protected int update(String sql, Object... parametros) {
        int resultadoOperacion = -1;
        try {
            resultadoOperacion = save(sql, parametros);
        } catch (SQLException e) {
           throw  new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
           throw  new RuntimeException(e.getMessage(), e);
        } finally {
            closeResources();
        }
        return resultadoOperacion;
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
            if (connection != null && closeConnection) {
                connection.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void closeConnection() {
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
    private void addParams(Object... parametros) throws SQLException {
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
            } else if (parametro instanceof Boolean) {
                preparedStatement.setBoolean(index, Boolean.parseBoolean(parametro.toString().trim()));
            } else if (parametro == null) {
                String nombreClaseEsperada = preparedStatement.getParameterMetaData().getParameterClassName(index);
                if (nombreClaseEsperada.equals("java.lang.String")) {
                    preparedStatement.setString(index, null);
                } else if (nombreClaseEsperada.equals("java.lang.Integer")) {
                    preparedStatement.setInt(index, 0);
                } else if (nombreClaseEsperada.equals("java.lang.Long")) {
                    preparedStatement.setLong(index, 0);
                } else if (nombreClaseEsperada.equals("java.lang.Double")) {
                    preparedStatement.setDouble(index, 0);
                } else if (nombreClaseEsperada.equals("java.lang.Boolean")) {
                    preparedStatement.setBoolean(index, false);
                } else if (nombreClaseEsperada.equals("java.util.Date")) {
                    preparedStatement.setTimestamp(index, null);
                } else {
                    String tipoBasedatos = preparedStatement.getParameterMetaData()
                            .getParameterTypeName(index);
                    throw new NullPointerException("parametro en index " + index
                            + " es null, necesita setear la clase "
                            + nombreClaseEsperada + " para el tipo " + tipoBasedatos);
                }

            } else {
                throw new IllegalArgumentException(parametro.getClass().getName() + " not supported for PreparedStatement, parameter nº " + index);
            }
            index++;
        }
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
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
