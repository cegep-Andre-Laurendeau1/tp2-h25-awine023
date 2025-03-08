package ca.cal.tp2.dao;

import ca.cal.tp2.model.Document;
import jakarta.persistence.EntityManager;
import java.util.List;

public class DocumentDAO {
    private final EntityManager em;

    public DocumentDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Document document) {
        em.persist(document);
    }

    public Document findById(Long id) {
        return em.find(Document.class, id);
    }

    public List<Document> findAll() {
        return em.createQuery("SELECT d FROM Document d", Document.class).getResultList();
    }

    public void delete(Document document) {
        em.remove(document);
    }
}
