package com.example.PiCloud.repository;


import com.example.PiCloud.entities.Reclamation;
import com.example.PiCloud.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation , Long > {
    Set<Reclamation> findByNomRec(String nomRec);
    Set<Reclamation> findByDateCreation(Date DateCreation);
    List<Reclamation> findByDateCreationBetween(Date date1, Date date2);


    @Query("select r from Reclamation r where r.status=:status order by r.dateCreation ASC ")
    List<Reclamation> findByStatusOrderByDateCreationDesc(Status status);

    int countAllByStatus(Status status);
    @Query("select r from Reclamation r order by r.nomRec ASC ")
    List<Reclamation> getAllOrderedByNomASC();





}



