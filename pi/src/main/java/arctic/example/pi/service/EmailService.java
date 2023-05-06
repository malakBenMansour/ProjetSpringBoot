package arctic.example.pi.service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

import arctic.example.pi.entity.User;
import arctic.example.pi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import arctic.example.pi.entity.Blog;

import javax.validation.constraints.Email;
@Service
public class EmailService implements IEmailService{

   @Autowired
   UserRepository userRepository ;

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public String sendSimpleMail(Blog blog) {
        String subject = "Nouvel Blog ajouté : " + blog.getTitre();
        String message = "Cher utilisateur,\n\n" + "Un nouvel Blog a été ajouté : " + blog.getTitre() +
                "\n\nCordialement,\nPetPalooza";
        List<User> users = userRepository.findAll();
        for (User user : users) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setText(message);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
        }
        return "Mail Sent Successfully...";
    }

    @Override
    public String sendEmail(String email, String subject, String message) {
        return null;
    }

    @Override
    public String sendMailWithAttachment(Email details) {
        return null;
    }

    @Override
    public void send(String to, String email) {

    }
}
