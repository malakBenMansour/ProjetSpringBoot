package arctic.example.pi.repository;

import arctic.example.pi.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.CachedRowSet;
import java.util.List;
import java.util.Set;

@Repository

public interface ArticlesRepository extends JpaRepository<Articles,Long> {
    Set<Articles> findAllByBlog(Blog blog);
    Set<Articles> findByTitre(String titre);


    @Query("SELECT e FROM Articles e ORDER BY e.titre DESC ")
    Set<Articles> triarticles () ;
    @Query("select count(*) FROM Articles a where a.statuarticle = 'ENCOURS'")
    int nbr();
    @Query("SELECT COUNT(a) FROM Articles a WHERE a.statuarticle = :statut")
    Long countArticlesByStatut(@Param("statut") Statuarticle statut);

    List<Articles> findAllByUser(User user);

}
