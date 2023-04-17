package arctic.example.pi.repository;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findByAddress(String address);
    List<User> findByStateuser (boolean stateUser);

    @Query("SELECT MIN(e.datenaissance) FROM User e ")
    Date getminage();
    @Query("SELECT MAX(e.datenaissance) FROM User e ")
    Date getmaxage();


    @Query("SELECT COUNT(e.stateuser) FROM User e WHERE e.stateuser=false")
    int getNbDisabled();


    List<User> findAllByOrderByRolesDesc();


    @Query(value="SELECT new arctic.example.pi.DTO.CountType(count(*),stateuser) from User  group by stateuser")
     List<CountType> statistque();
}
