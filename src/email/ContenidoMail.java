
package model.domain;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Roberto
 */
public class ContenidoMail {

    private String messageID;
    private String from;
    private String to;
    private String cc;
    private String sbj;
    private String sendDateString;
    private Date sendDate;
    private String texto;
    private String html;
    private List<String> cidImgInline;
    private List<String> nameAttachments;
    private List<String> pathImgInline;
    private List<String> pathImgAttachments;
    private List<String> urlinlines;

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<String> getCidImgInline() {
        return cidImgInline;
    }

    public void setCidImgInline(List<String> cidImgInline) {
        this.cidImgInline = cidImgInline;
    }

    public List<String> getNameAttachments() {
        return nameAttachments;
    }

    public void setNameAttachments(List<String> nameAttachments) {
        this.nameAttachments = nameAttachments;
    }

    public List<String> getPathImgInline() {
        return pathImgInline;
    }

    public void setPathImgInline(List<String> pathImgInline) {
        this.pathImgInline = pathImgInline;
    }

    public List<String> getPathImgAttachments() {
        return pathImgAttachments;
    }

    public void setPathImgAttachments(List<String> pathImgAttachments) {
        this.pathImgAttachments = pathImgAttachments;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSbj() {
        return sbj;
    }

    public void setSbj(String sbj) {
        this.sbj = sbj;
    }

    public String getSendDateString() {
        return sendDateString;
    }

    public void setSendDateString(String sendDateString) {
        this.sendDateString = sendDateString;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public List<String> getUrlinlines() {
        return urlinlines;
    }

    public void setUrlinlines(List<String> urlinlines) {
        this.urlinlines = urlinlines;
    }

    @Override
    public String toString() {
        return "ContenidoMail{" + "messageID=" + messageID + ", from=" + from + ", to=" + to + ", cc=" + cc + ", sbj=" + sbj + ", sendDate=" + sendDateString + ", texto=" + texto + ", cidImgInline=" + cidImgInline + ", nameAttachments=" + nameAttachments + ", pathImgInline=" + pathImgInline + ", pathImgAttachments=" + pathImgAttachments + ", urlinlines=" + urlinlines + '}';
    }
    
}
