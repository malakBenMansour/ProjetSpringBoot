package arctic.example.pi.service;

import arctic.example.pi.entity.Associations;
import arctic.example.pi.entity.Donations;
import arctic.example.pi.repository.AssociationRepository;
import arctic.example.pi.repository.DonationsRepostitory;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
public class AssociationsService implements IAssociationService {


@Autowired
    DonationsRepostitory donationsRepostitory;
    @Autowired
    AssociationRepository associationRepository;
  //@Autowired
    //private JavaMailSender mailSender;
    @Override
    public Associations saveAssociation(Associations associations) {

        return associationRepository.save(associations);
    }

    @Override
    public Associations updateAssociation(Associations associations) {
        Long id = associations.getId();
        Associations ass= associationRepository.findById(id).get();
        ass.setAdresse(associations.getAdresse());
        ass.setTel(associations.getTel());
        ass.setNom(associations.getNom());
        ass.setMail(associations.getMail());
        return associationRepository.save(ass);
    }

    @Override
    public void deleteAssociation(Long id) {
        associationRepository.deleteById(id);

    }

    @Override
    public List<Associations> getAssociation() {
        return associationRepository.findAll();
    }

    @Override
    public List<Associations> findAssociationsWithoutDon() {
        return associationRepository.findAssociationsWithoutDon();
    }
/*
    public void export(HttpServletResponse response, Long id) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        // Integer id = pro.getId();
        Associations associations = associationRepository.findById(id).get();
        // Programme prog = new Programme();

        String nom="nom:"+ associations.getNom();
        String adress="adresse:"+associations.getAdresse();
        String tel="téléphone:"+associations.getTel();

       // String Trip="Trip:" +associations.getTrip().getAdresse();

        document.open();
        com.lowagie.text.Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(20);

        Paragraph paragraph = new Paragraph("Association ", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        com.lowagie.text.Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(16);

        Paragraph paragraph2 = new Paragraph( nom, fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph3 = new Paragraph(adress, fontParagraph);
        paragraph3.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph paragraph4 = new Paragraph(tel, fontParagraph);
        paragraph4.setAlignment(Paragraph.ALIGN_CENTER);

       // Paragraph paragraph5 = new Paragraph(Trip, fontParagraph);
       // paragraph5.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);
        document.add(paragraph2);
        document.add(paragraph3);
        document.add(paragraph4);
      //  document.add(paragraph5);
        document.close();
    }
*/
/*public void export(HttpServletResponse response, Long id) throws IOException {
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, response.getOutputStream());
    // Integer id = pro.getId();
    Associations associations = associationRepository.findById(id).get();
    // Programme prog = new Programme();

    String nom="nom:"+ associations.getNom();
    String adress="adresse:"+associations.getAdresse();
    String tel="téléphone:"+associations.getTel();

    // String Trip="Trip:" +associations.getTrip().getAdresse();

    document.open();
    com.lowagie.text.Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    fontTitle.setSize(20);

    Paragraph paragraph = new Paragraph("Association ", fontTitle);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);

    com.lowagie.text.Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
    fontParagraph.setSize(16);

    Paragraph paragraph2 = new Paragraph( nom, fontParagraph);
    paragraph2.setAlignment(Paragraph.ALIGN_CENTER);

    Paragraph paragraph3 = new Paragraph(adress, fontParagraph);
    paragraph3.setAlignment(Paragraph.ALIGN_CENTER);

    Paragraph paragraph4 = new Paragraph(tel, fontParagraph);
    paragraph4.setAlignment(Paragraph.ALIGN_CENTER);

    // Paragraph paragraph5 = new Paragraph(Trip, fontParagraph);
    // paragraph5.setAlignment(Paragraph.ALIGN_CENTER);

    document.add(paragraph);
    document.add(paragraph2);
    document.add(paragraph3);
    document.add(paragraph4);
    //  document.add(paragraph5);
    document.close();
}

    /* public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("ikbel.benmansour@esprit.tn");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");
    }*/
public void exportAll(HttpServletResponse response) throws IOException, DocumentException {
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, response.getOutputStream());

    document.open();
    com.lowagie.text.Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    fontTitle.setSize(20);

    com.lowagie.text.Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
    fontParagraph.setSize(16);

    // Get the list of associations from the database
    List<Associations> associations = associationRepository.findAll();

    for (Associations association : associations) {
        // Add the association name to the PDF
        Paragraph paragraph = new Paragraph("Association de " + association.getNom(), fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        // Add the association details to the PDF
        Paragraph paragraph2 = new Paragraph("Adresse: " + association.getAdresse(), fontParagraph);
        Paragraph paragraph3 = new Paragraph("Téléphone: " + association.getTel(), fontParagraph);


        document.add(paragraph2);
        document.add(paragraph3);

        // Get the list of donations for this association from the database
       List<Donations> donations = donationsRepostitory.findDonationsByAssociations_Id(association.getId());
        if (!donations.isEmpty()) {
            // Add the donations header to the PDF
            Paragraph paragraph4 = new Paragraph("Liste des dons:", fontTitle);
            document.add(paragraph4);

            // Add the donations to the PDF
            for (Donations donation : donations) {
                Paragraph paragraph5 = new Paragraph(donation.getDescription() + " - " + donation.getQuantite() + "€", fontParagraph);
                document.add(paragraph5);
                Paragraph paragraph6= new Paragraph("de la part de : " + donation.getUser().getUsername(), fontParagraph);
                document.add((paragraph6));
            }
        }

        // Add a separator between the associations in the PDF
        Paragraph separator = new Paragraph("------------------------------------------------------------------------", fontParagraph);
        separator.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(separator);
    }

    document.close();
}


  /*  private void export(Associations associations, Document document) throws DocumentException {
        // Ajout du titre
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, new Color(52, 73, 94));
        Paragraph title = new Paragraph("Informations sur l'association " + associations.getNom(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Ajout des informations de l'association
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 14, new Color(44, 62, 80));
        Paragraph nom = new Paragraph("Nom: " + associations.getNom(), infoFont);
        document.add(nom);

        Paragraph adresse = new Paragraph("Adresse: " + associations.getAdresse(), infoFont);
        document.add(adresse);

        Paragraph tel = new Paragraph("Téléphone: " + associations.getTel(), infoFont);
        document.add(tel);

        // Ajout d'un saut de page
        document.newPage();
    }*/

    public List<Associations> findByNom(String nom){
       return associationRepository.findByNom(nom);
   }
public List<Associations> findByAdresse(String addr)
{
    return associationRepository.findByAdresse(addr);
}
public List<Associations> findByEmail(String mail)
{return associationRepository.findByMail(mail);}
    public void addAssociation(Associations association, MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        association.setImageFileName(fileName);
        associationRepository.save(association);

        String uploadDir = "association-images/" + association.getId();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException("Could not save image file: " + fileName, ex);
        }
    }

}