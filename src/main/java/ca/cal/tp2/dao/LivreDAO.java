package ca.cal.tp2.dao;

import ca.cal.tp2.model.Livre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class LivreDAO {
    private final EntityManager em;

    public LivreDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Livre livre) {
        em.persist(livre);
    }

    public List<Livre> findAll() {
        return em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
    }

    public Livre findById(Long id) {
        return em.find(Livre.class, id);
    }

    public List<Livre> rechercherParTitreOuAuteur(String critere) {
        TypedQuery<Livre> query = em.createQuery(
                "SELECT l FROM Livre l WHERE LOWER(l.titre) LIKE LOWER(:critere) " +
                        "OR LOWER(l.auteur) LIKE LOWER(:critere)", Livre.class);
        query.setParameter("critere", "%" + critere + "%");
        return query.getResultList();
    }

    public List<Livre> rechercherParAnnee(int annee) {
        TypedQuery<Livre> query = em.createQuery(
                "SELECT l FROM Livre l WHERE YEAR(l.datePublication) = :annee", Livre.class);
        query.setParameter("annee", annee);
        return query.getResultList();
    }
}
