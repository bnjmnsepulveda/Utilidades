
package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Crea un socket Server de tipo generico , que pueda personalizar el resultado
 * de la peticion hecha por un cliente, mediante la implementacion de la
 * interface SocketServerService.Listener.
 *
 * @author Roberto
 */
public final class SocketServerService implements Runnable {

    private final int puerto;
    private final int timeout = 10000;
    private volatile boolean run;
    private SocketServerService.Listener listener;
    private static Logger logger = LogManager.getLogger(SocketServerService.class);

    public SocketServerService(int puerto) {
        this.puerto = puerto;
        run = true;
    }

    public SocketServerService(int puerto, Listener listener) {
        this.puerto = puerto;
        this.listener = listener;
        run = true;
    }

    public void initServer() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(puerto);
            serverSocket.setSoTimeout(timeout);
            System.out.println("Servidor > iniciando en el puerto " + puerto);
            System.out.println("Servidor > En espera de peticion...");
            logger.info("Servidor > iniciando en el puerto " + puerto);
            logger.info("Servidor > En espera de peticion...");

            Socket clientSocket = null;
            BufferedReader input = null;
            PrintStream output = null;

            while (run) {
                try {
                    try {
                        clientSocket = serverSocket.accept();
                        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        output = new PrintStream(clientSocket.getOutputStream());
                        String request = input.readLine();
                        System.out.println("Cliente> petición [" + request + "]");
                        String strOutput = listener.responseRequest(request);
                        System.out.println("Servidor Principal> " + strOutput);
                        output.flush();
                        output.println(strOutput);
                    } catch (SocketTimeoutException e) {

                    }

                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    public void iniciar() {
        run = true;
    }

    public boolean estado() {
        return run;
    }

    public void detener() {
        run = false;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /**
     * Interface de comunicacion con objetos cliente.
     */
    public interface Listener {

        /**
         * la implementacion debe tratar la peticion del cliente socket y
         * devolver una respuesta en texto tratada por el servidor.
         *
         * @param request
         * @return
         */
        String responseRequest(String request);

    }

}
