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
        try {
            Process proceso = Runtime.getRuntime().exec(cmd);
            InputStreamReader entrada = new InputStreamReader(proceso.getInputStream());
            BufferedReader stdInput = new BufferedReader(entrada);
            if ((linea = stdInput.readLine()) != null) {
                System.out.println("Comando ejecutado Correctamente " + cmd);
                salida = linea;
                while ((linea = stdInput.readLine()) != null) {
                    salida += linea + "\n";
                }
            } else {
                System.out.println("No se a producido ninguna salida");
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return salida;
    }

    /**
     * Ejecuta un comando y devuelve la salida en String.
     *
     * @param cmd comando a ejecutar
     * @param pipeline parametro necesario para ejecutar comndos con llamado a
     * otros procesos como ls -la | grep /root
     * @return
     */
    public String executeCommand(String cmd, boolean pipeline) {
        String linea = null;
        String salida = null;
        Process proceso = null;
        try {
            if (pipeline) {
                String[] pipelineCmd = {
                    "/bin/sh",
                    "-c",
                    cmd
                };
                proceso = Runtime.getRuntime().exec(pipelineCmd);
            } else {
                proceso = Runtime.getRuntime().exec(cmd);
            }
            InputStreamReader entrada = new InputStreamReader(proceso.getInputStream());
            BufferedReader stdInput = new BufferedReader(entrada);
            if ((linea = stdInput.readLine()) != null) {
                System.out.println("Comando ejecutado Correctamente " + cmd);
                salida = linea;
                while ((linea = stdInput.readLine()) != null) {
                    salida += linea + "\n";
                }
            } else {
                System.out.println("No se a producido ninguna salida");
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return salida;
    }
}
