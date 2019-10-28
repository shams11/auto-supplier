package com.auto.supplier.services.impl;

import com.auto.supplier.models.User;
import com.auto.supplier.properties.AutoSupplierSpringProperty;
import com.auto.supplier.properties.MailProperty;
import com.auto.supplier.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

  private final JavaMailSender javaMailSender;
  private final MailProperty mailProperty;

  @Autowired
  public MailServiceImpl(JavaMailSender javaMailSender,
                         AutoSupplierSpringProperty autoSupplierSpringProperty) {
    this.javaMailSender = javaMailSender;
    this.mailProperty = autoSupplierSpringProperty.getMail();
  }

  @Override
  public void sendMail(User user) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setFrom(mailProperty.getUsername());
    mail.setSubject(mailProperty.getCreateUserSubject());
    mail.setText("Test email from shams");
    javaMailSender.send(mail);
  }
}
