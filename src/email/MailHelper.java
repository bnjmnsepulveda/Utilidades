package email;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Roberto
 */
public class MailHelper {

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Logger logger = LogManager.getLogger(MailHelper.class);

    public static String ccAddress(Message msg) throws MessagingException {
        Address[] direcciones = msg.getRecipients(Message.RecipientType.CC) ;
        String r = "";
        if (direcciones != null) {
            for (Address d : direcciones) {
                InternetAddress id = (InternetAddress) d;
                r += id.getAddress() + ";";
            }
        }
        return r;
    }


    public static String messageId(Message msg) {
        String id = "";
        try {
            id = msg.getHeader("Message-Id")[0];
        } catch (MessagingException ex) {
            logger.error(ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return id;
    }

    /**
     * limpia el MessageId de un email, de caracteres invalidos para la
     * generacion de id tag html.
     *
     * @param id
     * @return
     */
    public static String cleanIdMessage(String id) {
        return id.replaceAll("[^A-Z,a-z,0-9]", "");
    }

    public static boolean isNotSpam(List<String> spam, Message mm) {
        InternetAddress adr;
        try {
            adr = (InternetAddress) mm.getFrom()[0];
            String strAdr = adr.getAddress().trim();
            boolean listaBlanca = true;
            for (String s : spam) {
                s = s.trim();
                if (s.startsWith("*")) {
                    listaBlanca = !s.split("@")[1].equals(strAdr.split("@")[1]);
                    if (!listaBlanca) {
                        break;
                    }
                } else {
                    listaBlanca = !s.trim().toLowerCase().equals(strAdr.trim().toLowerCase());
                    if (!listaBlanca) {
                        break;
                    }
                }
            }
            return listaBlanca;

        } catch (MessagingException e) {
            logger.error("" + e.getMessage());
            return false;
        }
    }

    /**
     * quita el prefijo "RE :" del asunto para poder identificar el correo
     * mediante su asunto original.
     *
     * @param sbj
     * @return
     */
    public static String cleanAsunto(String sbj) {
        sbj = sbj.replace("RE:", "");
        sbj = sbj.replace("Re:", "");
        sbj = sbj.replace("RE :", "");
        sbj = sbj.replace("Re :", "");
        return sbj.trim();
    }

    public static boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static synchronized String fromAddress(Message msg) {
        try {
            return ((InternetAddress) msg.getFrom()[0]).getAddress();
        } catch (MessagingException ex) {
            return "Sin Direccion";
        }
    }

    public static String toAddress(Message msg) {
        try {
            return ((InternetAddress) msg.getRecipients(Message.RecipientType.TO)[0]).getAddress();
        } catch (Exception ex) {
            return "Sin Direccion";
        }
    }

    public static String parseAddress(Address msg) {
        return ((InternetAddress) msg).getAddress();
    }

    public static String getTextFromContent(Message m) {
        try {
            return getPartText(m.getContent());
        } catch (IOException | MessagingException e) {
            logger.error("Error obteniendo text/plain de Message" + e.getMessage());
            return null;
        }
    }

    public static String getPartText(Object content) {
        String text = "";
        MimeMultipart multipart = null;
        if (content instanceof MimeMultipart) {
            multipart = (MimeMultipart) content;
        } else {
            return content.toString();
        }
        try {
            Object obj;
            int multiCount = multipart.getCount();
            for (int i = 0; i < multiCount; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                obj = bodyPart.getContent();
                if (obj instanceof String) {
                    if (bodyPart.getContentType().contains("text/plain")) {
                        text += bodyPart.getContent().toString();
                    }

                } else if (obj instanceof Multipart) {
                    text += getPartText((MimeMultipart) obj);
                }
            }
        } catch (IOException | MessagingException e) {
            logger.error("" + e.getMessage());
        }
        return text;
    }

    public static String getPartHtml(MimeMultipart multipart) {//Object content
        String html = "";
        /*
        MimeMultipart multipart = null;
        if (content instanceof MimeMultipart) {
            multipart = (MimeMultipart) content;
        } else {
            return "";
        }
         */
        try {
            Object obj;
            int multiCount = multipart.getCount();
            for (int i = 0; i < multiCount; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                obj = bodyPart.getContent();
                if (obj instanceof String) {
                    if (bodyPart.getContentType().contains("text/html")) {
                        html += bodyPart.getContent().toString();
                    }
                } else if (obj instanceof Multipart) {
                    html += getPartHtml((MimeMultipart) obj);
                }
            }
        } catch (IOException | MessagingException e) {
            logger.error("" + e.getMessage());
            
        }
        return html;
    }

    public static String[] getNombreAdjuntos(Object content) {
        String t = getNombreAdjuntosMain(content);
        System.out.println("adjuntos string :" + t + " tamaño adj: " + t.split(";").length);
        if (t.isEmpty()) {
            return null;
        }
        return t.split(";");
    }

    private static String getNombreAdjuntosMain(Object content) {
        String adjuntos = "";
        MimeMultipart multipart = null;
        if (content instanceof MimeMultipart) {
            multipart = (MimeMultipart) content;
        } else {
            return "";
        }
        try {
            Object obj;
            int multiCount = multipart.getCount();
            for (int i = 0; i < multiCount; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                obj = bodyPart.getContent();
                if (obj instanceof InputStream) {
                    String disposition = bodyPart.getDisposition();
                    if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                        adjuntos += bodyPart.getFileName() + ";";
                    }

                } else if (obj instanceof Multipart) {
                    String aux = getNombreAdjuntosMain((MimeMultipart) obj);
                    System.out.println("ADJUNTO recorrido :" + aux);
                    if (!aux.isEmpty()) {
                        adjuntos += getNombreAdjuntosMain((MimeMultipart) obj);
                    }
                    // adjuntos += getNombreAdjuntosMain((MimeMultipart) obj) + ";";

                } else if (obj instanceof String) {
                    String fileName = bodyPart.getFileName();
                    if (fileName != null) {
                        System.out.println("archivo de texto como adjunto ");
                        adjuntos += fileName + ";";
                    }
                }

            }
        } catch (IOException | MessagingException e) {
            logger.error("" + e.getMessage());
            
        }
        return adjuntos;
    }

    public static List<MimeBodyPart> procesarAdjuntos(MimeMultipart multipart) {
        List<MimeBodyPart> adjuntos = new ArrayList();
        try {
            Object obj;
            int multiCount = multipart.getCount();
            for (int i = 0; i < multiCount; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                obj = bodyPart.getContent();
                if (obj instanceof Multipart) {
                    adjuntos.addAll(procesarAdjuntos((MimeMultipart) obj));
                } else if (obj instanceof InputStream) {
                    // System.out.println("img : " + bodyPart.getContentType());
                    String disposition = bodyPart.getDisposition();
                    if (disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                        System.out.println("la imagen es un adjunto :" + bodyPart.getFileName());
                        adjuntos.add(bodyPart);
                    }

                }
            }
        } catch (IOException | MessagingException e) {
            logger.error("" + e.getMessage());
            
        }
        return adjuntos;
    }

    public static List<MimeBodyPart> procesarInline(MimeMultipart multipart) {
        System.out.println("procesando inlines....");
        List<MimeBodyPart> adjuntos = new ArrayList();
        try {
            Object obj;
            int multiCount = multipart.getCount();
            for (int i = 0; i < multiCount; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                obj = bodyPart.getContent();
                if (obj instanceof Multipart) {
                    adjuntos.addAll(procesarInline((MimeMultipart) obj));
                } else if (obj instanceof InputStream) {
//                    System.out.println("la imagen es  :" + bodyPart.getFileName());
                    String disposition = bodyPart.getDisposition();
//                    System.out.println("disposition es : " + disposition + " comparando con :"+ Part.INLINE);
                    if (disposition != null) {
                        if (!disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                            continue;
                        }
                    }
                    System.out.println("la imagen es un inline :" + bodyPart.getFileName());
                    adjuntos.add(bodyPart);

                }
            }
        } catch (IOException | MessagingException e) {
            logger.error("Error al procesar imagen inline: " + e.getMessage());
        }
        return adjuntos;
    }

    public static String formatMailCC(InternetAddress[] direcciones) {
        String r = "";
        if (direcciones != null) {
            for (InternetAddress d : direcciones) {
                r += d.getAddress() + ";";
            }
        }
        return r;
    }

    public static String mailCCfromMsg(Message msg) {
        try {
            return formatMailCC((InternetAddress[]) msg.getRecipients(Message.RecipientType.CC));
        } catch (MessagingException e) {
            logger.error("" + e.getMessage());
            
            return null;
        }
    }

    /**
     * Convierte una cadena de direcciones de email con el formato <email>;<email> a
     * InternetAddress[].
     * @param direcciones
     * @return 
     */
    public static InternetAddress[] getDirecciones(String direcciones) {
        String[] spl = direcciones.split(";");
        InternetAddress[] adrrs = new InternetAddress[spl.length];
        try {
            for (int x = 0; x < spl.length; x++) {
                adrrs[x] = new InternetAddress(spl[x]);
            }
        } catch (AddressException e) {
            logger.error("" + e.getMessage());
            
        }
        return adrrs;
    }

    public static String quitaEspacios(String texto) {
        java.util.StringTokenizer tokens = new java.util.StringTokenizer(texto);
        StringBuilder buff = new StringBuilder();
        while (tokens.hasMoreTokens()) {
            buff.append(" ").append(tokens.nextToken());
        }
        return buff.toString();
    }

}
