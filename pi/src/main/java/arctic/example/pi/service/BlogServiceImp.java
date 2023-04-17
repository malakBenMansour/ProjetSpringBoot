package arctic.example.pi.service;

import arctic.example.pi.entity.Blog;
import arctic.example.pi.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    BlogRepository BlogRepo;
    @Override
    public Blog saveBlog(Blog Blog) {
        return BlogRepo.save(Blog);
    }

    @Override
    public Blog updateBlog(Blog Blog) {

        return BlogRepo.save(Blog);

    }

    @Override
    public void deleteBlog(Long id) {
        BlogRepo.deleteById(id);
    }

    @Override
    public List<Blog> getBlogs() {
        return (List<Blog>) BlogRepo.findAll();
    }

}
