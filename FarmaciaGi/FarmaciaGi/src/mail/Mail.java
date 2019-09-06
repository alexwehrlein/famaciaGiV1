/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author saube
 */
public class Mail {
    public static String Username = "farmaciagicontacto@gmail.com";
    public static String PassWord = "admingi++2019";
    String To;
    String Mensage;
    String Subject;
    
    public  void send_mail(String correo , String text , String sub){
        this.To = correo; this.Mensage = text; this.Subject = sub;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Username, PassWord);
                    }
                });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensage);
 
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "Su mensaje ha sido enviado");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
     public static void main(String[] args) {
       Mail mail = new Mail();
        mail.send_mail("sauber_alex@outlook.com", "Prueba" , "Soy un correo");
    }
    
}
