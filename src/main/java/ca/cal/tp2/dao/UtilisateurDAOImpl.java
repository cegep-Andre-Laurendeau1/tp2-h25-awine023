package ca.cal.tp2.dao;

import ca.cal.tp2.model.Utilisateur;
import ca.cal.tp2.model.Emprunteur;
import ca.cal.tp2.model.Prepose;

import static ca.cal.tp2.dao.EmParentDAO.executeInTransaction;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    @Override
    public List<Utilisateur> getUtilisateurs() {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class)
                        .getResultList()
        );
    }

    @Override
    public List<Emprunteur> getEmprunteurs() {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM Emprunteur e", Emprunteur.class)
                        .getResultList()
        );
    }

    @Override
    public List<Prepose> getPreposes() {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT p FROM Prepose p", Prepose.class)
                        .getResultList()
        );
    }

    @Override
    public Emprunteur ajouterEmprunteur(Emprunteur nouvelEmprunteur) {
        return executeInTransaction(entityManager -> {
            entityManager.persist(nouvelEmprunteur);
            return nouvelEmprunteur;
        });
    }

    @Override
    public Prepose ajouterPrepose(Prepose agent) {
        return executeInTransaction(entityManager -> {
            entityManager.persist(agent);
            return agent;
        });
    }

    @Override
    public List<Emprunteur> rechercherEmprunteurParNom(String termeRecherche) {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM Emprunteur e WHERE LOWER(e.name) LIKE :search", Emprunteur.class)
                        .setParameter("search", "%" + termeRecherche.toLowerCase() + "%")
                        .getResultList()
        );
    }

    @Override
    public Utilisateur getUtilisateurById(long idUtilisateur) {
        return executeInTransaction(entityManager ->
                entityManager.find(Utilisateur.class, idUtilisateur)
        );
    }

    @Override
    public Emprunteur getEmprunteurById(long idEmprunteur) {
        return executeInTransaction(entityManager ->
                entityManager.find(Emprunteur.class, idEmprunteur)
        );
    }
}
