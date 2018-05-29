package email;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import model.domain.ContenidoMail;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Trata el contenido de un email, guardando imagenes y archivos adjuntas ,
 * imagenes embebidas y la informacion basica del email.
 *
 * @author Roberto
 */
public class ContentService {

    private ContenidoMail contenido;
    private String html;
    private String txt;
    private String adjNames;
    private String inlinesNames;
    private String prefijoImgInline;
    private List<MimeBodyPart> adjuntos;
    private List<MimeBodyPart> inlines;
    private List<String> nameAttachments;
    private List<String> urlInlines;

    private String pathUpload;
    private static final Logger logger = LogManager.getLogger(ContentService.class);
    
    public ContenidoMail processContent(MimeMessage msg) {
        contenido = null;
        return contenido;
    }

    public ContenidoMail processContent(MimeMessage mm, String pathUpload, String pathDownload) throws IOException, MessagingException {
        logger.info("procesando contenido...");
        prefijoImgInline = System.currentTimeMillis() + "";
        contenido = new ContenidoMail();
        nameAttachments = new ArrayList();
        inlines = new ArrayList();
        adjNames = "";
        inlinesNames = "";
        txt = "";
        html = "";
        this.pathUpload = pathUpload;
        if (mm.getContent() instanceof String) {
            txt = mm.getContent().toString();
            html = mm.getContent().toString();
        } else {
            procesarAdjuntos((MimeMultipart) mm.getContent());
        }
        contenido.setMessageID(mm.getMessageID());
        contenido.setFrom(MailHelper.fromAddress(mm));
        contenido.setTo(MailHelper.toAddress(mm));
        contenido.setCc(MailHelper.mailCCfromMsg(mm));
        contenido.setSbj(mm.getSubject());
        contenido.setSendDateString("");
        contenido.setSendDate(mm.getSentDate());
        logger.info("leyendo archivos inlines");

        for (MimeBodyPart x : inlines) {
            logger.info("archivo: " + prefijoImgInline + x.getFileName() + " id: " + x.getContentID());
            String idc = x.getContentID();
            if (idc != null) {
                idc = idc.replace("<", "")
                        .replace(">", "")
                        .trim();
                html = html.replace("cid:" + idc, pathDownload + prefijoImgInline + x.getFileName());
            }
        }
        contenido.setNameAttachments(nameAttachments);
        //Console.println(Console.BLUE, html);
        html = "";
        linkearImg();
        contenido.setHtml(html);
        contenido.setTexto(txt);
        contenido.setUrlinlines(urlInlines);//si linkearImg() no se ejecuta urlInlines estara en null;
      //  Console.println(Console.GREEN, html);
        //showContent();
       // showImgInfo();
        return contenido;
    }

    public void procesarAdjuntos(MimeMultipart multipart) {
        logger.info("procesando ADJUNTOS....");

        try {
            Object obj;
            int multiCount = multipart.getCount();
            for (int i = 0; i < multiCount; i++) {
                MimeBodyPart bodyPart = (MimeBodyPart) multipart.getBodyPart(i);
                obj = bodyPart.getContent();
                if (obj instanceof Multipart) {
                    procesarAdjuntos((MimeMultipart) obj);
                } else if (obj instanceof InputStream) {
                    String disposition = bodyPart.getDisposition();
                    boolean isAtt = false;
                    if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
                        logger.info("El archivo es un adjunto :" + bodyPart.getFileName());
                        adjNames += bodyPart.getFileName();
                        nameAttachments.add(bodyPart.getFileName());
                        isAtt = true;
                    }

                    if (!isAtt) {
                        inlinesNames += prefijoImgInline + bodyPart.getFileName() + ";";
                        String ruta = pathUpload + prefijoImgInline + bodyPart.getFileName();
                        logger.info("ruta a guardar" + ruta);
                        bodyPart.saveFile(ruta);

                        inlines.add(bodyPart);

                        InputStream imagen;
                        try (FileOutputStream fichero = new FileOutputStream(ruta)) {
                            logger.info("creando archivo");
                            imagen = bodyPart.getInputStream();
                            byte[] bytes = new byte[1000];
                            int leidos = 0;
                            while ((leidos = imagen.read(bytes)) > 0) {
                                fichero.write(bytes, 0, leidos);
                            }
                        }
                        imagen.close();

                    }
                } else if (obj instanceof String) {
                    String fileName = bodyPart.getFileName();
                    if (fileName != null) {
                        logger.info("archivo de texto como adjunto ");
                        adjNames += bodyPart.getFileName() + ";";
                        nameAttachments.add(bodyPart.getFileName());
                        //   adjuntos.add(bodyPart);
                        // bodyPart.saveFile(rutaGuardar + System.currentTimeMillis()+ "" +bodyPart.getFileName());
                    } else if (bodyPart.getContentType().contains("text/html")) {
                        html += bodyPart.getContent().toString();
                    } else if (bodyPart.getContentType().contains("text/plain")) {
                        txt += bodyPart.getContent().toString();
                    } else {
                        
                    }
                   // Console.println(Console.RED, bodyPart.getContent().toString());

                }
            }
        } catch (IOException | MessagingException e) {
            logger.error("Error al procesar contenido de Email: " + e.getMessage());
        }
    }

    public void showContent() {
        System.out.println("----------------------------------------------------");
        System.out.println("NOMBRE de adjuntos " + adjNames);
        System.out.println("NOMBRE de inlines " + inlinesNames);
        System.out.println("CONTENDIO txt " + txt);
        System.out.println("CONTENDIO html " + html);
        System.out.println("====================================================");
        System.out.println("ATTACHMET NAMES ");
        for (String a : nameAttachments) {
            System.out.println(a);
        }
        System.out.println("====================================================");
        for (MimeBodyPart a : adjuntos) {
            try {
                System.out.println(" adj " + a.getFileName());
            } catch (MessagingException e) {
                logger.error("" + e.getMessage());
            }
        }
        System.out.println("====================================================");
        for (MimeBodyPart a : inlines) {
            try {
                System.out.println(" inline " + a.getFileName());
            } catch (MessagingException e) {
                logger.error("" + e.getMessage());
            }
        }
        System.out.println("----------------------------------------------------");
    }

    public void showImgInfo() {
        Document d = Jsoup.parse(html);
        Elements links = d.select("img");
        for (Element l : links) {
            System.out.println("imagen encontrada en html " + l.toString());
        }
        d = Jsoup.parse("<h1>html document</h1>");
        links = d.select("img");
        for (Element l : links) {
            System.out.println("imagen SANITIZADA encontrada en html " + l.toString());
        }
    }

    public void linkearImg() {
        Document d = Jsoup.parse(html);
        Elements links = d.select("img");
        Element aux = null;
        String r = "";
        String src = "";
        urlInlines = new ArrayList();
        for (Element l : links) {
            src = l.attr("src");            
            aux = l.clone();
            r = "<a href=\"" + src + "\" target=\"_blank\" ></a>";
            urlInlines.add(aux.attr("src"));
            html = html.replace(l.toString(), r);
        }
    }

}
