package ca.cal.tp2.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public abstract class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentID;

    private String titre;
    private int nombreExemplaires;

    public Document(String titre, int nombreExemplaires) {
        this.titre = titre;
        this.nombreExemplaires = nombreExemplaires;
    }

    public boolean verifieDisponibilite() {
        return nombreExemplaires > 0;
    }
}
