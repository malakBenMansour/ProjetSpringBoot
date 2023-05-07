package arctic.example.pi.controller;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Reclamation;
import arctic.example.pi.entity.StatistiquesDTO;
import arctic.example.pi.entity.Statuarticle;
import arctic.example.pi.repository.ArticlesRepository;
import arctic.example.pi.service.ArticlesService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:4200/", maxAge = 3600)
@RestController
@RequestMapping("/api/articles")

public class ArticlesController {
    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;

    @Value("${spring.social.facebook.appSecret}")
    private String facebookAppSecret;
    @Autowired
    ArticlesService ArticlesService;
    @Autowired
    ArticlesRepository articlesRepository;


    @GetMapping("/getarticles")
    public List<Articles> afficher()
    {
        return ArticlesService.getArticless();
    }

    @PostMapping("/save/{id}")

    Articles addReclamation(@RequestBody Articles r,@PathVariable Long id) {
        return ArticlesService.saveArticles(r,id);
    }


   /* @PutMapping("/update/{id}")
    public ResponseEntity<Articles> updateArticle(@PathVariable Long id, @RequestBody Articles articles) {
        Articles updateart = ArticlesService.findById(id);

        updateart.setTitre(articles.getTitre());
        updateart.setContenu(articles.getContenu());
        updateart.setStatuarticle(articles.getStatuarticle());
        updateart.setDatepublication(articles.getDatepublication());


        Articles updateBoite = ArticlesService.updateArticles(updateart);
        return ResponseEntity.ok(updateBoite);


    }*/
   @PutMapping("/update/{id}")
   public ResponseEntity<?> updateArticle(@PathVariable("id") Long id, @RequestBody Articles updatedArticle) {
       Articles article = ArticlesService.getArticleById(id);
       if(article == null) {
           return ResponseEntity.notFound().build();
       }

       article.setTitre(updatedArticle.getTitre());
       article.setContenu(updatedArticle.getContenu());
       article.setDatepublication(updatedArticle.getDatepublication());
       article.setStatuarticle(updatedArticle.getStatuarticle());
       article.setBlog(updatedArticle.getBlog());

       Articles savedArticle = ArticlesService.saveArticles(article,id);
       if(savedArticle == null) {
           return ResponseEntity.badRequest().build();
       }

       return ResponseEntity.ok().body(savedArticle);
   }


    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        ArticlesService.deleteArticles(id);
    }
    @PostMapping("/saveblog/{id}")
    public Articles addblogtoarticle(@RequestBody Articles articles,@PathVariable (value = "id") Long id)
    {
        return ArticlesService.addblogtoarticles(articles,id);
    }
    @GetMapping ("/get/{id}")
    public Set<Articles> getarticleblog(@PathVariable (value = "id")Long id)
    {
        return ArticlesService.getarticlesbyblog(id);
    }
    @GetMapping("/exportpdf")
    public ResponseEntity<InputStreamResource> exportPdf() {
        List<Articles> articles = (List<Articles>) ArticlesService.getArticless();
        ByteArrayInputStream bais =  ArticlesService.articlesExport(articles);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=user.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
    @GetMapping("/tri")
    public Set<Articles> findAll()
    {
        return ArticlesService.findAllOrderByTitre();
    }


    @GetMapping("/rechercheart/{titre}")
    @ResponseBody
    public Set<Articles> rechercheart(@PathVariable("titre") String titre) {
        return ArticlesService.getarticle(titre);
    }
    @GetMapping("/sceduling")
    public int nbrencours ()
    {
        return ArticlesService.nbrencours();
    }
    @GetMapping("/{id}/facebook-share")
    public String shareOnFacebook(@PathVariable Long id) {
        Articles article = articlesRepository.findById(id).get();

        Facebook facebook = new FacebookTemplate(facebookAppId, facebookAppSecret);
        String postText = "Check out this article: " + article.getTitre() + " " +
                "http://yourapp.com/articles/" + article.getId();
        facebook.feedOperations().updateStatus(postText);

        return "redirect:/articles/" + article.getId();
    }
    @GetMapping("/statistiques")
    public ResponseEntity<Integer[]> getStatistiques() {
        Integer[] statistiques = new Integer[3];
        statistiques[0] = ArticlesService.countArticlesByStatut(Statuarticle.ACCEPTE).intValue();
        statistiques[1] = ArticlesService.countArticlesByStatut(Statuarticle.ENCOURS).intValue();
        statistiques[2] = ArticlesService.countArticlesByStatut(Statuarticle.REFUSE).intValue();
        return ResponseEntity.ok(statistiques);
    }
   /* public ResponseEntity<StatistiquesDTO> getStatistiques() {
        StatistiquesDTO statistiquesDTO = new StatistiquesDTO();
        statistiquesDTO.setNombreArticlesAccepte(ArticlesService.countArticlesByStatut(Statuarticle.ACCEPTE));
        statistiquesDTO.setNombreArticlesENcours(ArticlesService.countArticlesByStatut(Statuarticle.ENCOURS));
        statistiquesDTO.setNombreArticlesRefuse(ArticlesService.countArticlesByStatut(Statuarticle.REFUSE));
        return ResponseEntity.ok(statistiquesDTO);
    }*/



    @GetMapping("/alluser/{id}")
    public List<Articles> afficherbyuser(@PathVariable(value="id")Long id)

    {
        return ArticlesService.findByuser(id);
    }
}
