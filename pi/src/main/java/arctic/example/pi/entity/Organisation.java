package arctic.example.pi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Organisation implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String description;
    @Enumerated(EnumType.STRING)
    private  TypeOrganisation typeorganisation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="organisation")
    private Set<User> users;
    @Column(columnDefinition = "longtext")
    private String image;


}

