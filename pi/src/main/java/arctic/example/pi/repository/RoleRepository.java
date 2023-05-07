package arctic.example.pi.repository;

import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(ERole role);
}
