package arctic.example.pi.controller;

import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.UserRepository;
import arctic.example.pi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<User> retrieveuserbyemail(@RequestParam String email) {
        return (List<User>) userService.getUserByMail(email);
    }

    // Activation User
    @PutMapping("/activateUser")
    public User activateUser(@RequestBody User user)
    {
        return userService.activateUser(user);
    }

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
}
