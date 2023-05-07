package arctic.example.pi.service;

import arctic.example.pi.entity.Categorie;
import arctic.example.pi.entity.Etatprod;
import arctic.example.pi.entity.Produit;
import arctic.example.pi.repository.CategorieRepository;
import arctic.example.pi.repository.ProduitRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.google.zxing.BarcodeFormat;

import com.itextpdf.text.pdf.qrcode.ByteMatrix;
import com.itextpdf.text.pdf.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;




import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class ProduitService implements Iproduit{
    @Autowired
    ProduitRepository produitRepository ;
    @Autowired
    CategorieRepository categorieRepository ;



    public Produit addProd(Produit a) {

        return produitRepository.save(a);
    }
    public void deleteProd(Long id) {
        produitRepository.deleteById(id);

    }
    public Produit changerEtat(Produit produit){
       Produit p=produitRepository.findById(produit.getId()).get();
       if(p.getEtat()== Etatprod.horstock)
       {
           p.setEtat(Etatprod.stock);
       }

        else if(p.getEtat()== Etatprod.stock)
        {
            p.setEtat(Etatprod.horstock);
        }

        return produitRepository.save(p);
    }
    public Produit addblogtoarticles(Produit produit, Long id)
    {
        Categorie categorie=categorieRepository.findById(id).get();

        produit.setCategorie(categorie);
        return produitRepository.save(produit);
    }







    public Produit updateProd(Produit a) {
        return produitRepository.save(a);
    }

    public List<Produit> retrieveAllProduits() {
        return (List<Produit>) produitRepository.findAll();
    }


    public Produit retrieveProd(Long  id) {
        return produitRepository.findById(id).orElse(null);
    }

    public Set<Produit> findByName(String name){
        return produitRepository.findByName(name);
    }

    public Set<Produit> findByDateExpBetween(Date date1 , Date date2){
        return produitRepository.findByDateExpBetween(date1 , date2);
    }


    @Override
    public void assignCategorieToProduit(Long id, Long idcat) {
        Produit produit = produitRepository.findById(id).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        Categorie categorie = categorieRepository.findById(idcat).orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        produit.setCategorie(categorie);
        produitRepository.save(produit);
    }



    @Override
    public Produit ajouterProduitt(Produit produit, Long idcat) {
        Categorie categorie = categorieRepository.findById(idcat).orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        produit.setCategorie(categorie);
        return  produitRepository.save(produit);
    }


    public int countProduitsByCategorie(Long idcat) {
        return produitRepository.countProduitsByCategorie(idcat);
    }

    @Override
    public Produit updateArticle(Produit produit) {
        Produit updateart = retrieveProd(produit.getId());
        updateart.setName(produit.getName());
        updateart.setDescription(produit.getDescription());
        updateart.setPrice(produit.getPrice());
        updateart.setCategorie(produit.getCategorie());
        updateart.setDateExp(produit.getDateExp());

        return produitRepository.save(updateart);
    }
    public ByteArrayInputStream blogExport(List<Produit> Produit) {
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


            Stream.of("Nom", "Description" , " Date_exp" , "Price" , "idb").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.BLUE);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);

            });

            for (Produit produit : Produit) {
                PdfPCell NomCell = new PdfPCell(new Phrase(produit.getName()));
                NomCell.setPaddingLeft(1);
                NomCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                NomCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(NomCell);


                PdfPCell descriptionCell = new PdfPCell(new Phrase(produit.getDescription()));
                descriptionCell.setPaddingLeft(1);
                descriptionCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                descriptionCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(descriptionCell);

                PdfPCell date_expCell = new PdfPCell(new Phrase(String.valueOf(produit.getDateExp())));
                date_expCell.setPaddingLeft(1);
                date_expCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                date_expCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(date_expCell);

                PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(produit.getPrice())));
                priceCell.setPaddingLeft(1);
                priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                priceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(priceCell);

                PdfPCell idcatCell = new PdfPCell(new Phrase(String.valueOf(produit.getCategorie())));
                idcatCell.setPaddingLeft(1);
                idcatCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idcatCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(idcatCell);



            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}
