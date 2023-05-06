package arctic.example.pi.service;

import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.TypeOrganisation;
import arctic.example.pi.entity.User;
import arctic.example.pi.repository.OrganisationRepository;
import arctic.example.pi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrganisationServiceImp implements OrganisationService {
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    UserRepository userRepository;


    @Override
    public Organisation saveOrganisation(Organisation organisation) {

      return organisationRepository.save(organisation);
    }
    @Override
    public Organisation findById(Long id) {
        return organisationRepository.findById(id).orElse(null);
    }
    @Override
    public Organisation updateOrganisation(Organisation organisation) {

        return organisationRepository.save(organisation);
    }

    @Override
    public void deleteOrganisation(Long id) {
     organisationRepository.deleteById(id);
    }

    @Override
    public List<Organisation> getOrganisations() {
       return (List<Organisation>)organisationRepository.findAll();
    }

    @Override
    public Organisation addUserToOrganisation( Organisation organisation,Long iduser) {
        User user=userRepository.findById(iduser).get();
        //Organisation organisation=organisationRepository.findById(idorg).get();
        user.setOrganisation(organisation);
        //organisation.getUsers().add(user);
        System.out.println("goood");
        return organisationRepository.save(organisation);
    }

    @Override
    public List<Organisation> findAllByAdresse(String adresse) {
        return organisationRepository.findAllByAdresse(adresse);
    }

    @Override
    public List<Organisation> findAllByTypeorganisation(TypeOrganisation type) {
        return organisationRepository.findAllByTypeorganisation(type);
    }

    @Override
    public Organisation findOrgById(Long id)
    {         User user=userRepository.findById(id).get();
        return organisationRepository.findByUsers(user);
    }
}
