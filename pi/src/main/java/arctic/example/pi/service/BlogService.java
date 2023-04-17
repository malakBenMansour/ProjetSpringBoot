package arctic.example.pi.service;

import arctic.example.pi.entity.Blog;
import arctic.example.pi.entity.Role;

import java.util.List;

public interface BlogService {
    Blog saveBlog(Blog blog);
    Blog updateBlog(Blog blog);

    void deleteBlog(Long id);

    List<Blog> getBlogs();
}
