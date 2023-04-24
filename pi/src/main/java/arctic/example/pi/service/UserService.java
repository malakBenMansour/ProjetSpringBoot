package arctic.example.pi.service;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.ERole;
import arctic.example.pi.entity.User;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

public interface UserService {

    // CRUD
    List<User> getUsers();
    User saveUser(User user);
    void deleteUser(Long id);

    User updateUser(User user);

    // service avancés
    User addRoleToUser(String username, ERole roleName);
    User getUser(String username);
    User getUserByMail(String mail);
    User activateUser(User user);
    List<User> retrieveUserByState(boolean stateUser);
    List<User> retrieveUserByAddress(String adressUser);
    void updatePassword(String emailUser, String newPassword);
    List<User> findAllByOrderBOrderByRolesDesc();
    List<CountType> statistque();
    public  ByteArrayInputStream userExport(List<User> users);
    public void forgotpass(String emailuser);
    User addOrganisationToUser(String username, Long id);
    User desactivateUser(User user1);
    public List<User> searchh(String s);
    public boolean ifEmailExist(String email);
    public     List<CountType> statistqueAge();
    public List<User> getdisable();
}
