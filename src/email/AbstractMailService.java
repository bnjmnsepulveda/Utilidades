package email;

import java.util.Date;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.SearchTerm;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author Roberto
 */
public abstract class AbstractMailService {

    private String correo;
    private String clave;
    protected Session sesion;
    protected Store store;
    protected Folder inbox;
    protected Folder sent;
    protected Folder draft;
    private String storeName;
    private String inboxName;
    private String sentName;
    private String draftName;
    protected boolean connect;
    public static final String SEPARATOR_ID = ":::";
    private static final Logger logger = LogManager.getLogger(AbstractMailService.class);
    final Level ERROR_AUX = Level.forName("ERROR_AUX", 50); // Nivel personalizado de error para logs. Imprime la traza completa de error.

    public AbstractMailService(String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
        this.connect = false;
    }


    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Folder getInbox() {
        return inbox;
    }

    public void setInbox(Folder inbox) {
        this.inbox = inbox;
    }

    public Folder getSent() {
        return sent;
    }

    public void setSent(Folder sent) {
        this.sent = sent;
    }

    public Folder getDraft() {
        return draft;
    }

    public void setDraft(Folder draft) {
        this.draft = draft;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isConnect() {
        return connect;
    }
    
}
