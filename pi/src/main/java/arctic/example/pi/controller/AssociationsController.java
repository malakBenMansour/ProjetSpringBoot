package arctic.example.pi.controller;

import arctic.example.pi.entity.Associations;
import arctic.example.pi.entity.Role;
import arctic.example.pi.service.AssociationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssociationsController {
    @Autowired
    AssociationsService associationsService;

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
}
