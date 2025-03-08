package ca.cal.tp2.dao;

import ca.cal.tp2.model.CD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CDDAO {
    private final EntityManager em;

    public CDDAO(EntityManager em) {
        this.em = em;
    }

    public void save(CD cd) {
        em.persist(cd);
    }

    public List<CD> findAll() {
        return em.createQuery("SELECT c FROM CD c", CD.class).getResultList();
    }

    public CD findById(Long id) {
        return em.find(CD.class, id);
    }

    public List<CD> rechercherParTitreOuArtiste(String critere) {
        TypedQuery<CD> query = em.createQuery(
                "SELECT c FROM CD c WHERE LOWER(c.titre) LIKE LOWER(:critere) " +
                        "OR LOWER(c.artiste) LIKE LOWER(:critere)", CD.class);
        query.setParameter("critere", "%" + critere + "%");
        return query.getResultList();
    }
}
