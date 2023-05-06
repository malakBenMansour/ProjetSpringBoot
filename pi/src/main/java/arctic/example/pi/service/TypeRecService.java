package arctic.example.pi.service;


import arctic.example.pi.DTO.CountType;
import arctic.example.pi.repository.TypeRecRepository;
import arctic.example.pi.entity.TypeReclamation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class TypeRecService implements ITypeRec{

    @Autowired
    TypeRecRepository typeRecRepository;

    public TypeReclamation addTypeReclamation(TypeReclamation t) {
        return typeRecRepository.save(t);
    }
    public void deleteTypeRec(Long idType) {
        typeRecRepository.deleteById(idType);

    }


    public TypeReclamation updateTypeReclamation(TypeReclamation t) {
        return typeRecRepository.save(t);
    }

    public List<TypeReclamation> retrieveAllTypeReclamations() {
        List<TypeReclamation> tr = (List<TypeReclamation>) typeRecRepository.findAll();
        return tr;
    }


    public TypeReclamation retrieveTypeReclamation(Long  idType) {
        return typeRecRepository.findById(idType).orElse(null);
    }

    public Set<TypeReclamation> findTypeByDateCreation(Date DateCreation) {
        return typeRecRepository.findTypeByDateCreation(DateCreation);
    }

    public int countAllByNom(String nom) {
        return typeRecRepository.countAllByNom(nom);
    }
    public List<TypeReclamation> findTypeReclamationByDateCreationBetween(Date date1, Date date2) {
        return typeRecRepository.findTypeReclamationByDateCreationBetween( date1,date2);
    }

   /* public List<CountType> statistque()
    {
        return typeRecRepository.statistque();
    }
*/
//me tekhdmsh
    public List<Object[]> nbReclamationBytypeBetweenDeuxDates(Date date1, Date date2) {
        return typeRecRepository.nbReclamationBytypeBetweenDeuxDates(date1,date2);
    }




}
