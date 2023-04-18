package arctic.example.pi.repository;

import arctic.example.pi.entity.Donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationsRepostitory extends JpaRepository<Donations,Long> {
}
