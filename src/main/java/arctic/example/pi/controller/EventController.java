package arctic.example.pi.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import arctic.example.pi.DTO.*;
import arctic.example.pi.entity.Evenement;
import arctic.example.pi.entity.Sponsor;
import arctic.example.pi.entity.User;
import arctic.example.pi.service.IEventService;
import arctic.example.pi.service.ParticipantsPDFService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private IEventService eventService;

    @Autowired
    ServletContext context;

    @PostMapping(value = "/addEvent")
    public void addSponsor(@RequestParam("file") MultipartFile file, @RequestParam("event") String event) throws IOException {
        System.out.println("Save event.............");
        Evenement ev = new ObjectMapper().readValue(event, Evenement.class);

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File("C:/Users/Inesk/Desktop/PICloud_Beta/pi/src/main/webapp/Imagess/" + newFileName);

        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (IOException e) {
            throw new IOException("Failed to save file: " + e.getMessage());
        }

        System.out.println("Real path: " + serverFile.getAbsolutePath());

        ev.setFileName(newFileName);

        try {
            eventService.addEvent(ev);
        } catch (Exception e) {
            FileUtils.deleteQuietly(serverFile);
            throw new IOException("Failed to save event: " + e.getMessage());
        }
    }

    @PutMapping("/updateEvent/{numEvent}")
    public  void updateEvent(@RequestParam("file") MultipartFile file, @RequestParam("event") String event,
                             @PathVariable("numEvent") Long numEvent)throws IOException{

        System.out.println("Save event.............");
        Evenement ev = new ObjectMapper().readValue(event, Evenement.class);
        ObjectMapper objectMapper = new ObjectMapper();



        ev.setNumEvent(numEvent);

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = FilenameUtils.getBaseName(originalFileName) + "." + FilenameUtils.getExtension(originalFileName);

        // Create a file object for the new image file
        File newFile = new File("C:/Users/Inesk/Desktop/PICloud_Beta/pi/src/main/webapp/Imagess/" + newFileName);

        // Delete the old image file if it exists
        String oldFileName = ev.getFileName();
        if (oldFileName != null) {
            File oldFile = new File("C:/Users/Inesk/Desktop/PICloud_Beta/pi/src/main/webapp/Imagess/" + oldFileName);
            FileUtils.deleteQuietly(oldFile);
        }

        // Write the new image file to the server
        try {
            FileUtils.writeByteArrayToFile(newFile, file.getBytes());
        } catch (IOException e) {
            throw new IOException("Failed to save file: " + e.getMessage());
        }

        // Set the new file name in the event object
        ev.setFileName(newFileName);


        // Update the event in the database
        try {
            eventService.updateEvenement(ev);
            System.out.println(ev.getFileName());
        } catch (Exception e) {
            FileUtils.deleteQuietly(newFile);
            throw new IOException("Failed to save event: " + e.getMessage());
        }
    }

  /*  @PutMapping("/updateEvent")
    public  void updateEvent(@RequestBody Evenement event){
        eventService.updateEvenement(event);
    }*/


    @DeleteMapping("/deleteEvent/{id}")
    public void deleteEvent(@PathVariable Long id ){
        eventService.removeEvenement(id);
    }

    @GetMapping("/events")
    public List<Evenement> getAllEvents(){
        return eventService.retrieveAllEvent();
    }

    @GetMapping("/getEvent/{id}")
    public Optional<Evenement> getEventById(@PathVariable Long id){
        return eventService.retrieveEvent(id);
    }

    @PostMapping ("/reserver")
    public void assign(@RequestBody ReservationRequest reservationRequest) throws IOException, WriterException, MessagingException

    { eventService.Reserver(reservationRequest);}

    @GetMapping(path="/ImgEvent/{numEvent}")
    public byte[] getPhoto(@PathVariable("numEvent") Long id) throws Exception{
        Evenement event   =eventService.retrieveEvent(id).get();
        Path imagePath = Paths.get("C:/Users/Inesk/Desktop/PICloud_Beta/pi/src/main/webapp/Imagess/" + event.getFileName());
        return Files.readAllBytes(imagePath);
    }

    @GetMapping("/getActivEvents")
    public List<Evenement> getActiveEvents() {return eventService.retrieveActiveEvents();}

    @GetMapping("/getReservationByUser/{id}")
    public List<Evenement> getReservationByUser(@PathVariable Long id){
        return eventService.retrieveReservationsByUser(id);
    }




    @PostMapping("/deleteReservation")
    public void deleteReservation(@RequestBody RemoveReservationRequest removeReservationRequest){
        eventService.removeReservation(removeReservationRequest);
    }

    @GetMapping("/sponsorsNonDuEvent/{id}")
    public List<Sponsor> getSponsorNonDuEvent(@PathVariable Long id) {
        return eventService.getSponsorNonDuEvent(id);
    }

    @GetMapping("/SponsorDuEvent/{id}")
    public List<Sponsor> getSponsorsDuEvent(@PathVariable Long id) {
        return eventService.getSponsorsDuEvent(id);
    }
    @GetMapping("/getParticipants/{id}")
    public List<User> getUsersByEvent(@PathVariable Long id){
        return eventService.retrieveUsersByEvent(id);
    }
    @PostMapping("/assignSponsor")
    public void assignSponsorToEvent(@RequestBody AssignToEventRequest req) {
        eventService.addSponsorFromEvent(req);
    }

    @PostMapping("/removeSponsor")
    public void removeSponsorDuEvent(@RequestBody RemoveSponsorFromEventRequest req) {
        eventService.removeSponsorFromEvent(req);
    }

@GetMapping("/soldOut")
public int getSoldOutEvents(){
        return eventService.countSoldOutEvents();
}

    @GetMapping("/placeDispo/{numEvent}")
    public int getAvailablePlacesPerEvent(@PathVariable Long numEvent){
        return eventService.numberPlacesAvailablePerEvent(numEvent);
    }

    @GetMapping(value = "/openpdf/participants/{numEvent}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> employeeReport(@PathVariable Long numEvent)  throws IOException {
        List<User> participants = (List<User>) eventService.retrieveUsersByEvent(numEvent);

        ByteArrayInputStream bis = ParticipantsPDFService.participantPDFReport(participants);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=participants.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/home")
    public AccueilStat accueil() {
        return eventService.pageAccueil();
    }




}
