package sistema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Servicio de ejecucion de comandos y procesos.
 *
 * @author Roberto
 */
public class CommandService {

    private static Logger logger = LogManager.getLogger(CommandService.class);

    /**
     * Ejecuta un comando y devuelve la salida en String.
     *
     * @param cmd comando a ejecutar
     * @return
     */
    public String executeCommand(String cmd) {
        String linea = null;
        String salida = null;
        String lineaError = null;
        String salidaError = null;
        Process proceso = null;
        try {
            InputStreamReader entrada = null;
            InputStreamReader error = null;
            BufferedReader stdInput = null;
            BufferedReader stdError = null;
            try {
                String[] pipelineCmd = {
                    "/bin/bash",
                    "-c",
                    cmd
                };
                proceso = Runtime.getRuntime().exec(pipelineCmd);
                entrada = new InputStreamReader(proceso.getInputStream());
                error = new InputStreamReader(proceso.getErrorStream());
                stdInput = new BufferedReader(entrada);
                stdError = new BufferedReader(error);
                logger.info("Comando a ejecutar " + cmd);
                if ((linea = stdInput.readLine()) != null) {
                    salida = linea;
                    while ((linea = stdInput.readLine()) != null) {
                        salida += linea + "\n";
                    }
                    logger.info("Resultado del comando " + salida);
                } else {
                    logger.info("No se a producido ninguna salida");
                }
                if ((lineaError = stdError.readLine()) != null) {
                    salidaError = lineaError;
                    while ((lineaError = stdError.readLine()) != null) {
                        salidaError += lineaError + "\n";
                    }
                    logger.warn("Resultado del error en comando " + salidaError);
                }
            } catch (IOException ioe) {
                logger.error(ioe.getMessage(), ioe);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (entrada != null) {
                    entrada.close();
                }
                if (error != null) {
                    error.close();
                }
                if (stdInput != null) {
                    stdInput.close();
                }
                if (stdError != null) {
                    stdError.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return salida;
    }

}
