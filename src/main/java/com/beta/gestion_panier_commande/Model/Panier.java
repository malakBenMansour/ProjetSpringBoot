package com.beta.gestion_panier_commande.Model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "panier")

public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "ref_product")
    private Integer ref_product ;

    @Column(name = "total_panier")
    private Integer total_panier;

    @Column(name = "nbr_product")
    private Integer nbr_product;

    @Column(name = "prix_product")
    private Integer prix_product;
    @Column(name = "Cin")
    private Integer Cin ;



    public Panier() {
    }

    public Panier(long id, Integer ref_product, Integer total_panier, Integer nbr_product, Integer prix_product, Integer cin) {
        this.id = id;
        this.ref_product = ref_product;
        this.total_panier = total_panier;
        this.nbr_product = nbr_product;
        this.prix_product = prix_product;
        Cin = cin;
    }

    public Panier(Integer ref_product, Integer total_panier, Integer nbr_product, Integer prix_product, Integer cin) {
        this.ref_product = ref_product;
        this.total_panier = total_panier;
        this.nbr_product = nbr_product;
        this.prix_product = prix_product;
        Cin = cin;
    }

    public Panier(long id, Integer ref_product, Integer total_panier, Integer nbr_product, Integer prix_product) {

        this.id = id;
        this.ref_product = ref_product;
        this.total_panier = total_panier;
        this.nbr_product = nbr_product;
        this.prix_product = prix_product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getRef_product() {
        return ref_product;
    }

    public void setRef_product(Integer ref_product) {
        this.ref_product = ref_product;
    }

    public Integer getTotal_panier() {
        return total_panier;
    }

    public void setTotal_panier(Integer total_panier) {
        this.total_panier = total_panier;
    }

    public Integer getNbr_product() {
        return nbr_product;
    }

    public void setNbr_product(Integer nbr_product) {
        this.nbr_product = nbr_product;
    }

    public Integer getPrix_product() {
        return prix_product;
    }

    public void setPrix_product(Integer prix_product) {
        this.prix_product = prix_product;
    }

    public Integer getCin() {
        return Cin;
    }

    public void setCin(Integer cin) {
        Cin = cin;
    }

    @Override
    public String toString() {
        return "Panier{" +
                "idP=" + id +
                ", ref_product=" + ref_product +
                ", total_panier=" + total_panier +
                ", nbr_product=" + nbr_product +
                ", prix_product=" + prix_product +
                ", Cin=" + Cin +
                '}';
    }
}
