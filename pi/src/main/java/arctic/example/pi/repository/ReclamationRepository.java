package arctic.example.pi.repository;


import arctic.example.pi.entity.Reclamation;
import arctic.example.pi.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long > {
  Reclamation findByNomRec(String nomRec);
    Set<Reclamation> findByIdRec (Long idRec);
    Set<Reclamation> findByDateCreation(Date DateCreation);
    List<Reclamation> findByDateCreationBetween(Date date1, Date date2);

Reclamation existsByNomRec(String nomRec);
    @Query("select r from Reclamation r where r.status=:status order by r.dateCreation ASC ")
    List<Reclamation> findByStatusOrderByDateCreationDesc(Status status);

    int countAllByStatus(Status status);
    @Query("select r from Reclamation r order by r.nomRec ASC ")
    List<Reclamation> getAllOrderedByNomASC();

    @Query("SELECT r.typeReclamation, COUNT(r.idRec) FROM Reclamation r GROUP BY r.typeReclamation")
    List<Object[]> countReclamationsByType();





}



