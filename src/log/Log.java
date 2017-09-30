/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

/**
 *
 * @author Roberto
 */
public class Log {

    /**
     * Inicia logs Log4j2 con una diferente ruta a Classpath.
     * configuracion argumentos java -Dlog4j.configurationFile=path/to/log4j2.xml MyApp.
     */
    public void iniciarLogs() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        File file = new File("path/to/log4j2.xml");
        context.setConfigLocation(file.toURI());
    }
}
