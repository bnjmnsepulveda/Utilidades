package ssh.interfaces;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.IOException;
import ssh.exception.CommandException;

/**
 *
 * @author benjamin
 */
public interface ServiceSSH {

    public static final String EXEC_CHANNEL = "exec";
    public static final String SFTP_CHANNEL = "sftp";
    public boolean isHostOnline();
    public void connect() throws IOException, JSchException;
    public void disconnect();
    public String sendCommand(String command) throws IOException, JSchException, CommandException;
    public void changeDirectory(String path) throws IOException, JSchException,SftpException;
    public void uploadFile(String pathFile, String pathDestino) throws IOException, JSchException, SftpException;
    public File downloadFile(String pathFile,String pathDestino)throws IOException, JSchException, SftpException;
    
}
