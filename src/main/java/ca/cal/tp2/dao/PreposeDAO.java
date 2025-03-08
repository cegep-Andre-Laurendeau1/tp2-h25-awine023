package ca.cal.tp2.dao;

import ca.cal.tp2.model.Prepose;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PreposeDAO {
    private final EntityManager em;

    public PreposeDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prepose prepose) {
        em.persist(prepose);
    }

    public void update(Prepose prepose) {
        em.merge(prepose);
    }

    public void delete(Prepose prepose) {
        em.remove(prepose);
    }

    public Prepose findById(Long id) {
        return em.find(Prepose.class, id);
    }

    public List<Prepose> findAll() {
        return em.createQuery("SELECT p FROM Prepose p", Prepose.class).getResultList();
    }
}
