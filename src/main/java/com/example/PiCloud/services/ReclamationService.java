package com.example.PiCloud.services;


import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.ReclamationComparator;
import com.example.PiCloud.entities.Status;
import com.example.PiCloud.entities.TypeReclamation;
import com.example.PiCloud.repository.ReclamationRepository;
import com.example.PiCloud.repository.TypeRecRepository;
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


    public Reclamation addReclamation(Reclamation rec) {
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
        return reclamationRepository.findByNomRec(nomRec);
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

}
