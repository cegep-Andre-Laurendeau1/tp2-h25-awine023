package ca.cal.tp2.model;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "livre")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString(callSuper = true)
public class Livre extends Document {

    @Column(nullable = false)
    private String ISBN;

    @Column(nullable = false)
    private String auteur;

    @Column(nullable = false)
    private String editeur;

    @Column(nullable = false)
    private int nombrePages;

    @Temporal(TemporalType.DATE)
    private Date datePublication;

    public Livre(String titre, int nombreExemplaires, String ISBN, String auteur, String editeur, int nombrePages, Date datePublication) {
        super(titre, nombreExemplaires);
        this.ISBN = ISBN;
        this.auteur = auteur;
        this.editeur = editeur;
        this.nombrePages = nombrePages;
        this.datePublication = datePublication;
    }
}
