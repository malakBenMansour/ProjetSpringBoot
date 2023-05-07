package arctic.example.pi.repository;


import arctic.example.pi.entity.Associations;
import arctic.example.pi.entity.Donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationsRepostitory extends JpaRepository<Donations,Long> {
    @Query("SELECT d.associations.nom, SUM(d.quantite) FROM Donations d GROUP BY d.associations")
    List<Object[]> sumQuantiteByAssociation();
    @Query("SELECT  d.associations.nom, COUNT(d) FROM Donations d GROUP BY d.associations.nom")
    List<Object[]> countByAssociation();
    @Query("SELECT d.associations.nom, AVG(d.quantite) FROM Donations d GROUP BY d.associations")
    List<Object[]> avgQuantiteByAssociation();
    @Query("SELECT d.associations, MAX(d.quantite) FROM Donations d GROUP BY d.associations ORDER BY MAX(d.quantite) DESC")
    List<Object[]> maxQuantiteByAssociation();
    @Query("SELECT d.associations, COUNT(d.id) FROM Donations d GROUP BY d.associations ORDER BY COUNT(d.id) DESC")
    List<Object[]> countDonsByAssociation();
    @Query("SELECT YEAR(d.date), MONTH(d.date), MAX(d.quantite) FROM Donations d GROUP BY YEAR(d.date), MONTH(d.date)")
    List<Object[]> maxMontantByMonth();
  List<Donations> findDonationsByAssociations_Id(Long id);




}
