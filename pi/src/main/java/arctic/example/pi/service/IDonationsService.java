package arctic.example.pi.service;

import arctic.example.pi.entity.Donations;

import java.util.List;

public interface IDonationsService {
    Donations saveDonation(Donations donations);
    Donations updateDonation(Donations donations);
    void deleteDonation(Long id);
    List<Donations> getDonation();
}
