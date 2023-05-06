package arctic.example.pi.repository;

import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(ERole role);
}
