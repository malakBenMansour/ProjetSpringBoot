package arctic.example.pi.controller;


import arctic.example.pi.entity.Sponsor;
import arctic.example.pi.service.ISponsorService;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.boot.json.JsonParseException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    @Autowired
    private ISponsorService sponsorService;
    @Autowired
    ServletContext context;

    @PostMapping(value = "/addSponsor")
    public void addSponsor(@RequestParam("file") MultipartFile file, @RequestParam("sponsor") String sponsor) throws IOException {
        System.out.println("Save sponsor.............");
        Sponsor spons = new ObjectMapper().readValue(sponsor, Sponsor.class);

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = FilenameUtils.getBaseName(filename) + "." + FilenameUtils.getExtension(filename);
        File serverFile = new File("C:/Users/Inesk/Desktop/PICloud_Beta/pi/src/main/webapp/Imagess/" + newFileName);

        try {
            FileUtils.writeByteArrayToFile(serverFile, file.getBytes());
        } catch (IOException e) {
            throw new IOException("Failed to save file: " + e.getMessage());
        }

        System.out.println("Real path: " + serverFile.getAbsolutePath());

        spons.setFileName(newFileName);

        try {
            sponsorService.addSponsor(spons);
        } catch (Exception e) {
            FileUtils.deleteQuietly(serverFile);
            throw new IOException("Failed to save sponsor: " + e.getMessage());
        }
    }

    @GetMapping(path="/ImgSpons/{numSponsor}")
    public byte[] getPhoto(@PathVariable("numSponsor") Long id) throws Exception{
        Sponsor sponsor   =sponsorService.retrieveSponsor(id);
        Path imagePath = Paths.get("C:/Users/Inesk/Desktop/PICloud_Beta/pi/src/main/webapp/Imagess/" + sponsor.getFileName());
        return Files.readAllBytes(imagePath);
    }

    @PutMapping("/updateSponsor/{numSponsor}")
    public void updateSponsort(@RequestParam("file") MultipartFile file, @RequestParam("sponsor") String sponsor, @PathVariable Long numSponsor) throws IOException{
        System.out.println("Save event.............");
        Sponsor ev = new ObjectMapper().readValue(sponsor, Sponsor.class);
        ev.setNumSponsor(numSponsor);
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
            sponsorService.updateSponsor(ev);
            System.out.println(ev.getFileName());
        } catch (Exception e) {
            FileUtils.deleteQuietly(newFile);
            throw new IOException("Failed to save spons: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteSponsor/{numSponsor}")
    public void deleteSponsor(@PathVariable Long numSponsor ){
        sponsorService.removeSponsor(numSponsor);
    }

    @GetMapping("/sponsors")
    public List<Sponsor> getAllSponsors(){
        return sponsorService.retrieveAllSponsors();
    }

    @GetMapping("/getSponsor/{id}")
    public Sponsor getSponsorById(@PathVariable Long id){
        return sponsorService.retrieveSponsor(id);
    }

    @GetMapping("/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Sponsors_Information.xlsx";
        response.setHeader(headerKey, headerValue);
        sponsorService.exportSponsorsToExcel(response);

    }


}
