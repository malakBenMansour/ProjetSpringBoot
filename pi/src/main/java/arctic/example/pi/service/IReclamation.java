package arctic.example.pi.service;

import arctic.example.pi.entity.Reclamation;
import arctic.example.pi.entity.Status;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReclamation {

//    public Reclamation addReclamation(Reclamation rec) ;
    public Reclamation addReclamation(Reclamation rec, Long idType);
    Set<Reclamation> findByIdRec (Long idRec);
    void deleteReclamation(Long idRec);

    Reclamation updateReclamation(Reclamation rec);

    List<Reclamation> retrieveAllReclamations();

    Reclamation retrieveReclamation( Long idRec);
    Set <Reclamation>  getReclamationByNom(String nomRec);
    Set <Reclamation>  getReclamationByDateCreation(Date DateCreation);
    List<Reclamation> getReclamationEntreDeuxDate(Date date1, Date date2);
    Reclamation modifierPriorite(Long id, String priorite);
    List<Reclamation> trierReclamationsParPriorite();
    int countAllByStatus(Status status);
    List<Reclamation> getAllOrderedByNomASC();

    List<Reclamation> findByStatusOrderByDateCreationDesc(Status status);
    void asseignRecToTypeRec(Long idRec, Long idType);
        List<Reclamation> retrieveReclamationsByType(Long idType);

List<Reclamation> findByuser(Long id);


}
