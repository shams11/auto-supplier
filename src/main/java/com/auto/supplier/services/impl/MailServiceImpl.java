package com.auto.supplier.services.impl;

import com.auto.supplier.models.User;
import com.auto.supplier.properties.AutoSupplierSpringProperty;
import com.auto.supplier.properties.MailProperty;
import com.auto.supplier.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
  public void sendMail(User user, String resetPasswordLink) {
    SimpleMailMessage mail = buildSimpleMailMessage(user, resetPasswordLink);
    javaMailSender.send(mail);
  }

  private SimpleMailMessage buildSimpleMailMessage(User user,
                                                   String resetPasswordLink) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setFrom(mailProperty.getUsername());
    mail.setSubject(mailProperty.getCreateUserSubject());
    mail.setText("To reset your password, click the link below:\n" + resetPasswordLink);
    return mail;
  }


  @Override
  public void sendMailWithAttachment(User user) throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    buildMimeMessageHelper(message, user);
    javaMailSender.send(message);
  }

  private void buildMimeMessageHelper(MimeMessage message, User user)
      throws MessagingException {
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
    mimeMessageHelper.setTo(user.getEmail());
    mimeMessageHelper.setFrom(mailProperty.getUsername());
    mimeMessageHelper.setSubject(mailProperty.getPartProductionSubject());
    // TODO: Need to remove the hard coding below
    mimeMessageHelper.setText(" Test email with attachment from shams");
    FileSystemResource file
        = new FileSystemResource(new
        File("/Users/i334072/Desktop/US Invite letter format.doc"));
    mimeMessageHelper.addAttachment("Visa-doc", file);
  }
}
