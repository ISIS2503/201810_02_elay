/*
 * The MIT License
 *
 * Copyright 2018 ws.duarte.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package programa;

import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ws.duarte
 */
public class MailSender extends Thread{
    
    private String asunto,destinatarios,contenido;

    public MailSender(String asunto, String destinatarios, String contenido) {
        this.asunto = asunto;
        this.destinatarios = destinatarios;
        this.contenido = contenido;
    }
    
    

    @Override
    public void run() {
        try {
            enviarCorreo();
        } catch (Exception ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void enviarCorreo() throws Exception {
            Properties properties = new Properties();
            properties.load(new FileReader(new File("./data/hotmail.properties")));
            Session session = Session.getInstance(properties, null);
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(properties.getProperty("mail.from")));
            StringTokenizer emailsSt = new StringTokenizer(destinatarios, ";,");
            while (emailsSt.hasMoreTokens()) {
                String email = emailsSt.nextToken();
                try {
                    mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            mensaje.setSubject(asunto);
            mensaje.setSentDate(new Date(1, 1, 1));
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(contenido);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            mensaje.setContent(multipart);
            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
            try {
                //conectar al servidor
                transport.connect(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
                //enviar el mensaje
                transport.sendMessage(mensaje, mensaje.getAllRecipients());
            } finally {
                //cerrar la conexión 
                transport.close();
            }
        }
}
