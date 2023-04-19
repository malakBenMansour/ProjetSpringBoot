package tn.esprit.picloudbeta.entity;

import javax.persistence.*;

import java.util.Set;

@Entity
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long numSponsor;

    public String nomSponsor;

    @ManyToMany
    public Set<Evenement> event;


    public Long getNumSponsor() {
        return numSponsor;
    }

    public void setNumSponsor(Long numSponsor) {
        this.numSponsor = numSponsor;
    }

    public String getNomSponsor() {
        return nomSponsor;
    }

    public void setNomSponsor(String nomSponsor) {
        this.nomSponsor = nomSponsor;
    }

    public Set<Evenement> getEvent() {
        return event;
    }

    public void setEvent(Set<Evenement> event) {
        this.event = event;
    }
}
