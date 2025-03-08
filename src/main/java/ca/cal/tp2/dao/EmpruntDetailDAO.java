package ca.cal.tp2.dao;

import ca.cal.tp2.model.EmpruntDetail;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EmpruntDetailDAO {
    private final EntityManager em;

    public EmpruntDetailDAO(EntityManager em) {
        this.em = em;
    }

    public void save(EmpruntDetail empruntDetail) {
        em.persist(empruntDetail);
    }

    public void update(EmpruntDetail empruntDetail) {
        em.merge(empruntDetail);
    }

    public void delete(EmpruntDetail empruntDetail) {
        em.remove(empruntDetail);
    }

    public EmpruntDetail findById(Long id) {
        return em.find(EmpruntDetail.class, id);
    }

    public List<EmpruntDetail> findAll() {
        return em.createQuery("SELECT e FROM EmpruntDetail e", EmpruntDetail.class).getResultList();
    }

    public List<EmpruntDetail> findByEmpruntId(Long empruntId) {
        return em.createQuery("SELECT e FROM EmpruntDetail e WHERE e.emprunt.borrowID = :empruntId", EmpruntDetail.class)
                .setParameter("empruntId", empruntId)
                .getResultList();
    }
}
