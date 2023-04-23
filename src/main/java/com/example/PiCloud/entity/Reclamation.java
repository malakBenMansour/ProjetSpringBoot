package com.example.PiCloud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reclamation implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRec ;
    private String nomRec ;
    private String description ;
    private String priority;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(TemporalType.DATE)
//    private LocalDateTime dateCreation;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateCreation;

    @ManyToOne
    @JoinColumn(name = "type_reclamation_id")
    @JsonIgnore
    private TypeReclamation typeReclamation;


}
