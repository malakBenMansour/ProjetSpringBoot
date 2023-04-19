package tn.esprit.picloudbeta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.picloudbeta.entity.Sponsor;

@Repository
public interface SponsorRepository extends CrudRepository<Sponsor,Long> {
}
