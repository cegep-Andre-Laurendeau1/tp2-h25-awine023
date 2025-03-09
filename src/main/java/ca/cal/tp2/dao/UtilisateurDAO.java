package ca.cal.tp2.dao;

import ca.cal.tp2.model.Utilisateur;
import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.model.Prepose;
import java.util.List;

public interface UtilisateurDAO {
    List<Utilisateur> getUtilisateurs();
    List<Emprunteur> getEmprunteurs();
    List<Prepose> getPreposes();
    Emprunteur ajouterEmprunteur(Emprunteur emprunteur);
    Prepose ajouterPrepose(Prepose prepose);
    List<Emprunteur> rechercherEmprunteurParNom(String nom);
    Utilisateur getUtilisateurById(long utilisateurId);
    Emprunteur getEmprunteurById(long emprunteurId);
}
