package tn.esprit.picloudbeta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.picloudbeta.entity.Evenement;

@Repository
public interface EventRepository extends CrudRepository<Evenement,Long> {
}
