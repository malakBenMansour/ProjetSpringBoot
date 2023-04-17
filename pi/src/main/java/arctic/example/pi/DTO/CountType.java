package arctic.example.pi.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class CountType {
    private Long count;
    private  Boolean stateuser;
    public CountType(){}
}
