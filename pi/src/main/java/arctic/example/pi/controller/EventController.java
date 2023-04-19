package tn.esprit.picloudbeta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.picloudbeta.entity.Evenement;
import tn.esprit.picloudbeta.service.IEventService;

import java.util.List;

@RestController
public class EventController {

    @Autowired
    private IEventService eventService;

    @PostMapping("/addEvent")
    public Evenement addEvent(@RequestBody Evenement event){
        return eventService.addOrUpdateEvent(event);
    }

    @PutMapping("/updateEvent")
    public  Evenement updateEvent(@RequestBody Evenement event){
        return eventService.addOrUpdateEvent(event);
    }

    @DeleteMapping("/deleteEvent")
    public void deleteEvent(@RequestBody Evenement event){
        eventService.removeEvenement(event);
    }

    @GetMapping("/events")
    public List<Evenement> getAllEvents(){
        return eventService.retrieveAllEvent();
    }

    @GetMapping("/getEvent/{id}")
    public Evenement getEventById(@PathVariable Long id){
        return eventService.retrieveEvent(id);
    }
}
