package arctic.example.pi.repository;

import arctic.example.pi.entity.Associations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<Associations,Long> {
}
