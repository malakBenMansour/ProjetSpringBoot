package arctic.example.pi.service;

import arctic.example.pi.DTO.CountType;
import arctic.example.pi.entity.TypeReclamation;

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
  List<TypeReclamation> findTypeReclamationByDateCreationBetween(Date date1, Date date2);
    List<CountType> statistque();
    List<Object[]> nbReclamationBytypeBetweenDeuxDates(Date date1, Date date2);
}
