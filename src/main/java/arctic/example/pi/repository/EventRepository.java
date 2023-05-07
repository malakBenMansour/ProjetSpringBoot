package arctic.example.pi.repository;

import arctic.example.pi.entity.Evenement;
import arctic.example.pi.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;



@Repository
public interface EventRepository extends CrudRepository<Evenement,Long> {

    @Query(value = "select * from Evenement where date_fin > NOW()",nativeQuery = true)
    List<Evenement> getActiveEvents();


    @Query(value = "SELECT * FROM Evenement WHERE (date_debut < :date_fin AND date_fin > :date_debut)",nativeQuery = true)
    List<Evenement> getEventCondition(@Param("date_debut") Date date_debut, @Param("date_fin") Date date_fin);

    @Query(value="SELECT e FROM Evenement e JOIN e.users r WHERE r.id = :num_user")
    List<Evenement> getEventsByUser(@Param("num_user") Long num_user);

   /* @Query(value="SELECT u FROM User u JOIN u.event e WHERE e.numEvent = :num_event")
    List<User> getUsersByEvent(@Param("num_event") Long num_event);*/
}
