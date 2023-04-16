package com.example.PiCloud.repository;

import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.TypeReclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface TypeRecRepository extends JpaRepository<TypeReclamation , Long> {

//    List<TypeReclamation> findTypeReclamationByDateCreationBetween(Date date1, Date date2);
//    Set<TypeReclamation> findByDateCreation(Date DateCreation);


}
