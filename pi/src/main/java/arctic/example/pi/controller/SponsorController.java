package tn.esprit.picloudbeta.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.esprit.picloudbeta.entity.Evenement;

import tn.esprit.picloudbeta.entity.Sponsor;
import tn.esprit.picloudbeta.service.ISponsorService;

import java.util.List;

@RestController
public class SponsorController {

    @Autowired
    private ISponsorService sponsorService;

    @PostMapping("/addSponsor")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor){
        return sponsorService.addOrUpdateSponsor(sponsor);
    }

    @PutMapping("/updateSponsor")
    public  Sponsor updateSponsort(@RequestBody Sponsor sponsor){
        return sponsorService.addOrUpdateSponsor(sponsor);
    }

    @DeleteMapping("/deleteSponsor")
    public void deleteSponsor(@RequestBody Sponsor sponsor){
        sponsorService.removeSponsor(sponsor);
    }

    @GetMapping("/sponsors")
    public List<Sponsor> getAllSponsors(){
        return sponsorService.retrieveAllSponsors();
    }

    @GetMapping("/getSponsor/{id}")
    public Sponsor getSponsorById(@PathVariable Long id){
        return sponsorService.retrieveSponsor(id);
    }


}
