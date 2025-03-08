package ca.cal.tp2.dao;

import ca.cal.tp2.model.DVD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DVDDAO {
    private final EntityManager em;

    public DVDDAO(EntityManager em) {
        this.em = em;
    }

    public void save(DVD dvd) {
        em.persist(dvd);
    }

    public List<DVD> findAll() {
        return em.createQuery("SELECT d FROM DVD d", DVD.class).getResultList();
    }

    public DVD findById(Long id) {
        return em.find(DVD.class, id);
    }

    public List<DVD> rechercherParTitreOuRealisateur(String critere) {
        TypedQuery<DVD> query = em.createQuery(
                "SELECT d FROM DVD d WHERE LOWER(d.titre) LIKE LOWER(:critere) " +
                        "OR LOWER(d.director) LIKE LOWER(:critere)", DVD.class);
        query.setParameter("critere", "%" + critere + "%");
        return query.getResultList();
    }
}
