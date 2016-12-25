package org.savand.mailsender.service;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.savand.mailsender.dao.IUserDao;
import org.savand.mailsender.dao.UserDaoImpl;
import org.savand.mailsender.exception.BatchMailException;
import org.savand.mailsender.mailer.EmailSenderImpl;
import org.savand.mailsender.mailer.IEmailSender;
import org.savand.mailsender.model.User;

public class BatchMailerImpl implements IBatchMailer{

  IUserDao userDao;
  IEmailSender mailSender;
  
  
  public BatchMailerImpl(IUserDao userDao, IEmailSender mailSender) {
    this.userDao = userDao;
    this.mailSender = mailSender;
  }
  

  @Override
  public boolean sentMail2DbUsers() throws BatchMailException {
	
	boolean mailSuccessfullySent = false;
    List<User> users = null;
    
    try {
      users = userDao.getUsers();
    } catch (SQLException e) {
        if (users == null)
      	  System.exit(e.getErrorCode());
      throw new BatchMailException(e.getMessage());
    }
    
    
    for (User user : users) {
      try {
        
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
        Template t = ve.getTemplate("mailTemplate.vm");
        VelocityContext context = new VelocityContext();
        context.put("name", user.getEmail());
        context.put("clock", new Date());
        StringWriter emailBody = new StringWriter();
        t.merge(context, emailBody);
        
        
        mailSender.sendMail2Address(user.getEmail(), emailBody.toString());
        mailSuccessfullySent = true;
        
      } catch (IOException e) {
        throw new BatchMailException(e.getMessage());
      }
    }
    
    return mailSuccessfullySent;
  }
  
  public static void main(String[] args) throws BatchMailException {
    BatchMailerImpl batchMailerImpl = new BatchMailerImpl(new UserDaoImpl(), new EmailSenderImpl());
    batchMailerImpl.sentMail2DbUsers();
  }
}
