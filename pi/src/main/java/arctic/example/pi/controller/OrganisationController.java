package arctic.example.pi.controller;
import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.TypeOrganisation;
import arctic.example.pi.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {
    @Autowired
    OrganisationService organisationService;

    @GetMapping("/getall")
    public List<Organisation> afficher()
    {
        return organisationService.getOrganisations();
    }

    @DeleteMapping("/delete/{id}")
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        organisationService.deleteOrganisation(id);
    }
    @PostMapping("/ajouter")
    public Organisation ajouter(@RequestBody Organisation organisation)
    {  //Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        //String userName = loggedInUser.getName();
        //User user = userRepository.findByUsername(userName).get();
        return organisationService.saveOrganisation(organisation);
    }
    @PutMapping("/update")
    public Organisation modifier(@RequestBody Organisation organisation)
    {
        return organisationService.saveOrganisation(organisation);
    }
    @PutMapping("/adduser/{id}")
    public Organisation addUserToOrganisation(@RequestBody Organisation organisation,@PathVariable(value="id") Long iduser){
        return organisationService.addUserToOrganisation(organisation,iduser);
    }

    @GetMapping("/rechercheadresse/{adresse}")
    public List<Organisation> findAllByAdresse(@PathVariable(value="adresse")String adresse)
    {
      return organisationService.findAllByAdresse(adresse);
    }

    @GetMapping("/recherchetype/{type}")
   public  List<Organisation> findAllByTypeorganisation(@PathVariable(value="type") TypeOrganisation type )
    {
        return organisationService.findAllByTypeorganisation(type);
    }
}
