package arctic.example.pi.service;

import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.Role;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.RoleRepository;
import arctic.example.pi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    @Autowired
    UserRepository userRepo;
@Autowired
    RoleRepository roleRepo;

    @Override
    public List<User> getUsers() {

        return (List<User>) userRepo.findAll();
    }

    @Override
    public User saveUser(User user) {
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepo.save(user);
    }
//service avancés
    @Override
    public User addRoleToUser(String username, ERole roleName) {


            User user = userRepo.findByUsername(username);
            Role role = roleRepo.findByName(roleName);
            user.getRoles().add(role);
            return userRepo.save((user));

    }
  // recherche
    @Override
    public User getUser(String username) {

        return userRepo.findByUsername(username);
    }

    @Override
    public User getUserByMail(String mail) {
        return this.userRepo.findByEmail(mail);
    }

    @Override
    public List<User> retrieveUserByState(boolean stateUser) {
        return (List<User>) userRepo.findByStateuser(stateUser);
    }
    @Override
    public List<User> retrieveUserByAddress(String adressUser) {
        return userRepo.findByAddress(adressUser);
    }
//activation user
    @Override
    public User activateUser(User user1)
    { User user=userRepo.findByUsername(user1.getUsername());
        if(user.isStateuser()==false)
        {
            user.setStateuser(true);
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEmail(user1.getEmail());
        user.setNom(user1.getNom());
        user.setPrenom(user1.getPrenom());
        user.setDatenaissance(user1.getDatenaissance());
        user.setTel(user1.getTel());
        user.setAddress(user1.getAddress());
        return userRepo.save(user);
    }

    // update password
    public void updatePassword(String emailUser, String newPassword, String confirmPassword) {
       User u = userRepo.findByEmail(emailUser);
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        String encodedConfirmPassword = passwordEncoder.encode(confirmPassword);
        u.setPassword(encodedPassword);
        u.setConfirmpassworduser(encodedConfirmPassword);

        userRepo.save(u);
    }
    public List<User>  findAllByOrderBOrderByRolesDesc()
    {
        return userRepo.findAllByOrderByRolesDesc();
    }

}
