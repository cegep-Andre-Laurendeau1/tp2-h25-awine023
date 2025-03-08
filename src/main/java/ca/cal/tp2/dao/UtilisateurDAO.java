package ca.cal.tp2.dao;

import ca.cal.tp2.model.Utilisateur;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UtilisateurDAO {
    private final EntityManager em;

    public UtilisateurDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    public Utilisateur findById(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public List<Utilisateur> findAll() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    public void delete(Utilisateur utilisateur) {
        em.remove(utilisateur);
    }
}
