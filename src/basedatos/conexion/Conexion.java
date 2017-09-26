/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Roberto
 */
public class Conexion {
    
    private static String basedatosIp;
    private static String basedatosNombre;
    private static String basedatosUsuario;
    private static String basedatosClave;
    private static final Logger logger = LogManager.getLogger(Conexion.class);

    /**
     * Conexion estatica generica para base de datos.
     * @return
     * @throws SQLException Cualquier problemas con autenticacion.
     */
    public static Connection connect() throws SQLException {
        Connection conexion = null;
        String url = "jdbc:postgresql://" + basedatosIp + "/" + basedatosNombre;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            conexion = DriverManager.getConnection(url, basedatosUsuario, basedatosClave);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.fatal(e.getMessage(), e);
            System.exit(1);
        }
        return conexion;
    }

    public static String getBasedatosIp() {
        return basedatosIp;
    }

    public static void setBasedatosIp(String basedatosIp) {
        Conexion.basedatosIp = basedatosIp;
    }

    public static String getBasedatosNombre() {
        return basedatosNombre;
    }

    public static void setBasedatosNombre(String basedatosNombre) {
        Conexion.basedatosNombre = basedatosNombre;
    }

    public static String getBasedatosUsuario() {
        return basedatosUsuario;
    }

    public static void setBasedatosUsuario(String basedatosUsuario) {
        Conexion.basedatosUsuario = basedatosUsuario;
    }

    public static String getBasedatosClave() {
        return basedatosClave;
    }

    public static void setBasedatosClave(String basedatosClave) {
        Conexion.basedatosClave = basedatosClave;
    }
    
}
