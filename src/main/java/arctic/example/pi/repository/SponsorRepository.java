package arctic.example.pi.repository;

import arctic.example.pi.entity.Sponsor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SponsorRepository extends CrudRepository<Sponsor,Long> {
}
