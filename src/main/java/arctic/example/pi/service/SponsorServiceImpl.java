package arctic.example.pi.service;

import arctic.example.pi.entity.Evenement;
import arctic.example.pi.entity.Sponsor;
import arctic.example.pi.repository.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class SponsorServiceImpl implements ISponsorService {

    @Autowired
    SponsorRepository sponsorRepo;


    public List<Sponsor> retrieveAllSponsors() {
        return (List<Sponsor>) sponsorRepo.findAll();
    }

    @Override
    public void addSponsor(Sponsor sponsor) {
     sponsorRepo.save(sponsor);
    }



    @Override
    public void updateSponsor(Sponsor sponsor) {
        Optional<Sponsor> spons = sponsorRepo.findById(sponsor.getNumSponsor());
        if (spons.isPresent()) {
            spons.get().setNomSponsor(sponsor.getNomSponsor());
            spons.get().setDescription(sponsor.getDescription());
            spons.get().setDebutContract(sponsor.getDebutContract());
            spons.get().setFinContract(sponsor.getFinContract());
            spons.get().setFileName(sponsor.getFileName());
            sponsorRepo.save(spons.get());
        }
    }

    @Override
    public void removeSponsor(Long id) {
        sponsorRepo.deleteById(id);
    }
    public Sponsor retrieveSponsor(Long numSponsor) {
        return sponsorRepo.findById(numSponsor).get();
    }

    @Override
    public List<Sponsor> exportSponsorsToExcel(HttpServletResponse response) throws IOException {
        List<Sponsor> sponsors = (List<Sponsor>) sponsorRepo.findAll();
        ExcelExportSponsors exportUtils = new ExcelExportSponsors(sponsors);
        exportUtils.exportDataToExcel(response);
        return sponsors;
    }
}
