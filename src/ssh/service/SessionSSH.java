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
import ssh.interfaces.ServiceSSH;

/**
 *
 * @author benjamin
 */
public class SessionSSH implements ServiceSSH {

    private final String usuario;
    private final String clave;
    private final String Servidor;
    private final int puerto;
    private Session sesion;

    public SessionSSH(String usuario, String clave, String Servidor, int puerto) {
        this.usuario = usuario;
        this.clave = clave;
        this.Servidor = Servidor;
        this.puerto = puerto;
    }

    @Override
    public boolean isHostOnline() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connect() throws IOException, JSchException {
        JSch jsch = new JSch();
        sesion = jsch.getSession(usuario, Servidor, puerto);
        UserInfo ui = new UserSSH(clave, null);
        sesion.setUserInfo(ui);
        sesion.setPassword(clave);
        sesion.connect();
    }

    @Override
    public void disconnect() {
        sesion.disconnect();
    }

    @Override
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

    @Override
    public void changeDirectory(String path)  throws IOException, JSchException,SftpException{
        ChannelSftp sftp = (ChannelSftp) sesion.openChannel(SFTP_CHANNEL);
        sftp.connect();
        sftp.cd(path);
        //sftp.exit();
        sftp.disconnect();
    }
    
    @Override
    public void uploadFile(String pathFile, String pathDestino) throws IOException,
            JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) sesion.openChannel(SFTP_CHANNEL);
        sftp.connect();
        sftp.put(pathFile, pathDestino);
        sftp.exit();
        sftp.disconnect();
    }

    @Override
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

}
