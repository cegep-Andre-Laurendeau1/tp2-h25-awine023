package ca.cal.tp2.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Amende {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineID;

    private double montant;
    private Date dateCreation;
    private boolean status;

    @ManyToOne
    private Emprunteur emprunteur;
}
