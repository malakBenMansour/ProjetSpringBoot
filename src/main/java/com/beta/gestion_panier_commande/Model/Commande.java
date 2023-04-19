package com.beta.gestion_panier_commande.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idC;

    @Column(name = "nom")
    private String nom ;
    @Column(name = "prenom")
    private String prenom ;
    @Column(name = "ref_product")
    private Integer ref_product ;

    @Column(name = "num_tel")
    private Integer num_tel ;
    @Column(name = "codepostal")
    private Integer codepostal ;
    @Column(name = "Etat")
    private String Etat ;

    @Column(name = "adressmail")
    private String adressmail ;

    @Column(name = "mode_paiment")
    private String mode_paiment ;



    public Commande() {
    }

    public Commande(String nom, String prenom, Integer ref_product, Integer num_tel, Integer codepostal, String etat, String adressmail, String mode_paiment) {
        this.nom = nom;
        this.prenom = prenom;
        this.ref_product = ref_product;
        this.num_tel = num_tel;
        this.codepostal = codepostal;
        Etat = etat;
        this.adressmail = adressmail;
        this.mode_paiment = mode_paiment;
    }

    public long getIdC() {
        return idC;
    }

    public void setIdC(long idC) {
        this.idC = idC;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getRef_product() {
        return ref_product;
    }

    public void setRef_product(Integer ref_product) {
        this.ref_product = ref_product;
    }

    public Integer getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(Integer num_tel) {
        this.num_tel = num_tel;
    }

    public Integer getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(Integer codepostal) {
        this.codepostal = codepostal;
    }

    public String getEtat() {
        return Etat;
    }

    public void setEtat(String etat) {
        Etat = etat;
    }

    public String getAdressmail() {
        return adressmail;
    }

    public void setAdressmail(String adressmail) {
        this.adressmail = adressmail;
    }

    public String getMode_paiment() {
        return mode_paiment;
    }

    public void setMode_paiment(String mode_paiment) {
        this.mode_paiment = mode_paiment;
    }

    public Commande(long idC, String nom, String prenom, Integer ref_product, Integer num_tel, Integer codepostal, String etat, String adressmail, String mode_paiment) {
        this.idC = idC;
        this.nom = nom;
        this.prenom = prenom;
        this.ref_product = ref_product;
        this.num_tel = num_tel;
        this.codepostal = codepostal;
        Etat = etat;
        this.adressmail = adressmail;
        this.mode_paiment = mode_paiment;




    }

    @Override
    public String toString() {
        return "Commande{" +
                "idC=" + idC +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ref_product=" + ref_product +
                ", num_tel=" + num_tel +
                ", codepostal=" + codepostal +
                ", Etat='" + Etat + '\'' +
                ", adressmail='" + adressmail + '\'' +
                ", mode_paiment='" + mode_paiment + '\'' +
                '}';
    }
}
