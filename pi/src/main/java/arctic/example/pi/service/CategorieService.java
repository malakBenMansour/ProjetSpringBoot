package arctic.example.pi.service;


import arctic.example.pi.entity.Categorie;
import arctic.example.pi.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService implements Icategorie{

    @Autowired
    CategorieRepository categorieRepository;

    public Categorie addCat(Categorie c) {
        return categorieRepository.save(c);
    }

    public void deleteCat(Long idcat) {
        categorieRepository.deleteById(idcat);
    }

    public Categorie updateCat(Categorie c) {
        return categorieRepository.save(c);
    }
 public List<Categorie> retrieveAllCategorie() {
        List<Categorie> cb = (List<Categorie>) categorieRepository.findAll();
        return cb;
    }

    public Categorie retrieveCat(Long  idcat) {
        return categorieRepository.findById(idcat).orElse(null);
    }




}
