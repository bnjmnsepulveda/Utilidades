package sistema;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;

/**
 *
 * @author Roberto
 */
public class NotificationService {

    private SystemTray tray;
    private TrayIcon trayIcon;

    public void init() throws AWTException {
        
        tray = SystemTray.getSystemTray();
        ImageIcon icon = new ImageIcon(NotificationService.class.getResource("/resources/img/icono.png"));
        trayIcon = new TrayIcon(icon.getImage(), "Notificacion llamada");
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip("Estado deidentificador");
        tray.add(trayIcon);
        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    System.out.println("clickeado");
                }
            }
//<editor-fold defaultstate="collapsed" desc="Eventos no implementados">

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
//</editor-fold>
        });
    }

    public void sendNotification(String titulo, String msg) {
        trayIcon.displayMessage(titulo, msg, TrayIcon.MessageType.INFO);
       
    }
}
