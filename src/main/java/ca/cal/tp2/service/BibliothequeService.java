package ca.cal.tp2.service;

import ca.cal.tp2.dao.*;
import ca.cal.tp2.dto.*;
import ca.cal.tp2.model.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BibliothequeService {
    private final DocumentDAO documentDAO;
    private final EmpruntDAO empruntDAO;
    private final UtilisateurDAO utilisateurDAO;

    public BibliothequeService(DocumentDAO documentDAO, EmpruntDAO empruntDAO, UtilisateurDAO utilisateurDAO) {
        this.documentDAO = documentDAO;
        this.empruntDAO = empruntDAO;
        this.utilisateurDAO = utilisateurDAO;
    }

    public LivreDTO ajouterLivre(LivreDTO livreDTO) {
        Livre livre = new Livre(
                livreDTO.titre(),
                livreDTO.auteur(),
                livreDTO.ISBN(),
                livreDTO.nombrePages(),
                livreDTO.nombreExemplaires(),
                livreDTO.editeur(),
                livreDTO.datePublication()
        );

        documentDAO.ajouterLivre(livre);
        return LivreDTO.fromEntity(livre);
    }

    public CDDTO ajouterCD(CDDTO cdDTO) {
        CD cd = new CD(
                cdDTO.titre(),
                cdDTO.artiste(),
                cdDTO.duree(),
                cdDTO.genre(),
                cdDTO.nombreExemplaires()
        );
        documentDAO.ajouterCD(cd);
        return CDDTO.fromEntity(cd);
    }

    public DVDDTO ajouterDVD(DVDDTO dvdDTO) {
        DVD dvd = new DVD(
                dvdDTO.titre(),
                dvdDTO.nombreExemplaires(),
                dvdDTO.directeur(),
                dvdDTO.duree(),
                dvdDTO.rating()
        );
        documentDAO.ajouterDVD(dvd);
        return DVDDTO.fromEntity(dvd);
    }

    public EmprunteurDTO ajouterEmprunteur(EmprunteurDTO emprunteurDTO) {
        Emprunteur emprunteur = new Emprunteur(
                emprunteurDTO.name(),
                emprunteurDTO.email(),
                emprunteurDTO.phoneNumber()
        );
        utilisateurDAO.ajouterEmprunteur(emprunteur);
        return EmprunteurDTO.fromEntity(emprunteur);
    }

    public PreposeDTO ajouterPrepose(PreposeDTO preposeDTO) {
        Prepose prepose = new Prepose(
                preposeDTO.name(),
                preposeDTO.email(),
                preposeDTO.phoneNumber()
        );
        utilisateurDAO.ajouterPrepose(prepose);
        return PreposeDTO.fromEntity(prepose);
    }

    public List<DocumentDTO> getAllDocuments() {
        return documentDAO.findAll().stream()
                .map(DocumentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<EmprunteurDTO> getAllEmprunteurs() {
        return utilisateurDAO.getEmprunteurs().stream()
                .map(EmprunteurDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public EmpruntDTO emprunterDocument(long emprunteurId, long documentId) {
        Emprunteur emprunteur = utilisateurDAO.getEmprunteurById(emprunteurId);
        Document document = documentDAO.findById(documentId);

        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur introuvable !");
        }
        if (document == null || document.getNombreExemplaires() <= 0) {
            throw new RuntimeException("Document indisponible !");
        }

        Emprunt emprunt = empruntDAO.creerEmprunt(emprunteur, new Date());
        EmpruntDetail detail = empruntDAO.ajouterDocumentAEmprunt(emprunt, document, new Date());

        return new EmpruntDTO(
                emprunt.getBorrowID(),
                emprunt.getDateEmprunt(),
                emprunt.getStatus(),
                EmprunteurDTO.fromEntity(emprunteur),
                List.of(EmpruntDetailDTO.fromEntity(detail))
        );
    }


    public List<EmpruntDTO> getEmpruntsParEmprunteur(long emprunteurId) {
        return EmParentDAO.executeInTransaction(em -> {
            List<Emprunt> emprunts = em.createQuery(
                            "SELECT e FROM Emprunt e LEFT JOIN FETCH e.empruntDetails WHERE e.emprunteur.id = :emprunteurId",
                            Emprunt.class)
                    .setParameter("emprunteurId", emprunteurId)
                    .getResultList();

            return emprunts.stream()
                    .map(emprunt -> new EmpruntDTO(
                            emprunt.getBorrowID(),
                            emprunt.getDateEmprunt(),
                            emprunt.getStatus(),
                            EmprunteurDTO.fromEntity(emprunt.getEmprunteur()),
                            emprunt.getEmpruntDetails().stream()
                                    .map(EmpruntDetailDTO::fromEntity)
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
        });
    }


    public boolean retournerDocument(long empruntDetailId) {
        return EmParentDAO.executeInTransaction(em -> {
            EmpruntDetail detail = em.find(EmpruntDetail.class, empruntDetailId);
            if (detail == null) {
                throw new RuntimeException("EmpruntDetail introuvable !");
            }

            detail.setDateRetourActuelle(new Date());
            detail.setStatus("RETORNE");
            em.merge(detail);

            Emprunt emprunt = detail.getEmprunt();
            boolean tousRetournes = emprunt.getEmpruntDetails().stream()
                    .allMatch(d -> "RETORNE".equals(d.getStatus()));

            if (tousRetournes) {
                emprunt.setStatus("RETORNE");
                em.merge(emprunt);
            }

            return true;
        });
    }

    public List<DocumentDTO> rechercherParTitre(String titre) {
        return documentDAO.rechercherParTitre(titre).stream()
                .map(DocumentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<LivreDTO> rechercherLivresParAuteur(String auteur) {
        return documentDAO.rechercherLivresParAuteur(auteur).stream()
                .map(LivreDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<CDDTO> rechercherCDsParArtiste(String artiste) {
        return documentDAO.rechercherCDsParArtiste(artiste).stream()
                .map(CDDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<DVDDTO> rechercherDVDsParDirecteur(String directeur) {
        return documentDAO.rechercherDVDsParDirecteur(directeur).stream()
                .map(DVDDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
