package arctic.example.pi.repository;


import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.TypeOrganisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation,Long> {
    List<Organisation> findAllByAdresse(String adresse);
    List<Organisation> findAllByTypeorganisation(TypeOrganisation type);



}
