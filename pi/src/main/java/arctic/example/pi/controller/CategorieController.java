package arctic.example.pi.controller;


import arctic.example.pi.entity.Categorie;
import arctic.example.pi.repository.ProduitRepository;
import arctic.example.pi.service.CategorieService;
import arctic.example.pi.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/categorie")
public class CategorieController {

    @Autowired
    CategorieService categorieService;

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    ProduitService produitService;

    @PostMapping("/AddCat")
    Categorie addCat(@RequestBody Categorie c) {
        return categorieService.addCat(c);
    }
    @GetMapping("/AfficherCat/{id}")
    Categorie retrieveCaat(@PathVariable Long idcat) {
        return categorieService.retrieveCat(idcat);
    }
    @GetMapping("/AfficherAllCat")
    List<Categorie> retrieveAllCat() {
        return categorieService.retrieveAllCategorie();
    }

    @DeleteMapping("/DeleteCat/{id}")
    void deleteCat(@PathVariable Long idcat) {
        categorieService.deleteCat(idcat);
    }
    @PutMapping("/ModifierCat")
    Categorie updatePiste(@RequestBody Categorie c) {
        return categorieService.updateCat(c);
    }





}
