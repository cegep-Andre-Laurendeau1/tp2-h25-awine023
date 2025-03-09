package ca.cal.tp2.dao;

import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Livre;
import ca.cal.tp2.model.CD;
import ca.cal.tp2.model.DVD;
import static ca.cal.tp2.dao.EmParentDAO.executeInTransaction;
import java.util.List;

public class DocumentDAOImpl implements DocumentDAO {

    @Override
    public void save(Document document) {
        executeInTransaction(em -> {
            if (document == null) {
                throw new IllegalArgumentException("Le document à sauvegarder est null !");
            }
            em.persist(document);
            return null;
        });
    }

    @Override
    public Document findById(Long id) {
        return executeInTransaction(em -> {
            Document doc = em.find(Document.class, id);
            if (doc == null) {
                throw new IllegalArgumentException("Aucun document trouvé avec l'ID : " + id);
            }
            return doc;
        });
    }

    @Override
    public List<Document> findAll() {
        return executeInTransaction(em -> {
            List<Document> documents = em.createQuery("SELECT d FROM Document d", Document.class).getResultList();
            if (documents.isEmpty()) {
                System.out.println("Aucun document trouvé dans la base de données.");
            }
            return documents;
        });
    }

    @Override
    public void delete(Document document) {
        executeInTransaction(em -> {
            Document docManaged = em.find(Document.class, document.getDocumentID());
            if (docManaged == null) {
                throw new IllegalArgumentException("Impossible de supprimer : Document introuvable !");
            }
            em.remove(docManaged);
            return null;
        });
    }

    @Override
    public Livre ajouterLivre(Livre livre) {
        return executeInTransaction(em -> {
            if (livre == null) {
                throw new IllegalArgumentException("Le livre à ajouter est null !");
            }
            em.persist(livre);
            return livre;
        });
    }

    @Override
    public CD ajouterCD(CD cd) {
        return executeInTransaction(em -> {
            if (cd == null) {
                throw new IllegalArgumentException("Le CD à ajouter est null !");
            }
            em.persist(cd);
            return cd;
        });
    }

    @Override
    public DVD ajouterDVD(DVD dvd) {
        return executeInTransaction(em -> {
            if (dvd == null) {
                throw new IllegalArgumentException("Le DVD à ajouter est null !");
            }
            em.persist(dvd);
            return dvd;
        });
    }

    @Override
    public List<Document> rechercherParTitre(String titre) {
        return executeInTransaction(em -> {
            if (titre == null || titre.trim().isEmpty()) {
                throw new IllegalArgumentException("Le titre ne peut pas être vide !");
            }
            return em.createQuery("SELECT d FROM Document d WHERE LOWER(d.titre) LIKE :titre", Document.class)
                    .setParameter("titre", "%" + titre.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public List<Livre> rechercherLivresParAuteur(String auteur) {
        return executeInTransaction(em -> {
            if (auteur == null || auteur.trim().isEmpty()) {
                throw new IllegalArgumentException("L'auteur ne peut pas être vide !");
            }
            return em.createQuery("SELECT l FROM Livre l WHERE LOWER(l.auteur) LIKE :auteur", Livre.class)
                    .setParameter("auteur", "%" + auteur.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public List<CD> rechercherCDsParArtiste(String artiste) {
        return executeInTransaction(em -> {
            if (artiste == null || artiste.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom de l'artiste ne peut pas être vide !");
            }
            return em.createQuery("SELECT c FROM CD c WHERE LOWER(c.artiste) LIKE :artiste", CD.class)
                    .setParameter("artiste", "%" + artiste.toLowerCase() + "%")
                    .getResultList();
        });
    }

    @Override
    public List<DVD> rechercherDVDsParDirecteur(String directeur) {
        return executeInTransaction(em -> {
            if (directeur == null || directeur.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom du directeur ne peut pas être vide !");
            }
            return em.createQuery("SELECT d FROM DVD d WHERE LOWER(d.directeur) LIKE :directeur", DVD.class)
                    .setParameter("directeur", "%" + directeur.toLowerCase() + "%")
                    .getResultList();
        });
    }
}
