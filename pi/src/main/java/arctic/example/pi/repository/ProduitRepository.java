package arctic.example.pi.repository;


import arctic.example.pi.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Set<Produit> findByName(String name);

    Set<Produit> findByDateExpBetween(Date date1, Date date2);

   /* @Query("SELECT p.categorie, COUNT(p) FROM Produit p GROUP BY p.categorie")
    List<Object[]> countProduitsByCategorie();*/

    @Query("SELECT COUNT(p) FROM Produit p WHERE p.categorie.idcat = :idcat")
    public int countProduitsByCategorie(@Param("idcat") Long idcat);
}