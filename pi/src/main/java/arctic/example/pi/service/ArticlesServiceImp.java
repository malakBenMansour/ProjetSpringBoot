package arctic.example.pi.service;

import arctic.example.pi.entity.Articles;
import arctic.example.pi.entity.Blog;
import arctic.example.pi.entity.Statuarticle;
import arctic.example.pi.repository.ArticlesRepository;
import arctic.example.pi.repository.BlogRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.stereotype.Service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service

public class ArticlesServiceImp implements ArticlesService {
    @Value("${spring.social.facebook.appId}")
    private String facebookAppId;

    @Value("${spring.social.facebook.appSecret}")
    private String facebookAppSecret;
    @Autowired
    ArticlesRepository ArticlesRepo;
    @Autowired
    BlogRepository blogRepository;
    /*@Override
    public Articles saveArticles(Articles articles) {
        return ArticlesRepo.save(articles);
    }*/
    @Override
    public Articles saveArticles(Articles rec, Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        rec.setBlog(blog);
        return ArticlesRepo.save(rec);
    }
    @Override
    public Articles updateArticles(Articles articles) {

        return ArticlesRepo.save(articles);

    }

    @Override
    public void deleteArticles(Long id) {
        ArticlesRepo.deleteById(id);
    }

    @Override
    public List<Articles> getArticless() {
        return (List<Articles>) ArticlesRepo.findAll();
    }
    public Articles addblogtoarticles(Articles articles, Long id)
    {
        Blog blog=blogRepository.findById(id).get();

     articles.setBlog(blog);
        return ArticlesRepo.save(articles);
    }
    public Set<Articles> getarticlesbyblog (Long id)
    {
        Blog blog=blogRepository.findById(id).get();
return ArticlesRepo.findAllByBlog(blog);
    }
    public ByteArrayInputStream articlesExport(List<Articles> article) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Liste des Articles", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(4);


            Stream.of("Titre", "DatePublication", "Contenu", "Statu").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.BLUE);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (Articles articles : article) {
                PdfPCell TitreCell = new PdfPCell(new Phrase(articles.getTitre()));
                TitreCell.setPaddingLeft(1);
                TitreCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                TitreCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(TitreCell);


                PdfPCell datepubCell = new PdfPCell(new Phrase(String.valueOf(articles.getDatepublication())));
                datepubCell.setPaddingLeft(1);
                datepubCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                datepubCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(datepubCell);


                PdfPCell ContenuCell = new PdfPCell(new Phrase(articles.getContenu()));
                ContenuCell.setPaddingLeft(1);
                ContenuCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                ContenuCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(ContenuCell);
                PdfPCell statuCell = new PdfPCell(new Phrase(articles.getStatuarticle().toString()));
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
    @Override
    public Set<Articles> getarticle(String titre) {

        return ArticlesRepo.findByTitre(titre);
    }
  @Override
    public Set<Articles>  findAllOrderByTitre()
    {
        return ArticlesRepo.triarticles();
    }
    @Override
    @Scheduled(fixedRate = 5000)
    //@Scheduled(cron = "0 0 8 * * MON") // Run every Monday at 8am
    public int nbrencours () {
     int count ;
     count = ArticlesRepo.nbr();
        return count ;

    }
    public Articles findById(Long id) {
        return ArticlesRepo.findById(id).orElse(null);
    }

    @Override
    public Articles getArticleById(Long id) {
        Optional<Articles> article = ArticlesRepo.findById(id);
        if (article.isPresent()) {
            return article.get();
        } else {
            throw new ResourceNotFoundException("Article", "id");
        }
    }
    @Override
    public Articles updateArticle(Articles articles) {
        Articles updateart = getArticleById(articles.getId());
        updateart.setTitre(articles.getTitre());
        updateart.setContenu(articles.getContenu());
        updateart.setStatuarticle(articles.getStatuarticle());
        updateart.setDatepublication(articles.getDatepublication());
        updateart.setBlog(articles.getBlog());
        return ArticlesRepo.save(updateart);
    }
    public List<Articles> getdisable(){
        return getdisable();
    }
    @Override
    public Long countArticlesByStatut(Statuarticle statut) {
        return ArticlesRepo.countArticlesByStatut(statut);
    }
}
