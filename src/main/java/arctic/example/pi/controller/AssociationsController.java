package arctic.example.pi.controller;

import arctic.example.pi.entity.Associations;
import arctic.example.pi.entity.Role;
import arctic.example.pi.repository.AssociationRepository;
import arctic.example.pi.service.AssociationsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/association")
public class AssociationsController {
    @Autowired
    AssociationsService associationsService;
  @Autowired
    AssociationRepository associationRepository;
    @GetMapping("/getAsso")
    public List<Associations> afficher()
    {
        return associationsService.getAssociation();
    }

    @PostMapping("/saveAsso")
    public Associations add(@RequestBody Associations associations)
    {
        return associationsService.saveAssociation(associations);
    }

    @PutMapping("/updateAsso")
    public Associations modifier(@RequestBody Associations associations)
    {
        return  associationsService.updateAssociation(associations);
    }
    @DeleteMapping("/deleteAss/{id}")
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        associationsService.deleteAssociation(id);
    }
   /* @GetMapping("api/pdf/generate/{id}")
    public void generatePDF(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        //String currentDateTime = dateFormatter.format(new Date(idf));

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.associationsService.export(response,id);
    }*/
   @GetMapping("/associations/pdf")
   public void generatePdf(HttpServletResponse response) throws IOException, DocumentException {
       associationsService.exportAll(response);
   }

    @GetMapping("/getAssowithoutDon")
    public List<Associations> findAssociationsWithoutDon()
{ return associationsService.findAssociationsWithoutDon();}
   /* @PostMapping("/association")
    public ResponseEntity<?> addAssociation(@ModelAttribute Associations association, @RequestParam("file") MultipartFile file) {
        try {
            associationsService.addAssociation(association, file);
            return new ResponseEntity<>("Association and image added successfully!", HttpStatus.OK);
        } catch (IOException ex) {
            return new ResponseEntity<>("Could not add association and image: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
   @PostMapping(value = "/addSponsor")
   public void addSponsor(@RequestParam("file") MultipartFile file, @RequestParam("association") String association) throws IOException {
       System.out.println("Save sponsor.............");
       Associations spons = new ObjectMapper().readValue(association, Associations.class);

       String filename = StringUtils.cleanPath(file.getOriginalFilename());
       String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
       File serverFile = new File("C:/Users/user/Desktop/integration/PICloud_Beta/src/main/webApp/Imagess/" + newFileName);

       try {
           FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
       } catch (IOException e) {
           throw new IOException("Failed to save file: " + e.getMessage());
       }

       System.out.println("Real path: " + serverFile.getAbsolutePath());

       spons.setImageFileName(newFileName);

       try {
           associationsService.saveAssociation(spons);
       } catch (Exception e) {
           FileUtils.deleteQuietly(serverFile);
           throw new IOException("Failed to save sponsor: " + e.getMessage());
       }
   }

    @GetMapping(path="/ImgAsso/{id}")
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Associations sponsor   = associationRepository.findById(id).get();
        Path imagePath = Paths.get("C:/Users/user/Desktop/integration/PICloud_Beta/src/main/webApp/Imagess/" + sponsor.getImageFileName());
        return Files.readAllBytes(imagePath);
    }

}
