package com.beta.gestion_panier_commande.Controller;

import com.beta.gestion_panier_commande.Model.Commande;
import com.beta.gestion_panier_commande.Model.Panier;
import com.beta.gestion_panier_commande.Service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paniers")
public class PanierController {

    @Autowired
    private PanierService panierService;

    @GetMapping
    public ResponseEntity<List<Panier>> getAllPaniers() {
        return ResponseEntity.ok(panierService.getAllPaniers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Panier> getPanierById(@PathVariable("id") long id) {
        Optional<Panier> panier = panierService.getPanierById(id);
        return panier.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> addPanier(@RequestBody Panier panier) {
        panierService.addPanier(panier);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePanier(@PathVariable("id") long id, @RequestBody Panier panier) {
        Optional<Panier> existingPanier = panierService.getPanierById(id);
        if (existingPanier.isPresent()) {
            panier.setId(id);
            panierService.updatePanier(panier);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable("id") long id) {
        Optional<Panier> existingPanier = panierService.getPanierById(id);
        if (existingPanier.isPresent()) {
            panierService.deletePanier(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // http://localhost:9091/paniers/getAllOrderedByTotalPanierASC


    @GetMapping("/getAllOrderedByTotalPanierASC")
    List<Panier> getAllOrderedByTotalPanierASC(){
        return panierService.getAllOrderedByTotalPanierASC();
    }



    @GetMapping(params = "ref_product")
    public ResponseEntity<List<Panier>> getTotalPanierByProductRef(@RequestParam String ref_product) {
        List<Panier> paniers = panierService.findReferenceByTotalPanier(ref_product);
        if (paniers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(paniers, HttpStatus.OK);
        }
    }



}
