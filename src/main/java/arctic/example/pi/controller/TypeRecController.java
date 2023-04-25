package arctic.example.pi.controller;

import arctic.example.pi.repository.TypeRecRepository;
import arctic.example.pi.entity.TypeReclamation;
import arctic.example.pi.service.TypeRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/getTypeReclamationEntreDeuxDate/{Date1}/{Date2}")
    List<TypeReclamation> getReclamationEntreDeuxDate(@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date Date1 ,@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date Date2) {
        return typeRecService.findTypeReclamationByDateCreationBetween(Date1 , Date2);
    }



}
