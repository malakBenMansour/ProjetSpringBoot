package com.beta.gestion_panier_commande.Service;

import com.beta.gestion_panier_commande.Model.Commande;
import com.beta.gestion_panier_commande.Model.Panier;
import com.beta.gestion_panier_commande.Repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    public List<Panier> getAllPaniers() {
        return panierRepository.findAll();
    }

    public Optional<Panier> getPanierById(long id) {
        return panierRepository.findById(id);
    }

    public void addPanier(Panier panier) {
        panierRepository.save(panier);
    }

    public void updatePanier(Panier panier) {
        panierRepository.save(panier);
    }

    public void deletePanier(long id) {
        panierRepository.deleteById(id);
    }


    public List<Panier> getAllOrderedByTotalPanierASC() {
        return panierRepository.getAllOrderedByTotalPanierASC();
    }

    public List<Panier> findReferenceByTotalPanier(String ref_product) {
        return panierRepository.findByRefProduitOrderByTotalPanier(ref_product);
    }


}

