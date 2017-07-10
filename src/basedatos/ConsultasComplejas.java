/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author Roberto
 */
public class ConsultasComplejas {
    
    /**
     * Lee la cantidad yel tipo de columnsas sql.
     * @param sql
     * @param headers
     * @return 
     */
     public Object read(String sql, String... headers) {

        try {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                //conn = ConexionPostgres.conectar();
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                ResultSetMetaData meta = rs.getMetaData();
                int colSize = meta.getColumnCount();

                System.out.println("columnas " + colSize);
                for (int x = 1; x <= colSize; x++) {
                    int tipo = meta.getColumnType(x);
                    switch (tipo) {
                        case Types.BIGINT:
                            System.out.println("bigint");
                            break;
                        case Types.BINARY:
                            System.out.println("binary");
                            break;
                        case Types.BIT:
                            System.out.println("bit");
                            break;
                        case Types.BLOB:
                            System.out.println("blob");
                            break;
                        case Types.BOOLEAN:
                            System.out.println("boolean");
                            break;
                        case Types.CHAR:
                            System.out.println("char");
                            break;
                        case Types.CLOB:
                            System.out.println("clob");
                            break;
                        case Types.DATALINK:
                            System.out.println("datalink");
                            break;
                        case Types.DATE:
                            System.out.println("date");
                            break;
                        case Types.DECIMAL:
                            System.out.println("decimal");
                            break;
                        case Types.DISTINCT:
                            System.out.println("distinct");
                            break;
                        case Types.DOUBLE:
                            System.out.println("double");
                            break;
                        case Types.FLOAT:
                            System.out.println("float");
                            break;
                        case Types.INTEGER:
                            System.out.println("integer");
                            break;
                        case Types.JAVA_OBJECT:
                            System.out.println("javaobject");
                            break;
                        case Types.LONGNVARCHAR:
                            System.out.println("longnvarchar");
                            break;
                        case Types.LONGVARBINARY:
                            System.out.println("longvarbinary");
                            break;
                        case Types.LONGVARCHAR:
                            System.out.println("longvarchar");
                            break;
                        case Types.NCHAR:
                            System.out.println("nchar");
                            break;
                        case Types.NCLOB:
                            System.out.println("nclob");
                            break;
                        case Types.NULL:
                            System.out.println("null");
                            break;
                        case Types.NUMERIC:
                            System.out.println("numeric");
                            break;
                        case Types.NVARCHAR:
                            System.out.println("nvarchar");
                            break;
                        case Types.OTHER:
                            System.out.println("other");
                            break;
                        case Types.REAL:
                            System.out.println("real");
                            break;
                        case Types.REF:
                            System.out.println("ref");
                            break;
                        case Types.ROWID:
                            System.out.println("rowid");
                            break;
                        case Types.SMALLINT:
                            System.out.println("smallint");
                            break;
                        case Types.SQLXML:
                            System.out.println("sqlxml");
                            break;
                        case Types.STRUCT:
                            System.out.println("struct");
                            break;
                        case Types.TIME:
                            System.out.println("time");
                            break;
                        case Types.TIMESTAMP:
                            System.out.println("timestamp");
                            break;
                        case Types.TINYINT:
                            System.out.println("tinyint");
                            break;
                        case Types.VARBINARY:
                            System.out.println("varbinary");
                            break;
                        case Types.VARCHAR:
                            System.out.println("varchar");
                            break;
                        default:
                            System.out.println("NO ENCONTRADO");
                    }

                }

                while (rs.next()) {
                    
                    System.out.println("rs:" +rs.toString());
                }

            } catch (SQLException e) {
               
            } finally {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }
        }catch (Exception e) {
           
        }
        return null;
    }

}
