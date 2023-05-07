package arctic.example.pi.DTO;

public class AccueilStat {

    private Long totalEvent;
    private int ActivEvent;
    private int eventsFull;

private int soldoutEvent;

    public int getSoldoutEvent() {
        return soldoutEvent;
    }

    public void setSoldoutEvent(int soldoutEvent) {
        this.soldoutEvent = soldoutEvent;
    }

    public Long getTotalEvent() {
        return totalEvent;
    }

    public void setTotalEvent(Long totalEvent) {
        this.totalEvent = totalEvent;
    }

    public int getActivEvent() {
        return ActivEvent;
    }

    public void setActivEvent(int activEvent) {
        ActivEvent = activEvent;
    }

    public int getEventsFull() {
        return eventsFull;
    }

    public void setEventsFull(int eventsFull) {
        this.eventsFull = eventsFull;
    }
}
