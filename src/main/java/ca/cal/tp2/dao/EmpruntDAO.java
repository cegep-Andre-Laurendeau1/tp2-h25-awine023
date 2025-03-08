package ca.cal.tp2.dao;

import ca.cal.tp2.model.Emprunt;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmpruntDAO {
    private final EntityManager em;

    public EmpruntDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Emprunt emprunt) {
        em.persist(emprunt);
    }

    public Emprunt findById(Long id) {
        return em.find(Emprunt.class, id);
    }

    public List<Emprunt> findAll() {
        return em.createQuery("SELECT e FROM Emprunt e", Emprunt.class).getResultList();
    }

    public void delete(Emprunt emprunt) {
        em.remove(emprunt);
    }
}
