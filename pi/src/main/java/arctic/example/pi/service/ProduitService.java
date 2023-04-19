package arctic.example.pi.service;

import arctic.example.pi.entity.Categorie;
import arctic.example.pi.entity.Produit;
import arctic.example.pi.repository.CategorieRepository;
import arctic.example.pi.repository.ProduitRepository;
import com.itextpdf.text.pdf.qrcode.BitMatrix;
import com.google.zxing.BarcodeFormat;

import com.itextpdf.text.pdf.qrcode.ByteMatrix;
import com.itextpdf.text.pdf.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.io.ByteArrayOutputStream;
import java.util.Base64;




import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

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


    public Produit updateProd(Produit a) {
        return produitRepository.save(a);
    }

    public List<Produit> retrieveAllProduits() {
        List<Produit> ab = (List<Produit>) produitRepository.findAll();
        return ab;
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



}
