package arctic.example.pi.repository;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.MaxRecParType;
import arctic.example.pi.entity.TypeReclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface TypeRecRepository extends JpaRepository<TypeReclamation , Long> {
    List<TypeReclamation> findTypeReclamationByDateCreationBetween(Date date1, Date date2);
Set<TypeReclamation> findTypeByDateCreation(Date DateCreation);
    int countAllByNom(String nom);

//    @Query(value="SELECT new arctic.example.pi.DTO.DataStat(count(*),nom) from TypeReclamation  group by nom")
//    List<DataStat> statistque();

    @Query(value="SELECT new arctic.example.pi.DTO.CountType(count(*),dateCreation,nom) from TypeReclamation  group by nom")
    List<CountType> statistque();

//    @Query(value = "select count (e.dateCreation) as nbr,d.nom as nom,e.dateCreation from TypeReclamation d  LEFT JOIN Reclamation e ON d.idType=e.typeReclamation.idType  where e.dateCreation between :date1 and :date2 group by (d.idType)")
//    List<MaxRecParType> nbReclamationBytypeBetweenDeuxDates(@Param("date1") Date date1, @Param("date2") Date date2);

    @Query("SELECT r.typeReclamation, COUNT(r.idRec) FROM Reclamation r  where r.dateCreation between :date1 and :date2  GROUP BY r.typeReclamation")
    List<Object[]> nbReclamationBytypeBetweenDeuxDates(@Param("date1") Date date1, @Param("date2") Date date2);

}
