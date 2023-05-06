package arctic.example.pi.service;


import arctic.example.pi.entity.*;
import arctic.example.pi.repository.ReclamationRepository;
import arctic.example.pi.repository.TypeRecRepository;
import arctic.example.pi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Service
public class ReclamationService implements IReclamation {

    @Autowired
    ReclamationRepository reclamationRepository ;
    TypeRecRepository typeRecRepository ;
    TypeRecService typeRecService;
    @Autowired
    UserRepository userRepository;


//    public Reclamation addReclamation(Reclamation rec) {
//        return reclamationRepository.save(rec);
//    }
public Reclamation addReclamation(Reclamation rec, Long idType) {
    TypeReclamation typeReclamation =  typeRecRepository.findById(idType).orElse(null);
    rec.setTypeReclamation(typeReclamation);
    return reclamationRepository.save(rec);
}
    public void deleteReclamation(Long idRec) {
        reclamationRepository.deleteById(idRec);

    }


    public Reclamation updateReclamation(Reclamation rec) {
        return reclamationRepository.save(rec);
    }

    public List<Reclamation> retrieveAllReclamations() {
        List<Reclamation> rec = (List<Reclamation>) reclamationRepository.findAll();
        return rec;
    }


    public Reclamation retrieveReclamation(Long  idRec) {
        return reclamationRepository.findById(idRec).orElse(null);
    }

    @Override
    public Set<Reclamation> getReclamationByNom(String nomRec) {
        return null;
    }
    @Override
    public Set<Reclamation> getReclamationByDateCreation(Date DateCreation) {
        return reclamationRepository.findByDateCreation(DateCreation);
    }


    public List<Reclamation> getReclamationEntreDeuxDate(Date date1, Date date2) {
        return reclamationRepository.findByDateCreationBetween( date1,date2);
    }

    @Override
    public Reclamation modifierPriorite(Long id, String priorite) {
        Reclamation reclamation = reclamationRepository.findById(id).orElse(null);
        reclamation.setPriority(priorite);
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> trierReclamationsParPriorite() {
        List<Reclamation> reclamations = reclamationRepository.findAll();
        Collections.sort(reclamations, new ReclamationComparator());
        return reclamations;
    }

    @Override
    public int countAllByStatus(Status status) {
        return reclamationRepository.countAllByStatus(status);
    }

    @Override
    public List<Reclamation> getAllOrderedByNomASC() {
        return reclamationRepository.getAllOrderedByNomASC();
    }

    public List<Reclamation> findByStatusOrderByDateCreationDesc(Status status) {
        return reclamationRepository.findByStatusOrderByDateCreationDesc(status);
    }

    @Override
    public void asseignRecToTypeRec(Long idRec, Long idType) {
        Reclamation reclamation = reclamationRepository.findById(idRec).orElse(null);


        TypeReclamation typeReclamation = typeRecRepository.findById(idType).orElse(null);
        reclamation.setTypeReclamation(typeReclamation);
        reclamationRepository.save(reclamation);
    }

    public List<Reclamation> retrieveReclamationsByType(Long idType) {
        TypeReclamation typeReclamation = typeRecRepository.findById(idType).orElse(null);
        return  typeReclamation.getReclamations();
    }

    public TypeReclamation getTypeWithMaxClaims() {
        List<Object[]> result = reclamationRepository.countReclamationsByType();
        long maxCount = 0;
        TypeReclamation typeWithMaxClaims = null;
        for (Object[] obj : result) {
            TypeReclamation type = (TypeReclamation) obj[0];
            long count = (long) obj[1];
            if (count > maxCount) {
                maxCount = count;
                typeWithMaxClaims = type;
            }
        }
        return typeWithMaxClaims;
    }

    public Set<Reclamation> findByIdRec(Long idRec) {
        return reclamationRepository.findByIdRec(idRec);
    }

    public void activer(Reclamation user1)
    { Reclamation user=  reclamationRepository.findById(user1.getIdRec()).get();

        if(user.getStatus()==Status.non_traitée)
        {
            user.setStatus(Status.traitée);
        }
        user.setNomRec(user1.getNomRec());
        user.setDescription(user1.getDescription());
        user.setNumTel(user1.getNumTel());
        user.setPriority(user1.getPriority());
        user.setDateCreation(user1.getDateCreation());
         reclamationRepository.save(user);
    }


    @Override
  public  List<Reclamation> findByuser(Long id)
    {
        User user= userRepository.findById(id).get();
        return reclamationRepository.findAllByUser(user);
    }
}
