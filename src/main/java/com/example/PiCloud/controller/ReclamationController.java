package com.example.PiCloud.controller;

import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.Status;
import com.example.PiCloud.services.ReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/reclamation")

public class ReclamationController {
    @Autowired
    ReclamationService iRec;

    @PostMapping("/addRec")
    Reclamation addReclamation(@RequestBody Reclamation r) {
        return iRec.addReclamation(r);
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


    @GetMapping("/AfficherByNom/{nomRec}")
    Set<Reclamation> getReclamationByNom(@PathVariable String nomRec) {
        return iRec.getReclamationByNom(nomRec);
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
}

