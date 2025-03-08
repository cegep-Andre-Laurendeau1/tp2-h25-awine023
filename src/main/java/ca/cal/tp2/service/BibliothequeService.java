package ca.cal.tp2.service;

import ca.cal.tp2.dao.*;
import ca.cal.tp2.dto.*;
import ca.cal.tp2.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BibliothequeService {
    private final LivreDAO livreDAO;
    private final CDDAO cdDAO;
    private final DVDDAO dvdDAO;
    private final EmpruntDAO empruntDAO;
    private final EmpruntDetailDAO empruntDetailDAO;
    private final AmendeDAO amendeDAO;
    private final UtilisateurDAO utilisateurDAO;
    private final EntityManager em;

    public BibliothequeService(EntityManager em) {
        this.em = em;
        this.livreDAO = new LivreDAO(em);
        this.cdDAO = new CDDAO(em);
        this.dvdDAO = new DVDDAO(em);
        this.empruntDAO = new EmpruntDAO(em);
        this.empruntDetailDAO = new EmpruntDetailDAO(em);
        this.amendeDAO = new AmendeDAO(em);
        this.utilisateurDAO = new UtilisateurDAO(em);
    }

    public LivreDTO ajouterLivre(String titre, String auteur, String ISBN, int nombrePages, int nbExemplaires) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Livre livre = new Livre();
            livre.setTitre(titre);
            livre.setAuteur(auteur);
            livre.setISBN(ISBN);
            livre.setNombrePages(nombrePages);
            livre.setNombreExemplaires(nbExemplaires);
            livreDAO.save(livre);
            tx.commit();
            return LivreDTO.fromEntity(livre);
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }

    public EmprunteurDTO ajouterEmprunteur(String name, String email, String phoneNumber) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Emprunteur emprunteur = new Emprunteur(name, email, phoneNumber);
            em.persist(emprunteur);
            tx.commit();
            return EmprunteurDTO.fromEntity(emprunteur);
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout de l'emprunteur : " + e.getMessage());
        }
    }

    public PreposeDTO ajouterPrepose(String name, String email, String phoneNumber) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Prepose prepose = new Prepose(name, email, phoneNumber);
            em.persist(prepose);
            tx.commit();
            return PreposeDTO.fromEntity(prepose); // ✅ Correction ici
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout du préposé : " + e.getMessage());
        }
    }


    public EmpruntDTO emprunterDocument(Long emprunteurId, Long documentId, Date dateRetourPrevue) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Emprunteur emprunteur = em.find(Emprunteur.class, emprunteurId);
            Document document = em.find(Document.class, documentId);

            if (emprunteur == null || document == null) {
                tx.rollback();
                throw new RuntimeException("Emprunt impossible : Emprunteur ou document introuvable.");
            }

            if (document.getNombreExemplaires() <= 0) {
                tx.rollback();
                throw new RuntimeException("Emprunt impossible : Plus d'exemplaires disponibles.");
            }

            Emprunt emprunt = new Emprunt();
            emprunt.setDateEmprunt(new Date());
            emprunt.setStatus("En cours");
            emprunt.setEmprunteur(emprunteur);
            empruntDAO.save(emprunt);

            EmpruntDetail detail = new EmpruntDetail();
            detail.setEmprunt(emprunt);
            detail.setDocument(document);
            detail.setDateRetourPrevue(dateRetourPrevue);
            detail.setStatus("Non retourné");
            empruntDetailDAO.save(detail);

            tx.commit();
            return EmpruntDTO.builder()
                    .id(emprunt.getBorrowID())
                    .dateEmprunt(emprunt.getDateEmprunt())
                    .status(emprunt.getStatus())
                    .emprunteur(EmprunteurDTO.fromEntity(emprunteur))
                    .details(List.of(EmpruntDetailDTO.fromEntity(detail)))
                    .build();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors de l'emprunt : " + e.getMessage());
        }
    }

    public void retournerDocument(Long empruntDetailId) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            EmpruntDetail detail = em.find(EmpruntDetail.class, empruntDetailId);

            if (detail == null) {
                tx.rollback();
                throw new RuntimeException("Retour impossible : Détail de l'emprunt introuvable.");
            }

            detail.setDateRetourActuelle(new Date());
            detail.setStatus("Retourné");
            em.merge(detail);

            Document document = detail.getDocument();
            document.setNombreExemplaires(document.getNombreExemplaires() + 1);
            em.merge(document);

            Emprunt emprunt = detail.getEmprunt();
            boolean tousRetournes = emprunt.getEmpruntDetails().stream()
                    .allMatch(d -> d.getStatus().equals("Retourné"));

            if (tousRetournes) {
                emprunt.setStatus("Terminé");
                em.merge(emprunt);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors du retour du document : " + e.getMessage());
        }
    }


    public List<EmpruntDTO> listerEmprunts() {
        return empruntDAO.findAll().stream()
                .map(emprunt -> EmpruntDTO.builder()
                        .id(emprunt.getBorrowID())
                        .dateEmprunt(emprunt.getDateEmprunt())
                        .status(emprunt.getStatus())
                        .emprunteur(convertirEnEmprunteurDTO(emprunt.getEmprunteur()))
                        .details(emprunt.getEmpruntDetails().stream()
                                .map(EmpruntDetailDTO::fromEntity)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }


    private EmprunteurDTO convertirEnEmprunteurDTO(Utilisateur utilisateur) {
        if (utilisateur instanceof Emprunteur emprunteur) {
            return EmprunteurDTO.fromEntity(emprunteur); // ✅ Convertir en DTO d'emprunteur
        } else {
            throw new RuntimeException("L'utilisateur associé à l'emprunt n'est pas un emprunteur !");
        }
    }



    public List<AmendeDTO> listerAmendesNonPayees() {
        return amendeDAO.findAll().stream()
                .filter(amende -> !amende.isStatus())
                .map(AmendeDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<LivreDTO> listerLivres() {
        return livreDAO.findAll().stream()
                .map(LivreDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public CDDTO ajouterCD(String titre, String artiste, int duree, String genre, int nbExemplaires) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            CD cd = new CD();
            cd.setTitre(titre);
            cd.setArtiste(artiste);
            cd.setDuree(duree);
            cd.setGenre(genre);
            cd.setNombreExemplaires(nbExemplaires);
            cdDAO.save(cd);
            tx.commit();
            return new CDDTO(cd.getDocumentID(), titre, artiste, duree, genre);
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout du CD : " + e.getMessage());
        }
    }

}
