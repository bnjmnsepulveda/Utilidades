/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author Roberto
 */
public class EmailService {

    private Session sesion;

    private String correo;
    private String usuarioCorreo;
    private String claveCorreo;
    private String servidorCorreo;
    private String[] destinatarios;
    private final String firma = "<br><br><table style=\"color:#0B2161\" ><tr><th style=\"text-align: left\">Soporte Adportas<br>Adportas Media Group</th><th style=\"width: 15\"></th></tr></table>"
            + " <font size=1 color=\"Red\">___________________________________________ </font><br>"
            + "<font size=1 style=\"font-family:arial;\"> Carmen Fari�a #6669 <br>"
            + "7640557 Vitacura, Santiago de Chile <br>"
            + "Phone:</font><font size=1 color=\"Blue\"> (562) 24134531</font> ";

    public EmailService() {
        configure();
    }

    private void configure() {

    }

    /**
     * Envia un email en un nuevo hilo dse java.
     *
     * @param sbj
     * @param contenido
     */
    public void sendEmailThread(final String sbj, final String contenido) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String msg = "<p style=\"font-family:Calibri;color:#1F497D;\">"
                            + contenido
                            + "</p>"
                            + firma;
                    Properties properties = new Properties();
                    properties.put("mail.host", servidorCorreo);
                    properties.put("mail.store.protocol", "imaps");
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.port", "25");
                    properties.put("mail.smtp.host", servidorCorreo);
                    properties.put("mail.smtp.protocol", "smtp");

                    sesion = Session.getInstance(properties, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(usuarioCorreo, claveCorreo);
                        }
                    });
                    Message message = new MimeMessage(sesion);
                    message.setFrom(new InternetAddress(correo));
                    message.setSubject(sbj);
                    InternetAddress[] addrs = new InternetAddress[destinatarios.length];
                    for (int x = 0; x < destinatarios.length; x++) {
                        addrs[x] = new InternetAddress(destinatarios[x]);
                    }
                    message.setRecipients(Message.RecipientType.TO, addrs);
                    DataHandler dh = new DataHandler(msg, "text/html;charset=UTF-8");
                    message.setDataHandler(dh);
                    Transport.send(message);
                } catch (MessagingException ex) {
                    ex.printStackTrace(System.out);
                } catch (Exception ex) {
                    ex.printStackTrace(System.out);
                }
            }
        });
        thread.start();
    }

    /**
     * Envia un email con confirmacion de enviado.
     *
     * @param sbj
     * @param contenido
     * @return false si no se envio el email.
     */
    public boolean sendEmail(final String sbj, final String contenido) {
        boolean enviado = true;
        try {
            String msg = "<p style=\"font-family:Calibri;color:#1F497D;\">"
                    + contenido
                    + "</p>"
                    + firma;
            Properties properties = new Properties();
            properties.put("mail.host", servidorCorreo);
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "25");
            properties.put("mail.smtp.host", servidorCorreo);
            properties.put("mail.smtp.protocol", "smtp");

            sesion = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuarioCorreo, claveCorreo);
                }
            });
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(correo));
            message.setSubject(sbj);
            InternetAddress[] addrs = new InternetAddress[destinatarios.length];
            for (int x = 0; x < destinatarios.length; x++) {
                addrs[x] = new InternetAddress(destinatarios[x]);
            }
            message.setRecipients(Message.RecipientType.TO, addrs);
            DataHandler dh = new DataHandler(msg, "text/html");
            message.setDataHandler(dh);
            Transport.send(message);
        } catch (MessagingException ex) {
            enviado = false;
            ex.printStackTrace(System.out);
        } catch (Exception ex) {
            enviado = false;
            ex.printStackTrace(System.out);
        }
        return enviado;
    }

    public boolean sendEmail(final String to, final String sbj, final String contenido) {
        boolean enviado = true;
        try {
            String msg = "<p style=\"font-family:Calibri;color:#1F497D;\">"
                    + contenido
                    + "</p>"
                    + firma;
            Properties properties = new Properties();
            properties.put("mail.host", servidorCorreo);
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "25");
            properties.put("mail.smtp.host", servidorCorreo);
            properties.put("mail.smtp.protocol", "smtp");

            sesion = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuarioCorreo, claveCorreo);
                }
            });
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(correo));
            message.setSubject(sbj);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            DataHandler dh = new DataHandler(msg, "text/html");
            message.setDataHandler(dh);
            Transport.send(message);
        } catch (MessagingException ex) {
            enviado = false;
            ex.printStackTrace(System.out);
        } catch (Exception ex) {
            enviado = false;
            ex.printStackTrace(System.out);
        }
        return enviado;
    }

    public void sendEmailTest() {
        try {
            String msg = "<p style=\"font-family:Calibri;color:#1F497D;\">"
                    + "Este es un email de pruebas enviado desde DemonioRackallONL"
                    + "</p>"
                    + firma;
            Properties properties = new Properties();
            properties.put("mail.host", servidorCorreo);
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "25");
            properties.put("mail.smtp.host", servidorCorreo);
            properties.put("mail.smtp.protocol", "smtp");

            sesion = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuarioCorreo, claveCorreo);
                }
            });
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(correo));
            message.setSubject("Envio de Email de pruebas en DemonioReckallOLN");
            InternetAddress[] addrs = new InternetAddress[destinatarios.length];
            for (int x = 0; x < destinatarios.length; x++) {
                addrs[x] = new InternetAddress(destinatarios[x]);
            }
            message.setRecipients(Message.RecipientType.TO, addrs);
            DataHandler dh = new DataHandler(msg, "text/html");
            message.setDataHandler(dh);
            Transport.send(message);
        } catch (MessagingException ex) {
            ex.printStackTrace(System.out);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

}
