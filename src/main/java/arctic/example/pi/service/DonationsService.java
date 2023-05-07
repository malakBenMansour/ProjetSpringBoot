package arctic.example.pi.service;


import arctic.example.pi.entity.Donations;
import arctic.example.pi.repository.DonationsRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DonationsService implements IDonationsService{
    @Autowired
    DonationsRepostitory donationsRepostitory;
@Autowired
private JavaMailSender sender;
  //  @Autowired
   // private TemplateEngine templateEngine;

    @Override
    public Donations saveDonation(Donations donations) throws MessagingException {
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(donations.getUser().getEmail());
        message.setSubject("Merci pour votre don !");
        message.setText("Bonjour " + donations.getUser().getNom() + ",\n\n"
                + "Nous vous remercions pour votre don de " + donations.getQuantite() + " euros.\n\n"
                + "Cordialement,\n"
                + "L'équipe de " + donations.getAssociations().getNom());
        mailSender.send(message);*/
        //**************************************************************************************************
      /*  MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        try {
            helper.setTo(donations.getUser().getEmail());
            helper.setSubject("Confirmation de don");

            // Ajoutez les valeurs du modèle HTML dans le contexte Thymeleaf
            Context context = new Context();
            Map<String, Object> variables = new HashMap<>();
            variables.put("nom", donations.getUser().getNom());
            variables.put("montant", donations.getQuantite());
            variables.put("association", donations.getAssociations().getNom());
            context.setVariables(variables);
            // Génère le contenu HTML à partir du modèle Thymeleaf
            String html = templateEngine.process("donation-template", context);

            helper.setText(html, true /* isHtml );
          /*  sender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
*/
        //*****************************************************
        return  donationsRepostitory.save(donations);
    }

    @Override
    public Donations updateDonation(Donations donations) {
        return donationsRepostitory.save(donations);
    }

    @Override
    public void deleteDonation(Long id) {
       donationsRepostitory.deleteById(id);

    }

    @Override
    public List<Donations> getDonation() {
        return donationsRepostitory.findAll();
    }

    @Override
    public List<Object[]> getSumQuantiteByAssociation() {
        return donationsRepostitory.sumQuantiteByAssociation();
    }



    @Override
    public List<Object[]> avgQuantiteByAssociation() {
        return donationsRepostitory.avgQuantiteByAssociation();
    }

    @Override
    public List<Object[]> maxQuantiteByAssociation() {
        return donationsRepostitory.maxQuantiteByAssociation();
    }

    @Override
    public List<Object[]> countDonsByAssociation() {
        return donationsRepostitory.countDonsByAssociation();
    }

    @Override
    public List<Object[]> maxMontantByMonth() {
        return donationsRepostitory.maxMontantByMonth();
    }

    //--------------send email----------
    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("ikbel.benmansour@esprit.tn");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        sender.send(message);
        System.out.println("Mail Send...");
    }

}
