package arctic.example.pi.service;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;
import arctic.example.pi.repository.ArticlesRepository;
import arctic.example.pi.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class ArticlesServiceImp implements ArticlesService {
    @Autowired
    ArticlesRepository ArticlesRepo;
    @Autowired
    BlogRepository blogRepository;
    @Override
    public Articles saveArticles(Articles articles) {
        return ArticlesRepo.save(articles);
    }

    @Override
    public Articles updateArticles(Articles articles) {

        return ArticlesRepo.save(articles);

    }

    @Override
    public void deleteArticles(Long id) {
        ArticlesRepo.deleteById(id);
    }

    @Override
    public List<Articles> getArticless() {
        return (List<Articles>) ArticlesRepo.findAll();
    }
    public Articles addblogtoarticles(Articles articles, Long id)
    {
        Blog blog=blogRepository.findById(id).get();

     articles.setBlog(blog);
        return ArticlesRepo.save(articles);
    }
    public Set<Articles> getarticlesbyblog (Long id)
    {
        Blog blog=blogRepository.findById(id).get();
return ArticlesRepo.findAllByBlog(blog);
    }
}
