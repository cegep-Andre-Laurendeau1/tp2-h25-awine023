package ca.cal.tp2.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class Livre extends Document {
    private String ISBN;
    private String auteur;
    private String editeur;
    private int nombrePages;
}
