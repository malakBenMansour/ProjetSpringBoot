package arctic.example.pi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import arctic.example.pi.entity.Produit;
import arctic.example.pi.repository.ProduitRepository;
import arctic.example.pi.service.QrCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.zxing.WriterException;

@RestController
public class QRCodeController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/produit/{id}/qrcode")
    public void generateQRCodeImage(@PathVariable(value = "id") Long idProduit, HttpServletResponse response) throws IOException, WriterException {
        Produit produit = produitRepository.findById(idProduit).get();
        // Create QR code text
        Map<String, Object> qrcodeData = new HashMap<>();
        qrcodeData.put("name", produit.getName());
        qrcodeData.put("description", produit.getDescription());
        qrcodeData.put("price", produit.getPrice());
        qrcodeData.put("dateExp", produit.getDateExp());
      //  qrcodeData.put("categorie", produit.getCategorie().getName());

        ObjectMapper objectMapper = new ObjectMapper();
        String qrcodeText = objectMapper.writeValueAsString(qrcodeData);

        // Generate QR code image
        byte[] qrcodeImage = QrCodeService.generateQRCodeImage(qrcodeText, 350, 350);

        // Set response headers
        response.setContentType("image/png");
        response.setContentLength(qrcodeImage.length);
        response.setHeader("Content-Disposition", "attachment; filename=\"qrcode.png\"");

        // Write image data to response output stream
        response.getOutputStream().write(qrcodeImage);
        response.getOutputStream().flush();
    }
}
