package arctic.example.pi.controller;

import arctic.example.pi.DTON.CountType;
import arctic.example.pi.repository.TypeRecRepository;
import arctic.example.pi.entity.TypeReclamation;
import arctic.example.pi.service.TypeRecService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/TypeRec")
public class TypeRecController {


    @Autowired
    TypeRecService typeRecService ;
    @Autowired
    private TypeRecRepository typeRecRepository;


    @PostMapping("/AddTypeRec")
    TypeReclamation addTypeReclamation(@RequestBody TypeReclamation i) {
        return typeRecService.addTypeReclamation(i);
    }

    @GetMapping("/AfficherTypeReclamation/{idType}")
    TypeReclamation retrieveTypeReclamation(@PathVariable Long idType) {
        return typeRecService.retrieveTypeReclamation(idType);
    }
    @GetMapping("/AfficherAllTypeReclamation")
    List<TypeReclamation> retrieveAllTypeReclamation() {
        return typeRecService.retrieveAllTypeReclamations();
    }

    @DeleteMapping("/DeleteTypeReclamation/{idType}")
    void deleteTypeReclamation(@PathVariable Long idType) {
        typeRecService.deleteTypeRec(idType);
    }
//    @PutMapping("/ModifierTypeReclamation")
//    TypeReclamation updateTypeReclamation(@RequestBody TypeReclamation i) {
//        return typeRecService.updateTypeReclamation(i);
//    }

    @PutMapping("/ModifierTypeReclamation/{idType}")
    public ResponseEntity<?> updateTypeReclamation(@PathVariable("idType") Long idType, @RequestBody TypeReclamation update) {
        TypeReclamation typeReclamation = typeRecRepository.findById(idType).get();
        if(typeReclamation == null) {
            return ResponseEntity.notFound().build();
        }

        typeReclamation.setDateCreation(update.getDateCreation());
        typeReclamation.setDescription(update.getDescription());
        typeReclamation.setNom(update.getNom());


        TypeReclamation savrec=typeRecService.addTypeReclamation(update);
        if(savrec == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(savrec);
    }

    @GetMapping("/AfficherByDate/{DateCreation}")
    Set<TypeReclamation> findTypeByDateCreation(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date DateCreation) {
        return typeRecService.findTypeByDateCreation(DateCreation);
    }
    @GetMapping("/countAllTypeByNom/{nom}")
    int countAllByNom(@PathVariable String nom){
        return typeRecService.countAllByNom(nom);
    }


    @GetMapping("/getTypeReclamationEntreDeuxDate/{Date1}/{Date2}")
    List<TypeReclamation> getReclamationEntreDeuxDate(@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date Date1 ,@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date Date2) {
        return typeRecService.findTypeReclamationByDateCreationBetween(Date1 , Date2);
    }

    @GetMapping("/stat")
    public List<CountType> statistque()
    {
        return typeRecService.statistque();
    }

    @GetMapping("/nbReclamationBytypeBetweenDeuxDates/{date1}/{date2}")
    List<Object[]> nbReclamationBytypeBetweenDeuxDates(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date1, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date2) {
        return typeRecService.nbReclamationBytypeBetweenDeuxDates(date1,date2);
    }
    @GetMapping("/export")
    public void exportTypeReclamations(HttpServletResponse response) throws IOException {

        // Récupérer la liste des réclamations à exporter
        List<TypeReclamation> typeReclamations = typeRecService.retrieveAllTypeReclamations();

        // Créer un nouveau workbook Excel
        Workbook workbook = new XSSFWorkbook();

        // Créer une nouvelle feuille Excel
        Sheet sheet = workbook.createSheet("TypeRec");

        // Créer la première ligne qui contient les en-têtes des colonnes
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Type");

        // Remplir les données de réclamation
        int rowNum = 1;
        for (TypeReclamation typeReclamation : typeReclamations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(typeReclamation.getIdType());
            row.createCell(1).setCellValue(typeReclamation.getNom());
            row.createCell(2).setCellValue(typeReclamation.getDescription());

        }

        // Configurer l'en-tête de réponse
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=typereclamations.xlsx");

        // Écrire le contenu du fichier Excel dans la réponse HTTP
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }


}
