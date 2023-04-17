package com.example.PiCloud.services;

import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.Status;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IReclamation {

    public Reclamation addReclamation(Reclamation rec) ;
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

}
