package tn.esprit.picloudbeta.service;


import tn.esprit.picloudbeta.entity.Sponsor;


import java.util.List;

public interface ISponsorService {


    List<Sponsor> retrieveAllSponsors();

    Sponsor addOrUpdateSponsor(Sponsor sponsor);

    void removeSponsor (Sponsor sponsor);

    Sponsor retrieveSponsor (Long numSponsor);
}
