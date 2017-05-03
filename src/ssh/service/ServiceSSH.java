/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssh.service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import ssh.exception.CommandException;
import static ssh.interfaces.ServiceSSH.SFTP_CHANNEL;

/**
 *
 * @author Roberto
 */
public class ServiceSSH {

    private final String usuario;
    private final String clave;
    private final String Servidor;
    private final int puerto;
    private Session sesion;

    public ServiceSSH(String usuario, String clave, String Servidor, int puerto) {
        this.usuario = usuario;
        this.clave = clave;
        this.Servidor = Servidor;
        this.puerto = puerto;
    }

    public void connect() throws IOException, JSchException {
        JSch jsch = new JSch();
        sesion = jsch.getSession(usuario, Servidor, puerto);
        UserInfo ui = new ServiceSSH.UserSSH(clave, null);
        sesion.setUserInfo(ui);
        sesion.setPassword(clave);
        sesion.connect();
    }

    public void disconnect() {
        sesion.disconnect();
    }

    public String sendCommand(String command) throws IOException, JSchException, CommandException {

        ChannelExec channelExec = (ChannelExec) sesion.openChannel("exec");
        InputStream in = channelExec.getInputStream();
        InputStream inError = channelExec.getErrStream();
        StringBuilder respuesta = new StringBuilder();

        StringBuilder respuestaError = new StringBuilder();
        String linea = null;

        channelExec.setCommand(command);
        channelExec.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        BufferedReader readerError = new BufferedReader(new InputStreamReader(inError));

        while ((linea = reader.readLine()) != null) {
            respuesta.append(linea);
            respuesta.append("\n");
        }

        while ((linea = readerError.readLine()) != null) {
            respuestaError.append(linea);
            respuestaError.append("\n");
        }

        if (!respuestaError.toString().isEmpty()) {
            channelExec.disconnect();
            throw new CommandException(respuestaError.toString());
        }

        channelExec.disconnect();
        return respuesta.toString();

    }

    public void changeDirectory(String path) throws IOException, JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) sesion.openChannel(SFTP_CHANNEL);
        sftp.connect();
        sftp.cd(path);
        //sftp.exit();
        sftp.disconnect();
    }

    public void uploadFile(String pathFile, String pathDestino) throws IOException,
            JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) sesion.openChannel(SFTP_CHANNEL);
        sftp.connect();
        sftp.put(pathFile, pathDestino);
        sftp.exit();
        sftp.disconnect();
    }

    public File downloadFile(String pathFile, String pathDestino)
            throws IOException, JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) sesion.openChannel(SFTP_CHANNEL);
        sftp.connect();
        InputStream in = sftp.get(pathFile);
        OutputStream out = new FileOutputStream(pathDestino);
        byte[] bytes = new byte[1024];
        int read;
        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        in.close();
        out.close();
        sftp.exit();
        sftp.disconnect();

        return new File(pathDestino);
    }

    public class UserSSH implements UserInfo {

        private final String password;
        private final String passPhrase;

        public UserSSH(String password, String passPhrase) {
            this.password = password;
            this.passPhrase = passPhrase;
        }

        @Override
        public String getPassphrase() {
            return passPhrase;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public boolean promptPassword(String string) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String string) {
            return true;
        }

        @Override
        public boolean promptYesNo(String string) {
            return true;
        }

        @Override
        public void showMessage(String string) {
            System.out.println("SessionSHH.showMessage()");
        }
    }

    public static void main(String[] args) {
        ServiceSSH service = new ServiceSSH("root", "adp2017", "192.168.100.44", 22);
        try {
            service.connect();
            System.out.println(service.sendCommand("ls -la"));
        } catch (IOException | JSchException | CommandException ex) {
            ex.printStackTrace(System.out);
        } finally {
            service.disconnect();
        }
    }

}
