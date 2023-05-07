package arctic.example.pi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Donations implements Serializable {
    @Transient
    private JavaMailSender mailSender;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private int quantite;
    private boolean suivi = false;
    public Donations(Associations association, User user, int quantite, Date dateDon, JavaMailSender mailSender) {
        this.associations = association;
        this.user = user;
        this.quantite = quantite;
        this.date = dateDon;
        this.mailSender = mailSender;
    }
  /*  @PostPersist
    public void apresPersistance() {
        this.suivi = true;
    }*/


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "idA")
    private Associations associations;


}
