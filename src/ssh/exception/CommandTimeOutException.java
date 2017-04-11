/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssh.exception;

/**
 *
 * @author benjamin
 */
public class CommandTimeOutException extends Exception{

    public CommandTimeOutException(String message) {
        super(message);
    }

    public CommandTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

}
