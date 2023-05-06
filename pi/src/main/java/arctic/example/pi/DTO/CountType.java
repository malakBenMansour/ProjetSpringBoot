package arctic.example.pi.DTO;
<<<<<<< HEAD

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
=======
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
>>>>>>> main

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
<<<<<<< HEAD
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
=======


public class CountType {
    private Long count;
    private  Boolean stateuser;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date datebirth;
    public CountType(){}
    public CountType(Long count,Boolean stateuser)
    {this.count=count;
    this.stateuser=stateuser;
    }
    public CountType(Long count,Date datebirth)
    {this.count=count;
        this.datebirth=datebirth;
    }
    public CountType(Long count,Date datebirth,Boolean stateuser)
    {this.count=count;
        this.datebirth=datebirth;
        this.stateuser=stateuser
                ;
>>>>>>> main
    }
}
