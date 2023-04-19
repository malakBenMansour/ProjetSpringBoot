package tn.esprit.picloudbeta.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numUser;

    @ManyToMany
    private Set<Evenement> event ;
}
