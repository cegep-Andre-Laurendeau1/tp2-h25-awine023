package ca.cal.tp2.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class DVD extends Document {
    private String directeur;
    private int duree;
    private String rating;

    public DVD(String titre, int nombreExemplaires, String directeur, int duree, String rating) {
        super(titre, nombreExemplaires);
        this.directeur = directeur;
        this.duree = duree;
        this.rating = rating;
    }

}
