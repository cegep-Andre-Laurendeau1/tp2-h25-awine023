package ca.cal.tp2.dao;

import ca.cal.tp2.model.Emprunteur;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmprunteurDAO {
    private final EntityManager em;

    public EmprunteurDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Emprunteur emprunteur) {
        em.persist(emprunteur);
    }

    public Emprunteur findById(Long id) {
        return em.find(Emprunteur.class, id);
    }

    public List<Emprunteur> findAll() {
        return em.createQuery("SELECT e FROM Emprunteur e", Emprunteur.class).getResultList();
    }

    public void delete(Emprunteur emprunteur) {
        em.remove(emprunteur);
    }
}
