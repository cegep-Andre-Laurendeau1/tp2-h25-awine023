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

    private final UtilisateurDAO utilisateurDAO;
    private final EntityManager em;

    public BibliothequeService(EntityManager em) {
        this.em = em;
        this.livreDAO = new LivreDAO(em);

        this.utilisateurDAO = new UtilisateurDAO(em);
    }

    // 📌 Ajouter un livre
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
            return PreposeDTO.fromEntity(prepose);
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException("Erreur lors de l'ajout du préposé : " + e.getMessage());
        }
    }














}
