package sistema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Servicio de ejecucion de comandos y procesos.
 *
 * @author Roberto
 */
public class CommandService {

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
            String[] pipelineCmd = {
                "/bin/sh",
                "-c",
                cmd
            };
            proceso = Runtime.getRuntime().exec(pipelineCmd);
            InputStreamReader entrada = new InputStreamReader(proceso.getInputStream());
            InputStreamReader error = new InputStreamReader(proceso.getErrorStream());
            BufferedReader stdInput = new BufferedReader(entrada);
            BufferedReader stdError = new BufferedReader(error);
            System.out.println("Comando a ejecutar " + cmd);
            if ((linea = stdInput.readLine()) != null) {
                salida = linea;
                while ((linea = stdInput.readLine()) != null) {
                    salida += linea + "\n";
                }
                System.out.println("Resultado del comando " + salida);
            } else {
                System.out.println("No se a producido ninguna salida");
            }
            if ((lineaError = stdError.readLine()) != null) {
                salidaError = lineaError;
                while ((lineaError = stdError.readLine()) != null) {
                    salidaError += lineaError + "\n";
                }
                System.out.println(salidaError);
            }
        } catch (IOException ioe) {
        } catch (Exception e) {
        }
        return salida;
    }

}
