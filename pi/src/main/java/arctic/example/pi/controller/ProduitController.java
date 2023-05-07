package arctic.example.pi.controller;


import arctic.example.pi.entity.Categorie;
import arctic.example.pi.entity.Produit;
import arctic.example.pi.repository.CategorieRepository;
import arctic.example.pi.repository.ProduitRepository;
import arctic.example.pi.service.CategorieService;
import arctic.example.pi.service.ProduitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
@CrossOrigin(origins = "*")
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
    @PostMapping("/AddProd/{idc}")
    Produit addProd(@RequestBody Produit p,@RequestParam(value = "idc")Long idc) {
        return produitService.ajouterProduitt(p,idc);
    }



    @GetMapping("/AfficherProd/{id}")
    Produit retrieveProd(@PathVariable Long id) {
        return produitService.retrieveProd(id);
    }
    @GetMapping("/AfficherAllProd")
    List<Produit> retrieveAllCat() {
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
    @GetMapping("/exportpdf")
    public ResponseEntity<InputStreamResource> exportPdf() {
        List<Produit> categories =  produitService.retrieveAllProduits();
        ByteArrayInputStream bais =  produitService.blogExport(categories);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=user.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable("id") Long id, @RequestBody Produit updatedArticle) {
        Produit produit = produitService.retrieveProd(id);
        if(produit == null) {
            return ResponseEntity.notFound().build();
        }

        produit.setName(updatedArticle.getName());
        produit.setDescription(updatedArticle.getDescription());
        produit.setDateExp(updatedArticle.getDateExp());
        produit.setPrice(updatedArticle.getPrice());
        produit.setCategorie(updatedArticle.getCategorie());
        Produit savedArticle = produitService.addProd(produit);
        if(savedArticle == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(savedArticle);
    }

    @PostMapping("/ajouter/{id}")

    Produit addReclamation(@RequestBody Produit r,@PathVariable Long id) {
        return produitService.addblogtoarticles(r,id);
    }
    @PutMapping("/changer")
    public Produit changer(@RequestBody Produit p)
    {
        return produitService.changerEtat(p);
    }

}







