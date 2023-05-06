package arctic.example.pi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatistiquesDTO {



    private Long nombreArticlesAccepte;
    private Long nombreArticlesENcours;
    private Long nombreArticlesRefuse;

}
//