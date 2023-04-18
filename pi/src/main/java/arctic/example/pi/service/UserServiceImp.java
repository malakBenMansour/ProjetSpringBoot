package arctic.example.pi.service;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.Role;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.OrganisationRepository;
import arctic.example.pi.repository.RoleRepository;
import arctic.example.pi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayInputStream;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;

import java.util.Properties;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    @Autowired
    UserRepository userRepo;
@Autowired
    RoleRepository roleRepo;
@Autowired
    OrganisationRepository organisationRepository;

    @Override
    public List<User> getUsers() {

        return (List<User>) userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepo.save(user);
    }
//service avancés
    @Override
    public User addRoleToUser(String username, ERole roleName) {


            User user = userRepo.findByUsername(username);
            Role role = roleRepo.findByName(roleName);
            user.getRoles().add(role);
            return userRepo.save((user));

    }
    @Override
    public User addOrganisationToUser(String username, Long id) {


        User user = userRepo.findByUsername(username);
        Organisation organisation = organisationRepository.findById(id).get();
        user.setOrganisation(organisation);
        return userRepo.save(user);

    }
  // recherche
    @Override
    public User getUser(String username) {

        return userRepo.findByUsername(username);
    }

    @Override
    public User getUserByMail(String mail) {
        return this.userRepo.findByEmail(mail);
    }

    @Override
    public List<User> retrieveUserByState(boolean stateUser) {
        return (List<User>) userRepo.findByStateuser(stateUser);
    }
    @Override
    public List<User> retrieveUserByAddress(String adressUser) {
        return userRepo.findByAddress(adressUser);
    }
//activation user
    @Override
    public User activateUser(User user1)
    { User user=userRepo.findByUsername(user1.getUsername());
        if(user.isStateuser()==false)
        {
            user.setStateuser(true);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(user1.getEmail());
        user.setNom(user1.getNom());
        user.setPrenom(user1.getPrenom());
        user.setDatenaissance(user1.getDatenaissance());
        user.setTel(user1.getTel());
        user.setAddress(user1.getAddress());
        return userRepo.save(user);
    }

    // update password
    public void updatePassword(String emailUser, String newPassword, String confirmPassword) {
       User u = userRepo.findByEmail(emailUser);
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        String encodedConfirmPassword = passwordEncoder.encode(confirmPassword);
        u.setPassword(encodedPassword);
        u.setConfirmpassworduser(encodedConfirmPassword);

        userRepo.save(u);
    }
    public List<User>  findAllByOrderBOrderByRolesDesc()
    {
        return userRepo.findAllByOrderByRolesDesc();
    }

   public List<CountType> statistque()
   {
       return userRepo.statistque();
   }
// export pdf
public  ByteArrayInputStream userExport(List<User> users) {
   Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
        PdfWriter.getInstance(document, out);
        document.open();
        com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
        Paragraph para = new Paragraph("Liste des Utilisateurs", font);
        para.setAlignment(Element.ALIGN_CENTER);
        document.add(para);
        document.add(Chunk.NEWLINE);
        PdfPTable table = new PdfPTable(5);


        Stream.of("nom", "adresse", "email", "tel", "ETAT").forEach(headerTitle -> {
            PdfPCell header = new PdfPCell();
            com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            header.setBackgroundColor(BaseColor.BLUE);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(headerTitle, headFont));
            table.addCell(header);
        });

        for (User user : users) {
            PdfPCell NomCell = new PdfPCell(new Phrase(user.getNom()));
            NomCell.setPaddingLeft(1);
            NomCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            NomCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(NomCell);


            PdfPCell adresseCell = new PdfPCell(new Phrase(user.getAddress()));
            adresseCell.setPaddingLeft(1);
            adresseCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            adresseCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(adresseCell);


            PdfPCell emailCell = new PdfPCell(new Phrase(user.getEmail()));
            emailCell.setPaddingLeft(1);
            emailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            emailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(emailCell);

            PdfPCell telCell = new PdfPCell(new Phrase(user.getTel()));
            telCell.setPaddingLeft(1);
            telCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            telCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(telCell);

            PdfPCell siteCell = new PdfPCell(new Phrase(String.valueOf(user.isStateuser())));
            siteCell.setPaddingLeft(1);
            siteCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            siteCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(siteCell);
        }
        document.add(table);
        document.close();
    } catch (DocumentException e) {
        e.printStackTrace();
    }
    return new ByteArrayInputStream(out.toByteArray());
}

// forget password mailing
public void forgotpass(String emailuser) {
    // TODO Auto-generated method stub
    User d = userRepo.findByEmail(emailuser);

    final String username = "benmansourmalak18@gmail.com";
    final String password = "malak2001";

    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true"); // TLS
    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("benmansourmalak18@gmail.com", "malak2001");
        }
    });

    try {

        Message message;
        message = new MimeMessage(session);
        message.setFrom(new InternetAddress("benmansourmalak18@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailuser));
        message.setSubject("Reset Your Password");
        message.setText("This a non reply message from malak\n " + "Dear Client \n"
                + "Please follow the following link to reser your password: \n" + "http://localhost:8090/user/modifUser");

        Transport.send(message);

        //log.info("Done");

    } catch (MessagingException e) {
        e.printStackTrace();
    }

}

    public boolean ifEmailExist(String email) {
        return userRepo.existsByEmail(email);
    }

}
