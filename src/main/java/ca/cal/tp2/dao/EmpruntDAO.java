package ca.cal.tp2.dao;

import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.EmpruntDetail;
import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunteur;
import java.util.Date;
import java.util.List;

public interface EmpruntDAO {
    List<Emprunt> getEmprunts();
    Emprunt creerEmprunt(Emprunteur emprunteur, Date dateEmprunt);
    EmpruntDetail ajouterDocumentAEmprunt(Emprunt emprunt, Document document, Date dateRetourPrevue);
    List<Emprunt> getEmpruntsParEmprunteur(long emprunteurId);
    Emprunt getEmpruntById(long empruntId);
    List<EmpruntDetail> getDetailsEmprunt(long empruntId);
    boolean enregistrerRetour(long empruntDetailId, Date dateRetourActuelle);
    boolean updateStatutEmprunt(long empruntId, String nouveauStatut);
}
