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
            LivreDTO livre1 = bibliothequeService.ajouterLivre("Le Petit Prince", "Antoine de Saint-Exupéry", "123-ABC", 150, 3, "Gallimard");
            LivreDTO livre2 = bibliothequeService.ajouterLivre("1984", "George Orwell", "456-DEF", 328, 2, "Seuil");
            System.out.println("📖 Livres ajoutés :\n" + livre1 + "\n" + livre2);

            // 📌 2. Ajouter un DVD
            DVDDTO dvd1 = bibliothequeService.ajouterDVD("Inception", "Christopher Nolan", 148, "PG-13");
            System.out.println("\n📀 DVD ajouté : " + dvd1);

            // 📌 Ajouter un CD
            CDDTO cd1 = bibliothequeService.ajouterCD("Thriller", "Michael Jackson", 42, "Pop", 5);
            System.out.println("\n🎵 CD ajouté : " + cd1);

            // 📌 3. Ajouter un emprunteur
            EmprunteurDTO emprunteur1 = bibliothequeService.ajouterEmprunteur("Alice Dupont", "alice@example.com", "555-1234");
            System.out.println("\n👤 Emprunteur ajouté : " + emprunteur1);

            // 📌 4. Ajouter un préposé
            PreposeDTO prepose1 = bibliothequeService.ajouterPrepose("Jean Martin", "jean.martin@example.com", "555-5678");
            System.out.println("\n👨‍💼 Préposé ajouté : " + prepose1);

            // 📌 5. Lister les livres disponibles
            List<LivreDTO> livres = bibliothequeService.listerLivres();
            System.out.println("\n📚 Liste des livres disponibles : ");
            livres.forEach(System.out::println);

            // 📌 6. Emprunter un document
            EmpruntDTO emprunt = bibliothequeService.emprunterDocument(emprunteur1.id(), livre1.id(), new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
            System.out.println("\n📌 Document emprunté : " + emprunt);

            // 📌 7. Lister les emprunts
            List<EmpruntDTO> emprunts = bibliothequeService.listerEmprunts();
            System.out.println("\n📋 Liste des emprunts : ");
            emprunts.forEach(System.out::println);

            // 📌 8. Retourner un document
            if (emprunt.details() != null && !emprunt.details().isEmpty()) {
                bibliothequeService.retournerDocument(emprunt.details().get(0).id());
                System.out.println("\n📌 Document retourné !");
            } else {
                System.out.println("\n⚠️ Aucun document à retourner.");
            }

            // 📌 9. Lister les amendes impayées
            List<AmendeDTO> amendes = bibliothequeService.listerAmendesNonPayees();
            System.out.println("\n💰 Liste des amendes impayées : ");
            if (amendes.isEmpty()) {
                System.out.println("✅ Aucune amende impayée.");
            } else {
                amendes.forEach(System.out::println);
            }

            // 📌 10. 🔍 Recherche de livres par titre ou auteur
            System.out.println("\n🔍 Recherche de livres contenant 'Prince' ou 'Saint-Exupéry' :");
            List<LivreDTO> resultatsTitreAuteur = bibliothequeService.rechercherLivresParTitreOuAuteur("Prince");
            resultatsTitreAuteur.forEach(System.out::println);

            // 📌 11. 🔍 Recherche de DVD par réalisateur
            System.out.println("\n🔍 Recherche de DVD réalisés par 'Nolan' :");
            List<DVDDTO> resultatsRealisateur = bibliothequeService.rechercherDVDParTitreOuRealisateur("Nolan");
            resultatsRealisateur.forEach(System.out::println);

            // 📌 Recherche d'un CD par titre ou artiste
            System.out.println("\n🔍 Recherche de CD contenant 'Michael' :");
            List<CDDTO> resultatsCD = bibliothequeService.rechercherCDParTitreOuArtiste("Michael");
            resultatsCD.forEach(System.out::println);

            // 📌 Récupérer les dates de retour des emprunts d'un emprunteur
            System.out.println("\n📅 Dates de retour des emprunts pour " + emprunteur1.name() + " :");
            List<EmpruntDetailDTO> datesRetour = bibliothequeService.obtenirDatesRetourParEmprunteur(emprunteur1.id());
            datesRetour.forEach(ed -> System.out.println("📅 Retour prévu : " + ed.dateRetourPrevue()));


            // 📌 12. 🔥 TEST : Emprunter un document avec des ID inexistants !
            try {
                bibliothequeService.emprunterDocument(999L, 888L, new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000));
            } catch (Exception e) {
                System.err.println("❌ Erreur (ID inexistant) : " + e.getMessage());
            }

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
