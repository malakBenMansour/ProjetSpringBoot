package arctic.example.pi.service;


import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Reclamation;
import arctic.example.pi.entity.Statuarticle;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

public interface ArticlesService {

    Articles updateArticles(Articles articles);

    void deleteArticles(Long id);

    List<Articles> getArticless();
    public Articles addblogtoarticles(Articles articles, Long id);
    public Set<Articles> getarticlesbyblog (Long id);
    public ByteArrayInputStream articlesExport(List<Articles> article);
    public Articles saveArticles(Articles rec, Long id);
    Set<Articles> getarticle(String titre);
    public Set<Articles>  findAllOrderByTitre();
    public int nbrencours () ;
    public List<Articles> getdisable();
    public Articles findById(Long id) ;
    public Articles updateArticle(Articles articles);
    public Articles getArticleById(Long id);
    Long countArticlesByStatut(Statuarticle statut);
    //public ConnectionFactory<Facebook> facebookConnectionFactory() ;
    //public int countArticlesByStatu(Statuarticle statuarticle);



    List<Articles> findByuser(Long id);
}
