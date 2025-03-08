package ca.cal.tp2.dao;

import ca.cal.tp2.model.Livre;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LivreDAO {
    private final EntityManager em;

    public LivreDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Livre livre) {
        em.persist(livre);
    }

    public void update(Livre livre) {
        em.merge(livre);
    }

    public void delete(Livre livre) {
        em.remove(livre);
    }

    public Livre findById(Long id) {
        return em.find(Livre.class, id);
    }

    public List<Livre> findAll() {
        return em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
    }
}
