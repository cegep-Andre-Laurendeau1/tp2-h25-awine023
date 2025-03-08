package ca.cal.tp2.dao;

import ca.cal.tp2.model.Amende;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AmendeDAO {
    private final EntityManager em;

    public AmendeDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Amende amende) {
        em.persist(amende);
    }

    public Amende findById(Long id) {
        return em.find(Amende.class, id);
    }

    public List<Amende> findAll() {
        return em.createQuery("SELECT a FROM Amende a", Amende.class).getResultList();
    }

    public void delete(Amende amende) {
        em.remove(amende);
    }
}
