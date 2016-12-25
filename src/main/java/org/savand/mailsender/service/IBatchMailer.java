package org.savand.mailsender.service;

import org.savand.mailsender.exception.BatchMailException;

public interface IBatchMailer {
  
  public boolean sentMail2DbUsers() throws BatchMailException;

}
