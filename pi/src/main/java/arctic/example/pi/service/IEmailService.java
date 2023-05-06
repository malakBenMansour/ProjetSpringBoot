package arctic.example.pi.service;

import arctic.example.pi.entity.Blog;

import javax.validation.constraints.Email;
import java.awt.*;

public interface IEmailService {

    String sendSimpleMail(Blog blog);

    String sendEmail(String email, String subject, String message);


    String sendMailWithAttachment(Email details);

    void send(String to, String email);
}
