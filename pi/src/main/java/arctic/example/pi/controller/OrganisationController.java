package arctic.example.pi.controller;
import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.TypeOrganisation;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.UserRepository;
import arctic.example.pi.service.OrganisationService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/organisation")
@CrossOrigin(origins = "http://localhost:4200")
public class OrganisationController {
    @Autowired
    OrganisationService organisationService;
    @Autowired
    UserRepository userRepository;

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
    public Organisation ajouter(@RequestBody Organisation organisation
                               // @RequestParam("org") String org,
                                //@RequestParam("image") MultipartFile file
   ) throws IOException {
      /// Organisation organisation = new Gson().fromJson(org,Organisation.class);


      // String image=file.getOriginalFilename();
       //String path="C://wamp64/www/img";

       //byte[] bytes = image.getBytes();
       //int image2=bytes.toString().hashCode();
       //Files.copy(file.getInputStream(), Paths.get(path+ File.separator+image2+".jpg"));
       //organisation.setImage(""+image2);
         /*Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
//        String id = loggedInUser.getName();
//        User user = userRepository.findByName(id);
//        organisation.getUsers().add(user);*/


  return organisationService.saveOrganisation(organisation);
    }


   /* @PostMapping("/ajouter")
    public Organisation ajouter(
            @RequestParam("org") String org,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        Organisation organisation = new Gson().fromJson(org,Organisation.class);


        String image=file.getOriginalFilename();
        String path="C://wamp64/www/img";

        byte[] bytes = image.getBytes();
        int image2=bytes.toString().hashCode();
        Files.copy(file.getInputStream(), Paths.get(path+ File.separator+image2+".jpg"));

        organisation.setImage(""+image2);

        return organisationService.saveOrganisation(organisation);
    }*/
    @PutMapping("/update")
    public Organisation modifier(@RequestBody Organisation organisation)
    {
        return organisationService.saveOrganisation(organisation);
    }
    @PutMapping("/adduser/{id}")
    public void addUserToOrganisation(@RequestBody Organisation organisation,@PathVariable(value="id") Long id){
         organisationService.addUserToOrganisation(organisation,id);
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

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Organisation> update(@PathVariable Long id, @RequestBody Organisation user) {
        Organisation updateUser = organisationService.findById(id);

        updateUser.setNom(user.getNom());
        updateUser.setAdresse(user.getAdresse());

        Organisation updateBoite = organisationService.updateOrganisation(updateUser);
        return ResponseEntity.ok(updateBoite);


    }

    @GetMapping("/getorg/{id}")
    public Organisation findOrg(@PathVariable(value = "id") Long id)
    {
        return organisationService.findOrgById(id);
    }
}
