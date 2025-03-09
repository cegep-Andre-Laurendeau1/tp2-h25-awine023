package ca.cal.tp2.dao;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Livre;
import ca.cal.tp2.model.CD;
import ca.cal.tp2.model.DVD;
import java.util.List;

public interface DocumentDAO {
    void save(Document document);
    Document findById(Long id);
    List<Document> findAll();
    void delete(Document document);

    Livre ajouterLivre(Livre livre);
    CD ajouterCD(CD cd);
    DVD ajouterDVD(DVD dvd);

    List<Document> rechercherParTitre(String titre);
    List<Livre> rechercherLivresParAuteur(String auteur);
    List<CD> rechercherCDsParArtiste(String artiste);
    List<DVD> rechercherDVDsParDirecteur(String directeur);
}
