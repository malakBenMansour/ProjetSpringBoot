package tn.esprit.picloudbeta.service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.picloudbeta.entity.Evenement;
import tn.esprit.picloudbeta.entity.Sponsor;
import tn.esprit.picloudbeta.repository.SponsorRepository;
import tn.esprit.picloudbeta.service.ISponsorService;

import java.util.List;

@Service
public class SponsorServiceImpl implements ISponsorService {

    @Autowired
    SponsorRepository sponsorRepo;


    public List<Sponsor> retrieveAllSponsors() {
        return (List<Sponsor>) sponsorRepo.findAll();
    }

    public Sponsor addOrUpdateSponsor(Sponsor sponsor) {
        return sponsorRepo.save(sponsor);
    }

    public void removeSponsor(Sponsor sponsor) {
        sponsorRepo.delete(sponsor);
    }

    public Sponsor retrieveSponsor(Long numSponsor) {
        return sponsorRepo.findById(numSponsor).get();
    }
}
