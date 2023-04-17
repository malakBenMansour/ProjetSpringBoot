package arctic.example.pi.repository;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.CachedRowSet;
import java.util.Set;

@Repository

public interface ArticlesRepository extends CrudRepository<Articles,Long> {
    Set<Articles> findAllByBlog(Blog blog);
}
