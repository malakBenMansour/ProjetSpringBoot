package tn.esprit.picloudbeta.entity;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.persistence.*;
import java.sql.Date;

import java.util.Set;

@Entity
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numEvent;
    private String nomEvent;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFin ;
    private int nbrPlace ;
    @ManyToMany(mappedBy = "event")
    private Set<User> users;

    @ManyToMany(mappedBy = "event")
    private Set<Sponsor> sponsors;

    public Long getNumEvent() {
        return numEvent;
    }

    public void setNumEvent(Long numEvent) {
        this.numEvent = numEvent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbrPlace() {
        return nbrPlace;
    }

    public void setNbrPlace(int nbrPlace) {
        this.nbrPlace = nbrPlace;
    }


}
