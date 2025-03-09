package ca.cal.tp2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("Emprunteur")
@Getter @Setter
@ToString(callSuper = true)
public class Emprunteur extends Utilisateur {

    public Emprunteur() {
        super();
        this.setTypeUtilisateur("Emprunteur");
    }

    public Emprunteur(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber, "Emprunteur");
    }
}
