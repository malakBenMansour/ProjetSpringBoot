package arctic.example.pi.controller;

import arctic.example.pi.entity.Blog;
import arctic.example.pi.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogCntroller {
    @Autowired
    BlogService BlogService;

    @GetMapping("/getBlogs")
    public List<Blog> afficher()
    {
        return BlogService.getBlogs();
    }

    @PostMapping("/save")
    public Blog add(@RequestBody Blog blog)
    {
        return BlogService.saveBlog(blog);
    }

    @PutMapping("/update")
    public Blog modifier(@RequestBody Blog blog)
    {
        return  BlogService.updateBlog(blog);
    }
    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        BlogService.deleteBlog(id);
    }
}
