package arctic.example.pi.controller;


import arctic.example.pi.entity.Produit;
import arctic.example.pi.repository.CategorieRepository;
import arctic.example.pi.repository.ProduitRepository;
import arctic.example.pi.service.CategorieService;
import arctic.example.pi.service.ProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produit")
public class ProduitController {

@Autowired
ProduitService produitService;
    @Autowired
    CategorieService categorieService;
    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    CategorieRepository categorieRepository;
    @PostMapping("/AddProd")
    Produit addProd(@RequestBody Produit p) {
        return produitService.addProd(p);
    }



    @GetMapping("/AfficherProd/{id}")
    Produit retrieveProd(@PathVariable Long id) {
        return produitService.retrieveProd(id);
    }
    @GetMapping("/AfficherAllProd")
    List<Produit> retrieveAllPistes() {
        return produitService.retrieveAllProduits();
    }

    @DeleteMapping("/DeleteProd/{id}")
    void deleteProd(@PathVariable Long id) {
        produitService.deleteProd(id);
    }
    @PutMapping("/ModifierProd")
    Produit updatePiste(@RequestBody Produit p) {
        return produitService.updateProd(p);
    }

    @GetMapping("/AffichProdByName/{name}")
    Set<Produit> findByName(@PathVariable String name) {
        return produitService.findByName(name);
    }


    @GetMapping("/AffichProdBetween/{date1}/{date2}")
    Set<Produit> findByDateExpBetween(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date1 , @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date2){
        return produitService.findByDateExpBetween(date1 ,date2);
    }


    @PostMapping("/assign/{id}/{idcat}")
    public void assignCategorieToProduit(@PathVariable Long id, @PathVariable Long idcat) {
        produitService.assignCategorieToProduit(id, idcat);
    }


    @PostMapping("/{idcat}")
    public Produit addProd2(@RequestBody Produit p, @PathVariable Long idcat) {

        return produitService.ajouterProduitt(p,idcat);
    }

    @GetMapping("/{idcat}/")
    public ResponseEntity<Integer> countProduitsByCategorie(@PathVariable Long idcat) {
        int count = produitService.countProduitsByCategorie(idcat);
        return ResponseEntity.ok(count);
    }
}







