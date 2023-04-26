package arctic.example.pi.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class CountType {

    private Long count;
    private  String nom;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateCreation;
    public CountType(){}
    public CountType(Long count,String nom)
    {this.count=count;
        this.nom=nom;
    }
    public CountType(Long count,Date dateCreation)
    {this.count=count;
        this.dateCreation=dateCreation;
    }
    public CountType(Long count,Date dateCreation,String nom)
    {this.count=count;
        this.dateCreation=dateCreation;
        this.nom=nom
        ;
    }
}
