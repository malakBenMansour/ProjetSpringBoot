package arctic.example.pi.service;

import arctic.example.pi.entity.Donations;

import javax.mail.MessagingException;
import java.util.List;

public interface IDonationsService {
    Donations saveDonation(Donations donations) throws MessagingException;
    Donations updateDonation(Donations donations);
    void deleteDonation(Long id);
    List<Donations> getDonation();
    List<Object[]> getSumQuantiteByAssociation();

    List<Object[]> avgQuantiteByAssociation();
    List<Object[]> maxQuantiteByAssociation();
    List<Object[]> countDonsByAssociation();
    List<Object[]> maxMontantByMonth();

}
