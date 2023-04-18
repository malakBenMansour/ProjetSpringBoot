package arctic.example.pi.controller;

import arctic.example.pi.entity.Donations;
import arctic.example.pi.entity.Role;
import arctic.example.pi.service.DonationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DonationContoller {
    @Autowired
    DonationsService donationsService;
    @GetMapping("/getDonation")
    public List<Donations> afficher()
    {
        return donationsService.getDonation();
    }

    @PostMapping("/saveD")
    public Donations add(@RequestBody Donations donations)
    {
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
}
