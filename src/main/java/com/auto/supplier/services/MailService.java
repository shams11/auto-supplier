package com.auto.supplier.services;

import com.auto.supplier.models.User;
import javax.mail.MessagingException;

public interface MailService {
  void sendMail(User user);
  void sendMailWithAttachment(User user) throws MessagingException;
}
