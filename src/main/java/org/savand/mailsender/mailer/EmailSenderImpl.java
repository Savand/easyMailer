package org.savand.mailsender.mailer;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.savand.mailsender.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailSenderImpl implements IEmailSender {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);
  
  boolean messageSent;
  
  @Override
  public boolean sendMail2Address(String emAddress, String mailBody) throws IOException {
    
    Properties properties = PropertyUtil.getPropertyFileFromResources("email");
    String senderEmailAddress = properties.getProperty("senderEmailAddress");
    String senderEmailPassword = properties.getProperty("senderEmailPassword");
    
    
    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        LOGGER.debug("Process of authentication started...");
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication(senderEmailAddress, senderEmailPassword);
        LOGGER.debug("Process of authentication succesfully finished.");
        return passwordAuthentication;
      }
    });
    
    try {
      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress(senderEmailAddress));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(emAddress));
      message.setSubject(properties.getProperty("subject"));
      message.setContent(mailBody, "text/html");
      Transport.send(message);
     
      LOGGER.debug("Message successfully was sent to " + emAddress);
      messageSent = true;
      
   }catch (MessagingException mex) {
     LOGGER.debug("Failed to send message to " + emAddress);
      mex.printStackTrace();
   }
    
    return messageSent;
  }
  

}
