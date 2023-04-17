package arctic.example.pi.controller;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.service.ArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    ArticlesService ArticlesService;

    @GetMapping("/getarticles")
    public List<Articles> afficher()
    {
        return ArticlesService.getArticless();
    }

    @PostMapping("/save")
    public Articles add(@RequestBody Articles articles)
    {
        return ArticlesService.saveArticles(articles);
    }

    @PutMapping("/update")
    public Articles modifier(@RequestBody Articles articles)
    {
        return  ArticlesService.updateArticles(articles);
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
}
