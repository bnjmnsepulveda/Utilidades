package configuracion;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Encargada de cargar parametros iniciales desde un archivo .properties,
 * dejando esta clase disponible para toda la aplicacion con los parametros de
 * funcionamiento.
 *
 * @author Benjamin
 */
public class Configuracion {

    //parametros personalizables
    public static String ipServidorPrincipal;
    public static String ipServidorContingencia;
    public static String ipCallManager;

    /**
     * Carga los parametros mediante un archivo .properties.
     *
     * @param conf
     */
    public static final void loadParametros(String conf) {
        FileInputStream inputStream = null;
        Properties propiedades = null;
        try {
            inputStream = new FileInputStream(conf);
            propiedades = new Properties();
            propiedades.load(inputStream);
            ipServidorPrincipal = propiedades.getProperty("principal.ip");
            ipServidorContingencia = propiedades.getProperty("contingencia.ip");
            ipCallManager = propiedades.getProperty("callmanager.ip");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
}
