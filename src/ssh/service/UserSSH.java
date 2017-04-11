
package ssh.service;

import com.jcraft.jsch.UserInfo;

/**
 *
 * @author benjamin
 */
public class UserSSH implements UserInfo{

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
