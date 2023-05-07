package arctic.example.pi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Produit  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long price;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateExp;

    @Enumerated(EnumType.STRING)
    private Etatprod etat;

    @Column(columnDefinition = "longtext")
    private String image;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "categorie_id")
    private Categorie categorie ;




    public Produit(String name, String description, Long price, Date dateExp, Categorie categorie) {
    }

    public Produit(Produit p, Long idcat) {
    }
}
