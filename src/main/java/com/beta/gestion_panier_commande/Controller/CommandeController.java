package com.beta.gestion_panier_commande.Controller;

import com.beta.gestion_panier_commande.Model.Commande;
import com.beta.gestion_panier_commande.Model.Panier;
import com.beta.gestion_panier_commande.Repository.CommandeRepository;
import com.beta.gestion_panier_commande.Service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {
    @Autowired
    private CommandeService commandeService;



    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.getAllCommandes();
        return new ResponseEntity<>(commandes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> optionalCommande = commandeService.getCommandeById(id);
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            return new ResponseEntity<>(commande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) {
        Commande createdCommande = commandeService.createCommande(commande);
        return new ResponseEntity<>(createdCommande, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        Commande updatedCommande = commandeService.updateCommande(id, commande);
        if (updatedCommande != null) {
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }






    // Services avancées

    // Search Commande By Email
    //  Test Example : http://localhost:8080/commandes?email=johndoe@example.com
    @GetMapping(params = "email")
    public ResponseEntity<List<Commande>> getCommandesByAdressmail(@RequestParam String email) {
        List<Commande> commandes = commandeService.getCommandesByAdressmail(email);
        if (commandes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(commandes, HttpStatus.OK);
        }
    }

    // Search By Payement Methode Function
    // Test Link : http://localhost:8080/commandes?mode_paiment=credit card
    @GetMapping(params = "mode_paiment")
    public ResponseEntity<List<Commande>> getCommandesByModePaiment(@RequestParam String mode_paiment) {
        List<Commande> commandes = commandeService.getCommandesByModePaiment(mode_paiment);
        if (commandes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(commandes, HttpStatus.OK);
        }
    }



}
