package com.example.PiCloud.services;

import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.TypeReclamation;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ITypeRec {

    public TypeReclamation addTypeReclamation(TypeReclamation t) ;
    void deleteTypeRec(Long idRec);

    TypeReclamation updateTypeReclamation(TypeReclamation t);

    List<TypeReclamation> retrieveAllTypeReclamations();

    TypeReclamation retrieveTypeReclamation( Long idType);
    Set<TypeReclamation> findTypeByDateCreation(Date DateCreation);
    int countAllByNom(String nom);
//    List<TypeReclamation> findTypeReclamationByDateCreationBetween(Date date1, Date date2);
}
