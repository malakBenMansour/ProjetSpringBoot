package arctic.example.pi.service;

import arctic.example.pi.DTON.CountType;
import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.Role;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.OrganisationRepository;
import arctic.example.pi.repository.RoleRepository;
import arctic.example.pi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.ByteArrayInputStream;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
//@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;

    public UserServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
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
        user.setStateuser(true);

        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public User updateUser(User user) {
        User updateUser = userRepo.findById(user.getId()).get();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        updateUser.setPassword(hashedPassword);
        updateUser.setName(user.getName());
        updateUser.setPrenom(user.getPrenom());
        updateUser.setTel(user.getTel());
        updateUser.setAddress(user.getAddress());
        updateUser.setUsername(user.getUsername());
        updateUser.setDatenaissance(user.getDatenaissance());
        updateUser.setEmail(user.getEmail());
        return userRepo.save(updateUser);
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
        else if(user.isStateuser()==true)
        {
            user.setStateuser(false);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(user1.getEmail());
        user.setName(user1.getName());
        user.setPrenom(user1.getPrenom());
        user.setDatenaissance(user1.getDatenaissance());
        user.setTel(user1.getTel());
        user.setAddress(user1.getAddress());
        return userRepo.save(user);
    }
    @Override
    public User desactivateUser(User user1)
    { User user=userRepo.findByUsername(user1.getUsername());
        if(user.isStateuser()==true)
        {
            user.setStateuser(false);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(user1.getEmail());
        user.setName(user1.getName());
        user.setPrenom(user1.getPrenom());
        user.setDatenaissance(user1.getDatenaissance());
        user.setTel(user1.getTel());
        user.setAddress(user1.getAddress());
        return userRepo.save(user);
    }
    // update password
    public void updatePassword(String emailUser, String newPassword) {
       User u = userRepo.findByEmail(emailUser);
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        u.setPassword(encodedPassword);
        userRepo.save(u);
    }
    public List<User>  findAllByOrderBOrderByRolesDesc()
    {
        return userRepo.findAllByOrderByRolesDesc();
    }


    // stats nbre of users actives or desactive
   public List<CountType> statistque()
   {
       return userRepo.statistque();
   }

   // stats nbre of max age
   public  List<CountType> statistqueAge()
   {
       return userRepo.minmaxeage();
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
            PdfPCell NomCell = new PdfPCell(new Phrase(user.getName()));
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

/*public void forgotpass(String emailuser) {

    User d = userRepo.findByEmail(emailuser);
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailuser);
        mailMessage.setText("This a non reply message from malak\n " + "Dear Client \n"
                + "Please follow the following link to reset your password: \n" + "http://localhost:8090/user/updatepassword");
        mailMessage.setSubject("password reset");
        javaMailSender.send(mailMessage);
        //return "Mail Sent Successfully...";
    }*/
    public void forgotpass(String emailuser) {

    User d = userRepo.findByEmail(emailuser);
        SimpleMailMessage mailMessage
                = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailuser);
        mailMessage.setText("This a non reply message from malak\n " + "Dear Client \n"
                + "Please follow the following link to reset your password: \n" + "http://localhost:4200/update");
        mailMessage.setSubject("password reset");
        javaMailSender.send(mailMessage);
        //return "Mail Sent Successfully...";
    }



    public boolean ifEmailExist(String email) {
        return userRepo.existsByEmail(email);
    }
/// recherche stream
    @Override
public List<User> searchh(String s) {

    return userRepo.findAll().stream().filter(user -> user.getName()!=null )
            .filter(user -> user.getName().contains(s)  ).collect(Collectors.toList());


}

// scheduler

   // @Scheduled(fixedRate = 5000)
   @Scheduled(cron = "0 0 8 * * MON")
    public List<User> getdisable()
    {

        return userRepo.getusersdisable();
    }
}
