package arctic.example.pi.controller;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.UserRepository;
import arctic.example.pi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepo;

    @GetMapping("/allUsers")
    public List<User> retrieveAllUsers() {
        List<User> List = userService.getUsers();
        return List;

    }
    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        return userService.saveUser(user);

    }

    @PutMapping("/modifUser")
    public User updateUser(@RequestBody User p) {
        return userService.updateUser(p);
    }
    @DeleteMapping("/deleteUser/{id}")
    public void DeleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    // partie service avancés

    //  ajout role to user


    @PutMapping("/add/{user}/{role}")
    public User addRoletoUser(@PathVariable String user, @PathVariable ERole role) {
        return  userService.addRoleToUser(user,role);
    }


    //RECHERCHE
    @GetMapping("/retrieveUserByState/{state}")
    @ResponseBody
    public List<User> retrieveUserByState(@PathVariable("state") boolean stateUser) {
        return userService.retrieveUserByState(stateUser);
    }
    @GetMapping("/userbyusername")
    @ResponseBody
    public List<User> retrieveuserbynom(@RequestParam String username) {
        return (List<User>) userService.getUser(username);
    }

    @GetMapping("/userbyemail")
    @ResponseBody
    public User retrieveuserbyemail(@RequestParam String email) {
        return  userService.getUserByMail(email);
    }

    // Activation User
    @PutMapping("/activateUser")
    public User activateUser(@RequestBody User user)
    {
        return userService.activateUser(user);
    }

    // desactiver User


    // Modifier Password
    @PutMapping("/updatepassword/{emailUser}/{password}/{cpassword}")
    void updatePassword(@PathVariable("emailUser") String emailUser, @PathVariable("password") String newPassword,
                        @PathVariable("cpassword") String confirmPassword) {
        userService.updatePassword(emailUser, newPassword, confirmPassword);
    }
    @GetMapping("/tri")
    public List<User> findAll()
    {
        return userService.findAllByOrderBOrderByRolesDesc();
    }
    @GetMapping("/stat")
    public List<CountType> statistque()
    {
        return userService.statistque();
    }

    @GetMapping("/exportpdf")
    public ResponseEntity<InputStreamResource> exportPdf() {
        List<User> users = (List<User>) userService.getUsers();
        ByteArrayInputStream bais = userService.userExport(users);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=user.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }

    // forget password API Mail
    @GetMapping("/sendme/{emailUser}")
    public void forgotpass(@PathVariable("emailUser") String emailUser) {
        userService.forgotpass(emailUser);
    }

    // add Organisation to User

    @PutMapping("/addorganisation/{username}/{id}")
    public User addOrganisationToUser(@PathVariable(value="username") String username,@PathVariable(value="id") Long id){
     return userService.addOrganisationToUser(username,id);
    }
}
