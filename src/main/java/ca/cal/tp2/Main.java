package ca.cal.tp2;

import ca.cal.tp2.dao.*;
import ca.cal.tp2.dto.*;
import ca.cal.tp2.service.BibliothequeService;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    @SneakyThrows
    public static void main(String[] args) throws SQLException {

        LogManager.getLogManager().reset();
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Logger.getLogger("org.h2").setLevel(Level.SEVERE);

        TcpServer.startTcpServer();

        DocumentDAO documentDAO = new DocumentDAOImpl();
        EmpruntDAO empruntDAO = new EmpruntDAOImpl();
        UtilisateurDAO utilisateurDAO = new UtilisateurDAOImpl();

        BibliothequeService bibliothequeService = new BibliothequeService(documentDAO, empruntDAO, utilisateurDAO);

        try {
            System.out.println("=== 📚 Initialisation de la bibliothèque ===");

            // 📌 Ajouter des livres
            LivreDTO livre1 = bibliothequeService.ajouterLivre(new LivreDTO(null, "Le Petit Prince", "Antoine de Saint-Exupéry", "123-ABC", 150, 3, "Gallimard", new Date()));
            LivreDTO livre2 = bibliothequeService.ajouterLivre(new LivreDTO(null, "1984", "George Orwell", "456-DEF", 328, 2, "Seuil", new Date()));
            System.out.println("📖 Livres ajoutés :\n" + livre1 + "\n" + livre2);

            // 📌 Ajouter un DVD
            DVDDTO dvd1 = bibliothequeService.ajouterDVD(new DVDDTO(null, "Inception", "Christopher Nolan", 148, "PG-13", 5));
            System.out.println("\n📀 DVD ajouté : " + dvd1);

            // 📌 Ajouter un CD
            CDDTO cd1 = bibliothequeService.ajouterCD(new CDDTO(null, "Thriller", "Michael Jackson", 42, "Pop", 5));
            System.out.println("\n🎵 CD ajouté : " + cd1);

            // 📌 Ajouter un emprunteur
            EmprunteurDTO emprunteur1 = bibliothequeService.ajouterEmprunteur(new EmprunteurDTO(null, "Alice Dupont", "alice@example.com", "555-1234"));
            System.out.println("\n👤 Emprunteur ajouté : " + emprunteur1);

            // 📌 Ajouter un préposé
            PreposeDTO prepose1 = bibliothequeService.ajouterPrepose(new PreposeDTO(null, "Jean Martin", "jean.martin@example.com", "555-5678"));
            System.out.println("\n👨‍💼 Préposé ajouté : " + prepose1);

            // 📌 Liste des documents disponibles
            List<DocumentDTO> documents = bibliothequeService.getAllDocuments();
            System.out.println("\n📚 Liste des documents disponibles : ");
            documents.forEach(System.out::println);

            // 📌 Emprunter un livre
            EmpruntDTO empruntLivre = bibliothequeService.emprunterDocument(emprunteur1.id(), livre1.id());
            System.out.println("\n📌 Livre emprunté : " + empruntLivre);

            // 📌 Emprunter un CD
            EmpruntDTO empruntCD = bibliothequeService.emprunterDocument(emprunteur1.id(), cd1.id());
            System.out.println("\n📌 CD emprunté : " + empruntCD);

            // 📌 Emprunter un document en cours (non retourné)
            EmpruntDTO empruntEnCours = bibliothequeService.emprunterDocument(emprunteur1.id(), dvd1.id());
            System.out.println("\n📌 Document en cours d'emprunt (non retourné) : " + empruntEnCours);

            // 📌 Liste des emprunts mis à jour
            List<EmpruntDTO> emprunts = bibliothequeService.getEmpruntsParEmprunteur(emprunteur1.id());
            System.out.println("\n📋 Liste des emprunts mis à jour : ");
            emprunts.forEach(System.out::println);

            // 📌 Retourner le livre
            if (empruntLivre.details() != null && !empruntLivre.details().isEmpty()) {
                boolean retourReussi = bibliothequeService.retournerDocument(empruntLivre.details().get(0).id());
                System.out.println("\n📌 Livre retourné : " + (retourReussi ? "✔ Succès" : "❌ Échec"));
            } else {
                System.out.println("\n⚠️ Aucun livre à retourner.");
            }

            // 📌 Retourner le CD
            if (empruntCD.details() != null && !empruntCD.details().isEmpty()) {
                boolean retourReussi = bibliothequeService.retournerDocument(empruntCD.details().get(0).id());
                System.out.println("\n📌 CD retourné : " + (retourReussi ? "✔ Succès" : "❌ Échec"));
            } else {
                System.out.println("\n⚠️ Aucun CD à retourner.");
            }

            // 📌 Ne pas retourner le document en cours (Dvd)
            System.out.println("\n📌 Le document '" + dvd1.titre() + "' reste en cours d'emprunt.");

            // 📌 Recherche de documents par titre
            System.out.println("\n🔍 Recherche de documents contenant 'Prince' :");
            List<DocumentDTO> resultatsTitre = bibliothequeService.rechercherParTitre("Prince");
            resultatsTitre.forEach(System.out::println);

            // 📌 Recherche de livres par auteur
            System.out.println("\n🔍 Recherche de livres écrits par 'Saint-Exupéry' :");
            List<LivreDTO> resultatsAuteur = bibliothequeService.rechercherLivresParAuteur("Saint-Exupéry");
            resultatsAuteur.forEach(System.out::println);

            // 📌 Recherche de DVD par réalisateur
            System.out.println("\n🔍 Recherche de DVD réalisés par 'Nolan' :");
            List<DVDDTO> resultatsRealisateur = bibliothequeService.rechercherDVDsParDirecteur("Nolan");
            resultatsRealisateur.forEach(System.out::println);

            // 📌 Recherche de CD par artiste
            System.out.println("\n🔍 Recherche de CD contenant 'Michael' :");
            List<CDDTO> resultatsCD = bibliothequeService.rechercherCDsParArtiste("Michael");
            resultatsCD.forEach(System.out::println);

        } catch (Exception e) {
            System.err.println("❌ Une erreur est survenue : " + e.getMessage());
            e.printStackTrace();
        } finally {
            EmParentDAO.closeEntityManagerFactory();
        }
    }
}
