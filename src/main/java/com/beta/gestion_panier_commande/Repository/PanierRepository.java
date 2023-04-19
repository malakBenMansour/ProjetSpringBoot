package com.beta.gestion_panier_commande.Repository;


import com.beta.gestion_panier_commande.Model.Commande;
import com.beta.gestion_panier_commande.Model.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierRepository  extends JpaRepository<Panier, Long>{

    @Query("select p from Panier p order by p.total_panier ASC ")
    List<Panier> getAllOrderedByTotalPanierASC();

    @Query("SELECT p FROM Panier p WHERE p.ref_product  = :ref_product  order by p.total_panier ASC")
    List<Panier> findByRefProduitOrderByTotalPanier(@Param("ref_product ") String ref_product );

}
