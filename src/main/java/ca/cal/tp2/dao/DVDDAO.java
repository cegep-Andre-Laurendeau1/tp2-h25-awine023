package ca.cal.tp2.dao;

import ca.cal.tp2.model.DVD;
import jakarta.persistence.EntityManager;
import java.util.List;

public class DVDDAO {
    private final EntityManager em;

    public DVDDAO(EntityManager em) {
        this.em = em;
    }

    public void save(DVD dvd) {
        em.persist(dvd);
    }

    public void update(DVD dvd) {
        em.merge(dvd);
    }

    public void delete(DVD dvd) {
        em.remove(dvd);
    }

    public DVD findById(Long id) {
        return em.find(DVD.class, id);
    }

    public List<DVD> findAll() {
        return em.createQuery("SELECT d FROM DVD d", DVD.class).getResultList();
    }
}
