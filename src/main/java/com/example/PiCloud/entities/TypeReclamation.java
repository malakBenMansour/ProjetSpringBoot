package com.example.PiCloud.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TypeReclamation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idType ;
    private String nom;
    private String description;
    @Temporal(TemporalType.DATE)
//    private LocalDateTime dateCreation;
    @JsonFormat(pattern="yyyy-MMM-dd")
    private Date dateCreation;


    @OneToMany(mappedBy = "typeReclamation", cascade = CascadeType.ALL)
    private List<Reclamation> reclamations;

}
