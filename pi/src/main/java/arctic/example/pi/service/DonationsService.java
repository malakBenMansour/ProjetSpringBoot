package arctic.example.pi.service;

import arctic.example.pi.entity.Donations;
import arctic.example.pi.repository.DonationsRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DonationsService implements IDonationsService{
    @Autowired
    DonationsRepostitory donationsRepostitory;
    @Override
    public Donations saveDonation(Donations donations) {
        return  donationsRepostitory.save(donations);
    }

    @Override
    public Donations updateDonation(Donations donations) {
        return donationsRepostitory.save(donations);
    }

    @Override
    public void deleteDonation(Long id) {
       donationsRepostitory.deleteById(id);
    }

    @Override
    public List<Donations> getDonation() {
        return donationsRepostitory.findAll();
    }
}
