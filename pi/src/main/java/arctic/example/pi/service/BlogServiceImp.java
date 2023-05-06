package arctic.example.pi.service;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;
import arctic.example.pi.repository.BlogRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    BlogRepository BlogRepo;
    @Override
    public Blog saveBlog(Blog Blog) {
        return BlogRepo.save(Blog);
    }
    @Override
    public Blog getBlogById(Long id) {
        Optional<Blog> blog = BlogRepo.findById(id);
        if (blog.isPresent()) {
            return blog.get();
        } else {
            throw new ResourceNotFoundException("Blog", "id");
        }
    }
    @Override
    public Blog updateBlog(Blog blog) {

        Blog updateblog = getBlogById(blog.getId());
        updateblog.setTitre(blog.getTitre());
        updateblog.setDescription(blog.getDescription());
        updateblog.setImage(blog.getImage());
        updateblog.setTypeblog(blog.getTypeblog());
        return BlogRepo.save(updateblog);

    }

    @Override
    public void deleteBlog(Long id) {
        BlogRepo.deleteById(id);
    }

    @Override
    public List<Blog> getBlogs() {
        return (List<Blog>) BlogRepo.findAll();
    }
    @Override
    public Set<Blog> findAllOrderByTitre()
    {
        return BlogRepo.triblog();
    }
    public ByteArrayInputStream blogExport(List<Blog> Blog) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Liste des Blog", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(3);


            Stream.of("Type", "Description", "Titre").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.BLUE);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (Blog blog : Blog) {
                PdfPCell TitreCell = new PdfPCell(new Phrase(blog.getTitre()));
                TitreCell.setPaddingLeft(1);
                TitreCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                TitreCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(TitreCell);





                PdfPCell ContenuCell = new PdfPCell(new Phrase(blog.getDescription()));
                ContenuCell.setPaddingLeft(1);
                ContenuCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                ContenuCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(ContenuCell);
                PdfPCell statuCell = new PdfPCell(new Phrase(blog.getDescription()));
                statuCell.setPaddingLeft(1);
                statuCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                statuCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(statuCell);


            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
    public Set<Blog> getblog(String titre) {

        return BlogRepo.findByTitre(titre);
    }
}
