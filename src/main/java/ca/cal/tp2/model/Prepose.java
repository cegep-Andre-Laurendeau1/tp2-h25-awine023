package ca.cal.tp2.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@DiscriminatorValue("PREPOSE")
@Getter @Setter
@ToString(callSuper = true)
public class Prepose extends Utilisateur {

    public Prepose() {
        super();
    }

    public Prepose(String name, String email, String phoneNumber) {
        super(name, email, phoneNumber, "PREPOSE");
    }


    public void entreNouveauDocument(Document document) {
        if (document != null) {
            System.out.println("📌 Nouveau document ajouté : " + document.getTitre());
        } else {
            System.out.println("⚠️ Impossible d'ajouter un document null !");
        }
    }

    public void collecteAmende(Emprunteur emprunteur, double montant) {
        if (emprunteur != null && montant > 0) {
            System.out.println("💰 Amende collectée pour " + emprunteur.getName() + ", Montant : " + montant);
        } else {
            System.out.println("⚠️ Impossible de collecter une amende invalide !");
        }
    }
}
