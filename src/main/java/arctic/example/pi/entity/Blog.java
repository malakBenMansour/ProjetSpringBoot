package arctic.example.pi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description ;
    @Enumerated(EnumType.STRING)
    private Typeblog Typeblog ;


    private String image ;
    @OneToMany(mappedBy = "blog")
    @JsonIgnore
    private Set<Articles> article = new HashSet<>() ;

    @ManyToOne
    @JsonIgnore
    private User user;



}
