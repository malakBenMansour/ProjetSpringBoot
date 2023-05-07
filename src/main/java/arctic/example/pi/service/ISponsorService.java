package arctic.example.pi.service;





import arctic.example.pi.entity.Sponsor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public interface ISponsorService {


    List<Sponsor> retrieveAllSponsors();

    public void addSponsor(Sponsor sponsor) ;

    void updateSponsor(Sponsor sponsor);

    void removeSponsor (Long id);

    Sponsor retrieveSponsor (Long numSponsor);

    List <Sponsor>  exportSponsorsToExcel(HttpServletResponse response) throws IOException;
}
