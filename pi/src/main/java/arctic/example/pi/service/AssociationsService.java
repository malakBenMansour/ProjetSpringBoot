package arctic.example.pi.service;

import arctic.example.pi.entity.Associations;
import arctic.example.pi.repository.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssociationsService implements IAssociationService{
    @Autowired
    AssociationRepository associationRepository;
    @Override
    public Associations saveAssociation(Associations associations) {
        return associationRepository.save(associations);
    }

    @Override
    public Associations updateAssociation(Associations associations) {
        return associationRepository.save(associations);
    }

    @Override
    public void deleteAssociation(Long id) {
        associationRepository.deleteById(id);

    }

    @Override
    public List<Associations> getAssociation() {
        return associationRepository.findAll();
    }
}
