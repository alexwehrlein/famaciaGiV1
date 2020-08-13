/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 *
 * @author saube
 */
public class Mail {

    public static String Username = "soporte@terabytet.com.mx";//farmaciagicontacto@gmail.com
    public static String PassWord = "carlos$%&02";//admingi++2019
    String To;
    String Mensage;
    String Subject;
    
    public void send_mail(String correo, String text, String sub, int file) {
        this.To = correo;
        this.Mensage = text;
        this.Subject = sub;

        
        
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "mail.terabytet.com.mx");  //El servidor SMTP de Google smtp.gmail.com
        props.put("mail.smtp.user", Username);
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google 587

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(Username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(To));   //Se podrían añadir varios de la misma manera
            message.setSubject(Subject);
            message.setText(Mensage);

            if (file == 1) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                DataSource source = new FileDataSource("C:\\farmacia\\inventario.pdf");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("Inventario.pdf");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }
            if (file == 2) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                DataSource source = new FileDataSource("C:\\farmacia\\bajas.pdf");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("Bajas.pdf");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }
            if (file == 3) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                DataSource source = new FileDataSource("C:\\farmacia\\faltantes.pdf");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("Faltantes.pdf");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }
            if (file == 4) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
                DataSource source = new FileDataSource("C:\\farmacia\\ventas.pdf");
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("Faltantes.pdf");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            }

            Transport transport = session.getTransport("smtp");
            transport.connect("mail.terabytet.com.mx", Username, PassWord);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            //JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Correo enviado</h1></html>" , "SUCCESS" , JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
            JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error al enviar el correo </h1></html>" , "ERROR" , JOptionPane.ERROR_MESSAGE);
        }

    }
}
