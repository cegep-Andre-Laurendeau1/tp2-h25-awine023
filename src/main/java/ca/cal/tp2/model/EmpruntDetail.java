package ca.cal.tp2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class EmpruntDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemID;

    private Date dateRetourPrevue;
    private Date dateRetourActuelle;
    private String status;

    @ManyToOne
    private Emprunt emprunt;

    @ManyToOne
    private Document document;

    public boolean isEnRetard() {
        return dateRetourActuelle != null && dateRetourPrevue != null && dateRetourActuelle.after(dateRetourPrevue);
    }
}
