package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Crea un cliente socket generico que personaliza la peticion y la respuesta
 * mediante la implementacion de la interface SocketClientService.Listener.
 *
 * @author Roberto
 */
public class SocketClientService {

    private final int puerto;
    private final String host;
    private static Logger logger = LogManager.getLogger(SocketClientService.class);

    public SocketClientService(String host, int puerto) {
        this.puerto = puerto;
        this.host = host;
        System.out.println("Cliente > iniciando en el puerto " + puerto);
        System.out.println("Ejecutara peticiones a " + host);
        logger.info("Cliente > iniciando en el puerto " + puerto);
        logger.info("Ejecutara peticiones a " + host);
    }

    public synchronized String sendRequest(String request){
        Socket socket = null;
        String salida = null;
        String linea = null;
        BufferedReader input = null;
        PrintStream output = null;
        try {
            try {

                System.out.println("Cliente > Peticion a " + host + " " + request);
                logger.info("Cliente > Peticion a " + host + " " + request);

                socket = new Socket(host, puerto);
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));//Para leer lo que envie el servidor 
                output = new PrintStream(socket.getOutputStream()); //para imprimir datos del servidor           
                output.println(request);//manda peticion al servidor            
                salida = "";//captura respuesta e imprime
                if ((linea = input.readLine()) != null) {
                    salida = linea + "\n";
                    while ((linea = input.readLine()) != null) {
                        salida += linea + "\n";
                    }
                } else {
                    System.out.println("no hay salida");
                    logger.info("no hay salida");
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());               
            } finally {
                if (socket != null) {
                    socket.close();
                }
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            
        }
        return salida;
    }

}
