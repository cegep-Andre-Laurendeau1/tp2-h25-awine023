package ca.cal.tp2;

import ca.cal.tp2.dto.*;
import ca.cal.tp2.service.BibliothequeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.SneakyThrows;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    @SneakyThrows
    public static void main(String[] args) throws SQLException {

        // 📌 Désactiver les logs Hibernate
        LogManager.getLogManager().reset();
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.h2").setLevel(Level.SEVERE);

        // 📌 Démarrer le serveur H2
        TcpServer.startTcpServer();

        // 📌 Initialisation de JPA/Hibernate
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliothequePU");
        EntityManager em = emf.createEntityManager();
        BibliothequeService bibliothequeService = new BibliothequeService(em);

        try {
            System.out.println("=== 📚 Initialisation de la bibliothèque ===");

            // 📌 1. Ajouter des livres
            LivreDTO livre1 = bibliothequeService.ajouterLivre("Le Petit Prince", "Antoine de Saint-Exupéry", "123-ABC", 150, 3);
            LivreDTO livre2 = bibliothequeService.ajouterLivre("1984", "George Orwell", "456-DEF", 328, 2);
            System.out.println("📖 Livres ajoutés :\n" + livre1 + "\n" + livre2);

            // 📌 2. Ajouter un CD
            CDDTO cd1 = bibliothequeService.ajouterCD("CD1", "Auteur1", 50, "Genre1", 3);
            System.out.println("\n🎵 CD ajouté : " + cd1);

            // 📌 3. Ajouter un emprunteur
            EmprunteurDTO emprunteur1 = bibliothequeService.ajouterEmprunteur("Alice Dupont", "alice@example.com", "555-1234");
            System.out.println("\n👤 Emprunteur ajouté : " + emprunteur1);

            // 📌 4. Ajouter un autre emprunteur (qui emprunte un CD)
            EmprunteurDTO emprunteur2 = bibliothequeService.ajouterEmprunteur("Bob Martin", "mail@gmail.com", "555-1234");
            System.out.println("\n👤 Emprunteur ajouté : " + emprunteur2);

            // 📌 5. Ajouter un préposé
            PreposeDTO prepose1 = bibliothequeService.ajouterPrepose("Jean Martin", "jean.martin@example.com", "555-5678");
            System.out.println("\n👨‍💼 Préposé ajouté : " + prepose1);

            // 📌 6. Lister les livres disponibles
            List<LivreDTO> livres = bibliothequeService.listerLivres();
            System.out.println("\n📚 Liste des livres disponibles : ");
            livres.forEach(System.out::println);

            // 📌 7. Emprunter un document
            EmpruntDTO emprunt = bibliothequeService.emprunterDocument(emprunteur1.id(), livre1.id(), new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
            System.out.println("\n📌 Document emprunté : " + emprunt);

            // 📌 8. Emprunter un CD
            EmpruntDTO empruntCD = bibliothequeService.emprunterDocument(emprunteur2.id(), cd1.id(), new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
            System.out.println("\n📌 CD emprunté : " + empruntCD);

            // 📌 9. Lister les emprunts
            List<EmpruntDTO> emprunts = bibliothequeService.listerEmprunts();
            System.out.println("\n📋 Liste des emprunts : ");
            emprunts.forEach(System.out::println);

            // 📌 10. Retourner un document (simulons un retour en retard)
            Thread.sleep(2000); // Simule un délai
            if (emprunt.details() != null && !emprunt.details().isEmpty()) {
                bibliothequeService.retournerDocument(emprunt.details().get(0).id());
                System.out.println("\n📌 Document retourné !");
            } else {
                System.out.println("\n⚠️ Aucun document à retourner.");
            }

            // 📌 11. Lister les amendes impayées
            List<AmendeDTO> amendes = bibliothequeService.listerAmendesNonPayees();
            System.out.println("\n💰 Liste des amendes impayées : ");
            if (amendes.isEmpty()) {
                System.out.println("✅ Aucune amende impayée.");
            } else {
                amendes.forEach(System.out::println);
            }

            // 📌 12. 🔥 TEST : Emprunter un document avec des ID inexistants !
            try {
                bibliothequeService.emprunterDocument(999L, 888L, new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
            } catch (Exception e) {
                System.err.println("❌ Erreur (ID inexistant) : " + e.getMessage());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("⚠️ Erreur d'interruption : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 📌 Fermeture de l'EntityManager
            em.close();
            emf.close();
        }

        Thread.currentThread().join();
    }
}
