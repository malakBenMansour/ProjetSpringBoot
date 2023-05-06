package arctic.example.pi.service;

import arctic.example.pi.entity.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role updateRole(Role role);

    void deleteRole(Long id);

    List<Role> getRoles();
    public Role findID(Long id);
}
