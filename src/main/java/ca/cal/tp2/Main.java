package ca.cal.tp2;

import ca.cal.tp2.dto.EmprunteurDTO;
import ca.cal.tp2.dto.LivreDTO;
import ca.cal.tp2.dto.PreposeDTO;
import ca.cal.tp2.service.BibliothequeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        // 📌 Création de l'EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();

        // 📌 Initialisation du service
        BibliothequeService bibliothequeService = new BibliothequeService(em);

        try {
            System.out.println("=== 📚 Initialisation de la bibliothèque ===");

            // 📖 Ajouter un livre
            LivreDTO livre = bibliothequeService.ajouterLivre(
                    "Le Petit Prince", "Antoine de Saint-Exupéry", "123-ABC", 150, 3
            );
            System.out.println("✅ Livre ajouté avec succès : " + livre);

            // 👤 Ajouter un emprunteur
            EmprunteurDTO emprunteur = bibliothequeService.ajouterEmprunteur(
                    "Alice Dupont", "alice@example.com", "555-1234"
            );
            System.out.println("✅ Emprunteur ajouté avec succès : " + emprunteur);

            // 👨‍💼 Ajouter un préposé
            PreposeDTO prepose = bibliothequeService.ajouterPrepose(
                    "Jean Martin", "jean.martin@example.com", "555-5678"
            );
            System.out.println("✅ Préposé ajouté avec succès : " + prepose);

        } catch (Exception e) {
            System.err.println("❌ Erreur : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 📌 Fermeture des ressources
            em.close();
            emf.close();
        }
    }
}
