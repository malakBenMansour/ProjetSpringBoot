package arctic.example.pi.service;


import arctic.example.pi.entity.Categorie;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface Icategorie {

    public Categorie addCat(Categorie c) ;

    void deleteCat(Long idcat);

    Categorie updateCat(Categorie c);

    List<Categorie> retrieveAllCategorie();

    Categorie updateArticle(Categorie categorie);

    Categorie retrieveCat(Long idcat);

    public ByteArrayInputStream blogExport(List<Categorie> Blog);


}
