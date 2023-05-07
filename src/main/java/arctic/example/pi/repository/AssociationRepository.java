package arctic.example.pi.repository;

import arctic.example.pi.entity.Associations;
import arctic.example.pi.entity.Donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociationRepository extends JpaRepository<Associations,Long> {
    List<Associations> findByNom(String nom);
    List<Associations> findByAdresse(String adresse);
    List<Associations>findByMail(String mail);
    List<Associations> getAssociationsByAdresseOrderByAdresse(String adresse);
    @Query("SELECT a FROM Associations a WHERE a NOT IN (SELECT d.associations FROM Donations d)")
    List<Associations> findAssociationsWithoutDon();


}
