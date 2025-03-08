package ca.cal.tp2;

import ca.cal.tp2.dto.LivreDTO;
import ca.cal.tp2.service.BibliothequeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();

        BibliothequeService bibliothequeService = new BibliothequeService(em);

        try {
            // Ajouter un livre via le service
            LivreDTO livre = bibliothequeService.ajouterLivre(
                    "Le Petit Prince", "Antoine de Saint-Exupéry", "123-ABC", 150, 3
            );

            // Affichage du résultat
            System.out.println("✅ Livre ajouté avec succès : " + livre);

        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Fermeture des ressources
            em.close();
            emf.close();
        }
    }
}
