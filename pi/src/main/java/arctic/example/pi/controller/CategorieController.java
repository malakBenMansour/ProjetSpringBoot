package arctic.example.pi.controller;


import arctic.example.pi.entity.Categorie;
import arctic.example.pi.repository.ProduitRepository;
import arctic.example.pi.service.CategorieService;
import arctic.example.pi.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/categorie")
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
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        categorieService.deleteCat(id);
    }
    @PutMapping("/ModifierCat")
    Categorie updatePiste(@RequestBody Categorie c) {
        return categorieService.updateCat(c);
    }


    @GetMapping("/exportpdf")
    public ResponseEntity<InputStreamResource> exportPdf() {
        List<Categorie> categories =  categorieService.retrieveAllCategorie();
        ByteArrayInputStream bais =  categorieService.blogExport(categories);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=user.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Long id, @RequestBody Categorie updatedArticle) {
        Categorie categorie = categorieService.retrieveCat(id);
        if(categorie == null) {
            return ResponseEntity.notFound().build();
        }

        categorie.setName(updatedArticle.getName());
        categorie.setDescription(updatedArticle.getDescription());


        Categorie savedArticle = categorieService.addCat(categorie);
        if(savedArticle == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(savedArticle);
    }

}
