package ca.cal.tp2;

import ca.cal.tp2.model.Livre;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Création d'un livre
        Livre livre = new Livre();
        livre.setTitre("Le Petit Prince");
        livre.setAuteur("Antoine de Saint-Exupéry");
        livre.setISBN("123-ABC");
        livre.setNombrePages(150);
        livre.setNombreExemplaires(3);

        em.persist(livre);
        em.getTransaction().commit();

        System.out.println("📖 Livre ajouté avec succès : " + livre);

        em.close();
        emf.close();
    }
}
