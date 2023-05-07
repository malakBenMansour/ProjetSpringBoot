package arctic.example.pi.controller;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;
import arctic.example.pi.entity.Reclamation;
import arctic.example.pi.service.BlogService;
import arctic.example.pi.service.IEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BlogCntroller {
    @Autowired
    BlogService BlogService;
    @Autowired
    IEmailService emailService;

    @GetMapping("/getBlogs")
    public List<Blog> afficher()
    {
        return BlogService.getBlogs();
    }

    @PostMapping("/save")
    public Blog add(@RequestBody Blog blog)
    {
        //emailService.sendSimpleMail(blog);
        return BlogService.saveBlog(blog);
    }
   /*@PostMapping(value = "/save")
   public void addSponsor(@RequestParam("file") MultipartFile file, @RequestParam("blog") String blog) throws IOException {
       System.out.println("Save event.............");
       Blog bl = new ObjectMapper().readValue(blog, Blog.class);

       String filename = StringUtils.cleanPath(file.getOriginalFilename());
       String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
       File serverFile = new File("C:/Users/moham/Desktop/PICloud_Betapi/src/main/webapp/Imagess/" + newFileName);

       try {
           FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
       } catch (IOException e) {
           throw new IOException("Failed to save file: " + e.getMessage());
       }

       System.out.println("Real path: " + serverFile.getAbsolutePath());

       bl.setImage(newFileName);

       try {
           BlogService.saveBlog(bl);
       } catch (Exception e) {
           FileUtils.deleteQuietly(serverFile);
           throw new IOException("Failed to save blog: " + e.getMessage());
       }
   }*/


    @GetMapping(path="/imgblog/{id}")
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Blog blog   =BlogService.getBlogById(id);
        Path imagePath = Paths.get("C:/Users/moham/Desktop/PICloud_Betapi/src/main/webapp/Imagess/" + blog.getImage());
        return Files.readAllBytes(imagePath);
    }
    /*@PutMapping("/update")
    public Blog modifier(@RequestBody Blog blog)
    {
        return  BlogService.updateBlog(blog);
    }*/
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable("id") Long id, @RequestBody Blog updatedBlog) {
        Blog blog = BlogService.getBlogById(id);
        if(blog == null) {
            return ResponseEntity.notFound().build();
        }

        blog.setTitre(updatedBlog.getTitre());
        blog.setDescription(updatedBlog.getDescription());
        blog.setImage(updatedBlog.getImage());
        blog.setTypeblog(updatedBlog.getTypeblog());

        Blog savedBlog = BlogService.saveBlog(blog);
        if(savedBlog == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(savedBlog);
    }
    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        BlogService.deleteBlog(id);
    }
    @GetMapping("/tri")
    public Set<Blog> findAll()
    {
        return BlogService.findAllOrderByTitre();
    }
    @GetMapping("/exportpdfblog")
    public ResponseEntity<InputStreamResource> exportPdf() {
        List<Blog> blog = (List<Blog>) BlogService.getBlogs();
        ByteArrayInputStream bais =  BlogService.blogExport(blog);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=user.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
    @GetMapping("/rechercheblog/{titre}")
    @ResponseBody
    public Set<Blog> rechercheblog(@PathVariable("titre") String titre) {
        return BlogService.getblog(titre);
    }


    @GetMapping("/alluser/{id}")
    public List<Blog> afficherbyuser(@PathVariable(value="id")Long id)

    {
        return BlogService.findByuser(id);
    }
}
