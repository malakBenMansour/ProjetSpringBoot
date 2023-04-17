package arctic.example.pi.repository;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BlogRepository extends CrudRepository<Blog,Long> {
}
