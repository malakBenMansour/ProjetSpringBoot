package arctic.example.pi.service;

import arctic.example.pi.entity.Associations;

import java.util.List;

public interface IAssociationService {
   Associations saveAssociation(Associations associations);
   Associations updateAssociation(Associations associations);
   void deleteAssociation(Long id);
   List<Associations> getAssociation();

}
