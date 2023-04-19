package com.beta.gestion_panier_commande.Service;

import com.beta.gestion_panier_commande.Model.Commande;
import com.beta.gestion_panier_commande.Repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {


    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    public Commande updateCommande(Long id, Commande commande) {
        Optional<Commande> optionalCommande = commandeRepository.findById(id);
        if (optionalCommande.isPresent()) {
            commande.setIdC(id);
            return commandeRepository.save(commande);
        } else {
            return null;
        }
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    // Services avancées

    public List<Commande> getCommandesByAdressmail(String adressmail) {
        return commandeRepository.findByAdressmail(adressmail);
    }


    public List<Commande> getCommandesByModePaiment(String modePaiment) {
        return commandeRepository.findByModePaiment(modePaiment);
    }



}
