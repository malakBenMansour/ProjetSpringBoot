package arctic.example.pi.repository;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.TypeReclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value="SELECT new arctic.example.pi.DTO.CountType(count(*),nom) from TypeReclamation  group by nom")
    List<CountType> statistque();


}
