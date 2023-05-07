package arctic.example.pi.service;

import arctic.example.pi.entity.Role;
import arctic.example.pi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImp implements RoleService{

    @Autowired
    RoleRepository roleRepo;
    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role updateRole(Role role) {

       return roleRepo.save(role);

    }

    @Override
    public void deleteRole(Long id) {
     roleRepo.deleteById(id);
    }

    @Override
    public List<Role> getRoles() {
       return (List<Role>) roleRepo.findAll();
    }
}
