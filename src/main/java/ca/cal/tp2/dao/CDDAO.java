package ca.cal.tp2.dao;

import ca.cal.tp2.model.CD;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CDDAO {
    private final EntityManager em;

    public CDDAO(EntityManager em) {
        this.em = em;
    }

    public void save(CD cd) {
        em.persist(cd);
    }

    public void update(CD cd) {
        em.merge(cd);
    }

    public void delete(CD cd) {
        em.remove(cd);
    }

    public CD findById(Long id) {
        return em.find(CD.class, id);
    }

    public List<CD> findAll() {
        return em.createQuery("SELECT c FROM CD c", CD.class).getResultList();
    }
}
