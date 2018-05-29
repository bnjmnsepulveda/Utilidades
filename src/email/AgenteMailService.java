package email;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Roberto
 */
public class AgenteMailService extends AbstractMailService {

    private static final Logger logger = LogManager.getLogger(AgenteMailService.class);

    public AgenteMailService(String correo, String clave) {
        super(correo, clave);
    }

    public String responseMessage(
            String messageId,
            String to,
            String from,
            String cc,
            String sbj,
            String nameAdj,
            String content,
            int folder,
            String pathUpload) {

        String txtRs = "";
        Message msg = null;
        Message forward = null;
        String originalHtml = null;
        String originalText = null;
        MimeMultipart contenidoCorreo = null;
        Multipart mixed = null;
        Multipart alternative = null;
        MimeBodyPart bodyMixed = null;
        MimeBodyPart bodyText = null;
        MimeBodyPart bodyHtml = null;
        List<MimeBodyPart> adjuntos = null;
        String[] adjs = null;
        MimeBodyPart adjunto = null;
        Object contentEmail = null;

        try {
            logger.info("iniciando responder mail...");
            
            if (msg != null) {

                if (sbj.contains("RE:") || sbj.contains("RE :")) {
                    forward = new MimeMessage((MimeMessage) msg);
                } else {
                    forward = msg.reply(false);
                }
                contentEmail = msg.getContent();
                if (contentEmail != null && contentEmail instanceof MimeMultipart) {
                    contenidoCorreo = (MimeMultipart) contentEmail;
                    originalHtml = "";
                    originalHtml = "";
                    originalText = MailHelper.getPartText(contenidoCorreo);
                    mixed = new MimeMultipart("mixed");
                    alternative = new MimeMultipart("alternative");
                    bodyMixed = new MimeBodyPart();
                    bodyText = new MimeBodyPart();
                    bodyHtml = new MimeBodyPart();
                    adjuntos = new ArrayList();
                    String contenidoHtml = "";
                    String contenidoText = "";
                    bodyText.setContent(contenidoText, "text/plain;charset=UTF-8");
                    bodyHtml.setContent(contenidoHtml, "text/html;charset=UTF-8");
                    alternative.addBodyPart(bodyText);
                    alternative.addBodyPart(bodyHtml);
                    bodyMixed.setContent(alternative);
                    mixed.addBodyPart(bodyMixed);
                    adjuntos = MailHelper.procesarAdjuntos(contenidoCorreo);
                    for (MimeBodyPart b : adjuntos) {
                        mixed.addBodyPart(b);
                    }
                    if (!nameAdj.trim().isEmpty()) {
                        adjs = nameAdj.split(";");
                        for (String adj : adjs) {
                            adjunto = new MimeBodyPart();
                            String p = pathUpload.trim() + adj.trim();
                            File f = new File(p);
                            adjunto.setDataHandler(new DataHandler(new FileDataSource(f)));
                            adjunto.setFileName(adj);
                            // adjunto.attachFile(f);
                            mixed.addBodyPart(adjunto);

                        }
                    }
                } else if (msg.getContent() instanceof String) {

                    originalText = MailHelper.getPartText(msg.getContent());
                    forward.setSentDate(new Date());
                    mixed = new MimeMultipart("mixed");
                    alternative = new MimeMultipart("alternative");
                    bodyMixed = new MimeBodyPart();
                    bodyText = new MimeBodyPart();
                    bodyHtml = new MimeBodyPart();
                    String contenidoHtml = content + "";
                    String contenidoText = content + "";
                    bodyText.setContent(contenidoText, "text/plain;charset=UTF-8");
                    bodyHtml.setContent(contenidoHtml, "text/html;charset=UTF-8");
                    alternative.addBodyPart(bodyText);
                    alternative.addBodyPart(bodyHtml);
                    bodyMixed.setContent(alternative);
                    mixed.addBodyPart(bodyMixed);
                    if (nameAdj.contains("@")) {
                        adjs = nameAdj.split(";");
                        for (String adj : adjs) {
                            //System.out.println("ADJUNTOA:" + adj);
                            adjunto = new MimeBodyPart();
                            String p = pathUpload.trim() + adj.trim();
                            File f = new File(p);
                            System.out.println("path:" + p);
                            System.out.println("file:" + f.getAbsolutePath());
                            //adjunto.setDataHandler(new DataHandler(new FileDataSource(f)));
                            //adjunto.setFileName(adj);
                            adjunto.attachFile(f);
                            mixed.addBodyPart(adjunto);
                        }
                    }
                }

                if (mixed != null && forward != null) {
                    String poolEmail = "";
                    forward.setFrom(new InternetAddress(poolEmail));
                    forward.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                    if (!cc.trim().isEmpty()) {
                        if (cc.contains(";")) {
                            forward.setRecipients(Message.RecipientType.CC, MailHelper.getDirecciones(cc));
                        } else {
                            forward.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                        }
                    }
                    sbj = sbj != null ? sbj : "Sin Asunto";
                    if (!sbj.contains(AbstractMailService.SEPARATOR_ID)) {
                        forward.setFrom(new InternetAddress(""));
                    } else {
                        forward.setFrom(new InternetAddress(poolEmail));
                    }
                    forward.setSentDate(new Date());
                    forward.setContent(mixed);
                    Transport.send(forward);
                    logger.info("Correo respondido " + ((MimeMessage) forward).getMessageID());
                    forward.setFlag(Flags.Flag.SEEN, true);
                    //  forward.setFrom(new InternetAddress(origen));
                   
                } else {
                    
                }
            }
        } catch (MessagingException | IOException e) {
            logger.error(e.getMessage());
            logger.log(ERROR_AUX, e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.log(ERROR_AUX, e.getMessage(), e);
        }
        return txtRs;
    }

    /**
     * Envia un Email generico.
     *
     * @param msg
     * @param asunto
     * @param from
     * @param to
     * @param cc
     * @throws javax.mail.MessagingException
     */
    public void sendMessage(String msg, String asunto, String from, String to, String cc) throws MessagingException {
        logger.info("iniciando envio de mensaje...");
        MimeMessage mm = null;
        String html = null;
        String text = null;
        Multipart mixed = null;
        Multipart alternative = null;
        MimeBodyPart bodyMixed = null;
        MimeBodyPart bodyText = null;
        MimeBodyPart bodyHtml = null;
        mm = new MimeMessage(sesion);
        mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mm.setFrom(new InternetAddress(from));
        if (cc != null && !cc.trim().isEmpty()) {
            if (cc.contains(";")) {
                mm.setRecipients(Message.RecipientType.CC, MailHelper.getDirecciones(cc));
            } else {
                mm.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
        }
        mm.setSentDate(new Date());
        if (!asunto.trim().isEmpty()) {
            mm.setSubject(asunto);
        } else {
            mm.setSubject("Sin Asunto");
        }

        html = "";
        text = "";

        mixed = new MimeMultipart("mixed");
        alternative = new MimeMultipart("alternative");

        bodyMixed = new MimeBodyPart();
        bodyText = new MimeBodyPart();
        bodyHtml = new MimeBodyPart();
        //firma html
        html = msg;
        logger.debug("contenido email html:" + msg);
        //firma text
        text = "";

        bodyText.setContent(text, "text/plain;charset=UTF-8");
        bodyHtml.setContent(html, "text/html;charset=UTF-8");
        alternative.addBodyPart(bodyText);
        alternative.addBodyPart(bodyHtml);
        bodyMixed.setContent(alternative);
        mixed.addBodyPart(bodyMixed);
        mm.setContent(mixed);

        Transport.send(mm);
        mm.setFlag(Flags.Flag.SEEN, true);
        logger.info("mensaje enviado, asunto:" + asunto + " remitente:" + from + " para:" + to);

    }

    /**
     * Envia un Email generico con las rutas de los archivos a adjuntar.
     *
     * @param msg
     * @param asunto
     * @param from
     * @param to
     * @param cc
     * @param path
     * @param nameAdj
     */
    public void sendMessage(String msg, String asunto, String from, String to, String cc, String path, String nameAdj) {

        MimeMessage mm = null;
        String html = null;
        String text = null;
        String[] adjs = null;
        Multipart mixed = null;
        Multipart alternative = null;
        MimeBodyPart bodyMixed = null;
        MimeBodyPart bodyText = null;
        MimeBodyPart bodyHtml = null;
        MimeBodyPart adjunto = null;

        try {
            logger.info("iniciando envio de mensaje...");
            mm = new MimeMessage(sesion);
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mm.setFrom(new InternetAddress(from));
            if (cc != null && !cc.trim().isEmpty()) {
                if (cc.contains(";")) {
                    mm.setRecipients(Message.RecipientType.CC, MailHelper.getDirecciones(cc));
                } else {
                    mm.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
                }
            }
            mm.setSentDate(new Date());
            if (!asunto.trim().isEmpty()) {
                mm.setSubject(asunto);
            } else {
                mm.setSubject("Sin Asunto");
            }

            html = "";
            text = "";

            mixed = new MimeMultipart("mixed");
            alternative = new MimeMultipart("alternative");

            bodyMixed = new MimeBodyPart();
            bodyText = new MimeBodyPart();
            bodyHtml = new MimeBodyPart();
            //firma html
            html = msg;
            logger.debug("contenido email html:" + msg);
            //firma text
            text = "";

            bodyText.setContent(text, "text/plain;charset=UTF-8");
            bodyHtml.setContent(html, "text/html;charset=UTF-8");
            alternative.addBodyPart(bodyText);
            alternative.addBodyPart(bodyHtml);
            bodyMixed.setContent(alternative);
            mixed.addBodyPart(bodyMixed);

            adjs = nameAdj.split(";");
            for (String adj : adjs) {
                adjunto = new MimeBodyPart();
                String p = path.trim() + adj.trim();
                File f = new File(p);
                //adjunto.setDataHandler(new DataHandler(new FileDataSource(f)));
                //adjunto.setFileName(adj);

                adjunto.attachFile(f);
                mixed.addBodyPart(adjunto);

            }

            mm.setContent(mixed);

            Transport.send(mm);
            mm.setFlag(Flags.Flag.SEEN, true);
            logger.info("mensaje enviado, asunto:" + asunto + " remitente:" + from + " para:" + to);
        } catch (IOException | MessagingException e) {
            logger.error("Error enviando, asunto:" + asunto + " remitente:" + from + " para:" + to + ": " + e.getMessage());
            logger.log(ERROR_AUX, "Error enviando, asunto:" + asunto + " remitente:" + from + " para:" + to, e);
        }
    }

    public void sendMessageDraft(String msg, String asunto, String from, String to, String cc) throws MessagingException {
        MimeMessage mm = null;
        String html = null;
        String text = null;
        Multipart mixed = null;
        Multipart alternative = null;
        MimeBodyPart bodyMixed = null;
        MimeBodyPart bodyText = null;
        MimeBodyPart bodyHtml = null;
        mm = new MimeMessage(sesion);
        if (!to.trim().isEmpty() && MailHelper.validarEmail(to)) {
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        }
        if (!from.trim().isEmpty() && MailHelper.validarEmail(from)) {
            mm.setFrom(new InternetAddress(from));
        }
        if (cc != null && !cc.trim().isEmpty()) {
            if (cc.contains(";")) {
                mm.setRecipients(Message.RecipientType.CC, MailHelper.getDirecciones(cc));
            } else {
                mm.setRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
        }
        mm.setSentDate(new Date());
        if (asunto != null && !asunto.trim().isEmpty()) {
            mm.setSubject(asunto);
        } else {
            mm.setSubject("Sin Asunto");
        }
        html = "";
        text = "";
        mixed = new MimeMultipart("mixed");
        alternative = new MimeMultipart("alternative");
        bodyMixed = new MimeBodyPart();
        bodyText = new MimeBodyPart();
        bodyHtml = new MimeBodyPart();
        //firma html
        html = msg;
        //firma text
        text = msg;
        bodyText.setContent(text, "text/plain;charset=UTF-8");
        bodyHtml.setContent(html, "text/html;charset=UTF-8");
        alternative.addBodyPart(bodyText);
        alternative.addBodyPart(bodyHtml);
        bodyMixed.setContent(alternative);
        mixed.addBodyPart(bodyMixed);
        mm.setContent(mixed);
        mm.setFlag(Flags.Flag.SEEN, true);
    }


}
