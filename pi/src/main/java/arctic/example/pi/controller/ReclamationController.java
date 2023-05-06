package arctic.example.pi.controller;

import arctic.example.pi.entity.TypeReclamation;
import arctic.example.pi.service.ReclamationService;
import arctic.example.pi.entity.Reclamation;
import arctic.example.pi.entity.Status;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reclamation")

public class ReclamationController {
    @Autowired
    ReclamationService iRec;

    @PostMapping("/addRec/{id}")
    Reclamation addReclamation(@RequestBody Reclamation r,@PathVariable Long id) {
        return iRec.addReclamation(r,id);
    }
    @GetMapping("/AfficherRec/{idRec}")
    Reclamation retrieveReclamation(@PathVariable Long idRec) {
        return iRec.retrieveReclamation(idRec);
    }
    @GetMapping("/AfficherAllRec")
    List<Reclamation> retrieveReclamation() {
        return iRec.retrieveAllReclamations();
    }

    @DeleteMapping("/DeleteRec/{idRec}")
    void deleteRec(@PathVariable Long idRec) {
        iRec.deleteReclamation(idRec);
    }
    @PutMapping("/ModifierRec")
    Reclamation updateReclamation(@RequestBody Reclamation rec) {
        return iRec.updateReclamation(rec);
    }



    @PutMapping("/activer")
    public void activateUser(@RequestBody Reclamation user)
    {
         iRec.activer(user);
    }


    @GetMapping("/AfficherByNom/{nomRec}")
    Set<Reclamation> getReclamationByNom(@PathVariable String nomRec) {
        return iRec.getReclamationByNom(nomRec);
    }

    @GetMapping("/AfficherById/{idRec}")
    Set<Reclamation> getReclamationByIdRec(@PathVariable Long idRec) {
        return iRec.findByIdRec(idRec);
    }

    @GetMapping("/AfficherByDate/{DateCreation}")
    Set<Reclamation> getReclamationByDateCreation(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  Date DateCreation) {
        return iRec.getReclamationByDateCreation(DateCreation);
    }


    @GetMapping("/getReclamationEntreDeuxDate/{Date1}/{Date2}")
    List<Reclamation> getReclamationEntreDeuxDate(@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date Date1 ,@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date Date2) {
        return iRec.getReclamationEntreDeuxDate(Date1 , Date2);
    }

    @PutMapping("/modifierPriority/{id}/{priorite}")
    public Reclamation modifierPriorite(@PathVariable Long id, @PathVariable String priorite) {
        return  iRec.modifierPriorite(id, priorite);
    }

    @GetMapping("/tri-par-priorite")
    public List<Reclamation> trierReclamationsParPriorite() {
        return iRec.trierReclamationsParPriorite();

    }
    @GetMapping("/getAllOrderedByNomASC")
    List<Reclamation> getAllOrderedByNomASC(){
        return iRec.getAllOrderedByNomASC();
    }
    @GetMapping("/countAllByStatus/{status}")
    int countAllByStatus(@PathVariable Status status){
        return iRec.countAllByStatus(status);
    }

    @GetMapping("/AfficherbyStatusOrderbyDateCreation/{status}")
    List<Reclamation> findByStatusOrderByDateCreationDesc(@PathVariable Status status) {
        return iRec.findByStatusOrderByDateCreationDesc(status);
    }
    @PutMapping("/asseignRecToTypeRec/{idRec}/{idType}")
    public void asseignRecToTypeRec(@PathVariable Long idRec,@PathVariable Long idType) {
        iRec.asseignRecToTypeRec(idRec,idType);
    }


    @GetMapping("/retrieveReclamationsByType/{idType}")
    public List<Reclamation> retrieveReclamationsByType(@PathVariable Long idType) {
        return iRec.retrieveReclamationsByType(idType);
    }

    @GetMapping("/maxClaims")
    public TypeReclamation getTypeWithMaxClaims() {
        return iRec.getTypeWithMaxClaims();
    }

    @GetMapping("/export")
    public void exportReclamations(HttpServletResponse response) throws IOException {

        // Récupérer la liste des réclamations à exporter
        List<Reclamation> reclamations = iRec.retrieveAllReclamations();

        // Créer un nouveau workbook Excel
        Workbook workbook = new XSSFWorkbook();

        // Créer une nouvelle feuille Excel
        Sheet sheet = workbook.createSheet("Reclamations");

        // Créer la première ligne qui contient les en-têtes des colonnes
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Description");
        headerRow.createCell(3).setCellValue("Type");
        headerRow.createCell(4).setCellValue("Priority");
        headerRow.createCell(5).setCellValue("numTel");
        headerRow.createCell(6).setCellValue("date de creation");

        // Remplir les données de réclamation
        int rowNum = 1;
        for (Reclamation reclamation : reclamations) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(reclamation.getIdRec());
            row.createCell(1).setCellValue(reclamation.getNomRec());
            row.createCell(2).setCellValue(reclamation.getDescription());
            row.createCell(3).setCellValue(reclamation.getTypeReclamation().getNom());
            row.createCell(4).setCellValue(reclamation.getPriority());
            row.createCell(5).setCellValue(reclamation.getNumTel());
            row.createCell(6).setCellValue(reclamation.getDateCreation());
        row.createCell(7).setCellValue(reclamation.getStatus().ordinal());
        }

        // Configurer l'en-tête de réponse
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reclamations.xlsx");

        // Écrire le contenu du fichier Excel dans la réponse HTTP
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}

