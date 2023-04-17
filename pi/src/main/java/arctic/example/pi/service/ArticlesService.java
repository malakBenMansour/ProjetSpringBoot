package arctic.example.pi.service;


import arctic.example.pi.entity.Articles;

import java.util.List;
import java.util.Set;

public interface ArticlesService {
    Articles saveArticles(Articles articles);
    Articles updateArticles(Articles articles);

    void deleteArticles(Long id);

    List<Articles> getArticless();
    public Articles addblogtoarticles(Articles articles, Long id);
    public Set<Articles> getarticlesbyblog (Long id);
}
