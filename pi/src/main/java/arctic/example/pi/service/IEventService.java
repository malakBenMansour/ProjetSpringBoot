package tn.esprit.picloudbeta.service;

import tn.esprit.picloudbeta.entity.Evenement;


import java.util.List;

public interface IEventService {

    List<Evenement> retrieveAllEvent();

    Evenement addOrUpdateEvent(Evenement event);

    void removeEvenement (Evenement event);

    Evenement retrieveEvent (Long numEvent);

    void removeReservation (Long numEvent, Long numUser);
}
