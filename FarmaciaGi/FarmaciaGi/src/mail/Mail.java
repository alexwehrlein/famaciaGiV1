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
        
        Properties props = System.getProperties();
    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
    props.put("mail.smtp.user", Username);
    props.put("mail.smtp.clave", "miClaveDeGMail");    //La clave de la cuenta
    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

    Session session = Session.getDefaultInstance(props);
    MimeMessage message = new MimeMessage(session);

    try {
        message.setFrom(new InternetAddress(Username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(To));   //Se podrían añadir varios de la misma manera
        message.setSubject(Subject);
        message.setText(Mensage);
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", Username, PassWord);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Correo enviado</h1></html>" , "SUCCESS" , JOptionPane.INFORMATION_MESSAGE);
    }
    catch (MessagingException me) {
        me.printStackTrace();   //Si se produce un error
        JOptionPane.showMessageDialog(null, "<html><h1 align='center'>Error al enviar el correo </h1></html>" , "ERROR" , JOptionPane.ERROR_MESSAGE);
    }
   
    }
    
}
