package arctic.example.pi.DTO;

public class RemoveReservationRequest {
    private Long numEvent;
    private Long id;

    public Long getNumEvent() {
        return numEvent;
    }

    public void setNumEvent(Long numEvent) {
        this.numEvent = numEvent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
