package org.savand.mailsender.mailer;

import java.io.IOException;

public interface IEmailSender {
  
  public boolean sendMail2Address(String emAddress, String mailBody) throws IOException;
  
}
