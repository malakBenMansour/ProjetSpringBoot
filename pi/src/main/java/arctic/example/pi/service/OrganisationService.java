package arctic.example.pi.service;

import arctic.example.pi.entity.Organisation;
import arctic.example.pi.entity.TypeOrganisation;

import java.util.List;

public interface OrganisationService {
    Organisation saveOrganisation(Organisation organisation);
    Organisation updateOrganisation(Organisation organisation);

    void deleteOrganisation(Long id);

    List<Organisation> getOrganisations();
    // service avancé
    public Organisation addUserToOrganisation( Organisation organisation,Long iduser);
    List<Organisation> findAllByAdresse(String adresse);
    List<Organisation> findAllByTypeorganisation(TypeOrganisation type);
    public Organisation findById(Long id);
    public Organisation findOrgById(Long id);
}
