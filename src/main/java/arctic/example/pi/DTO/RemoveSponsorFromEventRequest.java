package arctic.example.pi.DTO;

public class RemoveSponsorFromEventRequest {
    private Long numEvent;
    private Long numSponsor;

    public Long getNumEvent() {
        return numEvent;
    }

    public void setNumEvent(Long numEvent) {
        this.numEvent = numEvent;
    }

    public Long getNumSponsor() {
        return numSponsor;
    }

    public void setNumSponsor(Long numSponsor) {
        this.numSponsor = numSponsor;
    }
}
