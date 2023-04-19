package arctic.example.pi.service;


import arctic.example.pi.entity.Categorie;

import java.util.List;

public interface Icategorie {

    public Categorie addCat(Categorie c) ;

    void deleteCat(Long idcat);

    Categorie updateCat(Categorie c);

    List<Categorie> retrieveAllCategorie();
    Categorie retrieveCat(Long idcat);




}
