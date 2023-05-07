package arctic.example.pi.service;


import arctic.example.pi.entity.Categorie;
import arctic.example.pi.repository.CategorieRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CategorieService implements Icategorie{

    @Autowired
    CategorieRepository categorieRepository;

    public Categorie addCat(Categorie c) {
        return categorieRepository.save(c);
    }

    public void deleteCat(Long idcat) {
        categorieRepository.deleteById(idcat);
    }

    public Categorie updateCat(Categorie c) {
        return categorieRepository.save(c);
    }
 public List<Categorie> retrieveAllCategorie() {
         return (List<Categorie>) categorieRepository.findAll();

    }
    @Override
    public Categorie updateArticle(Categorie categorie) {
        Categorie updateart = retrieveCat(categorie.getIdcat());
        updateart.setName(categorie.getName());
        updateart.setDescription(categorie.getDescription());

        return categorieRepository.save(updateart);
    }

    public Categorie retrieveCat(Long  idcat) {
        return categorieRepository.findById(idcat).orElse(null);
    }


    public ByteArrayInputStream blogExport(List<Categorie> Blog) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();
           Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Paragraph para = new Paragraph("Liste des Categories", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);
            PdfPTable table = new PdfPTable(2);


            Stream.of("Nom", "Description").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.BLUE);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);

            });

            for (Categorie categorie : Blog) {
                PdfPCell NomCell = new PdfPCell(new Phrase(categorie.getName()));
                NomCell.setPaddingLeft(1);
                NomCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                NomCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(NomCell);


                PdfPCell descriptionCell = new PdfPCell(new Phrase(categorie.getDescription()));
                descriptionCell.setPaddingLeft(1);
                descriptionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                descriptionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(descriptionCell);




            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

}
