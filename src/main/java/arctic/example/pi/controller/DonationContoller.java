package arctic.example.pi.controller;


import arctic.example.pi.entity.Associations;
import arctic.example.pi.entity.Donations;
import arctic.example.pi.entity.Role;
import arctic.example.pi.repository.AssociationRepository;
import arctic.example.pi.repository.DonationsRepostitory;
import arctic.example.pi.service.DonationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/donations")
@RestController
public class DonationContoller {
    @Autowired
    DonationsService donationsService;
    @Autowired
    AssociationRepository associationRepository;

    @Autowired
    DonationsRepostitory donationsRepostitory;
    @GetMapping("/getDonation")
    public List<Donations> afficher()
    {
        return donationsService.getDonation();
    }

    @PostMapping("/saveD")
    public Donations add(@RequestBody Donations donations) throws MessagingException {
        return donationsService.saveDonation(donations);
    }

    @PutMapping("/updateD")
    public Donations modifier(@RequestBody Donations donations)
    {
        return  donationsService.updateDonation(donations);
    }
    @DeleteMapping("/deleteD/{id}")
    public void supprimer(@PathVariable(value = "id") Long id)
    {
        donationsService.deleteDonation(id);
    }
    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        String fileName = "donation_" + currentDateTime + ".xlsx";
        String filePath = desktopPath + fileName;
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        List<Donations> listUsers = donationsService.getDonation();

        // UserExcelController excelExporter = new UserExcelController();
        DonationExcelController excelExporter=new DonationExcelController(listUsers);
       // excelExporter.export((HttpServletResponse) new FileOutputStream(filePath));
       // response.flushBuffer();
        excelExporter.export(filePath);

        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"donations.xlsx\"");
        response.setContentLength((int) file.length());

        OutputStream outStream = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.flush();
        outStream.close();

       // file.delete();

    }
    // send mail !!
    @GetMapping("/sendmail/{id}")
    public String sendmail(@PathVariable("id") Long id){

        // String email= associationsService
        // Associations ASS= (Associations) associationsService.findByEmail(email);
      //  String email= donationsRepostitory.findById(id).get().getAssociations().getMail();
String email1 ="ikbelbenmansour4@gmail.com";
        donationsService.sendSimpleEmail(email1,
                        "Reservation with succcess.\n" +
                                "Now you can get your ticket\n" +
                                "\n" +

                                "\n" +
                                "thank you",
                        "Reservation"
                );
 return email1;
      //  return donationsRepostitory.findById(id).get().getAssociations().getNom();
    }
    @GetMapping("/getSum")
    public List<Object[]> sumQuantitéByAsso ()
    {return donationsService.getSumQuantiteByAssociation();}

@GetMapping("stats/moyenne-by-quantité")
    public List<Object[]> avgQuantiteByAssociation()
{
    return donationsService.avgQuantiteByAssociation();
}
@GetMapping("stats/getAssobyMaxQuantité")
    public List<Object[]> maxQuantiteByAssociation()
{return donationsService.maxQuantiteByAssociation();}
@GetMapping("stats/getassoByMaxDon")
    public List<Object[]> countDonsByAssociation()
{
    return donationsService.countDonsByAssociation();
}
@GetMapping("stats/MaxQuantitebyMonth")
    public List<Object[]> maxQuantiteByMonth()
{
    return donationsService.maxMontantByMonth();
}
    @PutMapping("/donations/{id}")
    public ResponseEntity<Donations> updateDonationStatus(@PathVariable("id") Long id) {
        Optional<Donations> donationOptional = donationsRepostitory.findById(id);
        if (!donationOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Donations donation = donationOptional.get();
        donation.setSuivi(true);
        Donations updatedDonation = donationsRepostitory.save(donation);
        return ResponseEntity.ok(updatedDonation);
    }
    @PostMapping("/donations/{donationId}/{associationId}")
    public ResponseEntity<Donations> assignAssociationToDonation(@PathVariable Long donationId, @PathVariable Long associationId) {
        Optional<Donations> donation = donationsRepostitory.findById(donationId);
        Optional<Associations> association = associationRepository.findById(associationId);
Associations associations1= association.get();
 String mail=associations1.getMail();
 Donations dons=donation.get();
 String description=dons.getDescription();
        donationsService.sendSimpleEmail(mail,
                "Dear Association,\n\n" +
                        "We are pleased to inform you that a new donation of %s has been made to your organization by %s (%s).\n" +
                        "The donation was made for the purpose of %s, and was designated as a %s.\n" +
                        "Thank you for your continued work and dedication to your cause.\n\n" +
                        "Sincerely,\n"+
                        "Your Donation Platform"
                ,

                "Dons"
        );
        if (donation.isPresent() && association.isPresent()) {
            Donations updatedDonation = donation.get();
            updatedDonation.setAssociations(association.get());
            donationsRepostitory.save(updatedDonation);
            return ResponseEntity.ok(updatedDonation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public DonationContoller(DonationsRepostitory donationRepository) {
        this.donationsRepostitory = donationRepository;
    }

    @GetMapping("/donation-stats")
    public List<Object[]> getDonationStatistics() {
        return donationsRepostitory.countByAssociation();
    }

}
