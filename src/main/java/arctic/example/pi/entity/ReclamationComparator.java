package arctic.example.pi.entity;

import java.util.Comparator;

public class ReclamationComparator implements Comparator<Reclamation> {
    @Override
    public int compare(Reclamation r1, Reclamation r2) {
        // Tri par ordre décroissant de priorité
        if (r1.getPriority().equals(r2.getPriority())) {
            return 0;
        } else if (r1.getPriority().equals("Haute")) {
            return -1;
        } else if (r2.getPriority().equals("Haute")) {
            return 1;
        } else if (r1.getPriority().equals("Moyenne")) {
            return -1;
        } else if (r2.getPriority().equals("Moyenne")){
            return 1;
        } else if (r1.getPriority().equals("Faible")){
            return -1;

    } else {return 1;
        }
        }
}
