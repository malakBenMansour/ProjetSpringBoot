package arctic.example.pi.service;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Set;

public interface BlogService {
    Blog saveBlog(Blog blog);
    Blog updateBlog(Blog blog);

    void deleteBlog(Long id);

    List<Blog> getBlogs();
    public Set<Blog> findAllOrderByTitre();
    public ByteArrayInputStream blogExport(List<Blog> Blog);
    Set<Blog> getblog(String titre);
    public Blog getBlogById(Long id);
    //public Articles updateArticle(Articles articles);

    List<Blog> findByuser(Long id);

}
