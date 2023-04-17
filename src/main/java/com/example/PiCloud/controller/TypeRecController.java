package com.example.PiCloud.controller;

import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.Status;
import com.example.PiCloud.entities.TypeReclamation;
import com.example.PiCloud.services.TypeRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/TypeRec")
public class TypeRecController {


    @Autowired
    TypeRecService typeRecService ;


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
    @PutMapping("/ModifierTypeReclamation")
    TypeReclamation updateTypeReclamation(@RequestBody TypeReclamation i) {
        return typeRecService.updateTypeReclamation(i);
    }

    @GetMapping("/AfficherByDate/{DateCreation}")
    Set<TypeReclamation> findTypeByDateCreation(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date DateCreation) {
        return typeRecService.findTypeByDateCreation(DateCreation);
    }
    @GetMapping("/countAllTypeByNom/{nom}")
    int countAllByNom(@PathVariable String nom){
        return typeRecService.countAllByNom(nom);
    }



}
