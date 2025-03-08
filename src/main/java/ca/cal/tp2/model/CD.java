package ca.cal.tp2.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class CD extends Document {
    private String artiste;
    private int duree;
    private String genre;

    public CD(String titre, String artiste, int duree, String genre, int nbExemplaires) {
        super(titre, nbExemplaires);
        this.artiste = artiste;
        this.duree = duree;
        this.genre = genre;
    }
}
