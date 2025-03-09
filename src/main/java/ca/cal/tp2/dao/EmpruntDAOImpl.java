package ca.cal.tp2.dao;

import ca.cal.tp2.model.Emprunt;
import ca.cal.tp2.model.EmpruntDetail;
import ca.cal.tp2.model.Document;
import ca.cal.tp2.model.Emprunteur;
import static ca.cal.tp2.dao.EmParentDAO.executeInTransaction;
import java.util.Date;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {

    @Override
    public List<Emprunt> getEmprunts() {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM Emprunt e", Emprunt.class).getResultList()
        );
    }

    @Override
    public Emprunt creerEmprunt(Emprunteur client, Date dateDebut) {
        return executeInTransaction(entityManager -> {
            Emprunteur clientTrouve = entityManager.find(Emprunteur.class, client.getUserID());
            if (clientTrouve == null) {
                throw new RuntimeException("Client inexistant : " + client.getUserID());
            }

            Emprunt nouvelEmprunt = new Emprunt();
            nouvelEmprunt.setEmprunteur(clientTrouve);
            nouvelEmprunt.setDateEmprunt(dateDebut);
            nouvelEmprunt.setStatus("EN COURS");

            entityManager.persist(nouvelEmprunt);
            return nouvelEmprunt;
        });
    }

    @Override
    public EmpruntDetail ajouterDocumentAEmprunt(Emprunt emprunt, Document fichier, Date datePrevueRetour) {
        return executeInTransaction(entityManager -> {
            Emprunt empruntAssocie = entityManager.find(Emprunt.class, emprunt.getBorrowID());
            if (empruntAssocie == null) {
                throw new IllegalArgumentException("Aucun emprunt trouvé pour l'ID : " + emprunt.getBorrowID());
            }

            Document fichierTrouve = entityManager.find(Document.class, fichier.getDocumentID());
            if (fichierTrouve == null) {
                throw new IllegalArgumentException("Document introuvable avec ID : " + fichier.getDocumentID());
            }

            if (fichierTrouve.getNombreExemplaires() <= 0) {
                throw new IllegalStateException("Stock insuffisant pour : " + fichierTrouve.getTitre());
            }

            fichierTrouve.setNombreExemplaires(fichierTrouve.getNombreExemplaires() - 1);

            EmpruntDetail detailEmprunt = new EmpruntDetail();
            detailEmprunt.setEmprunt(empruntAssocie);
            detailEmprunt.setDocument(fichierTrouve);
            detailEmprunt.setDateRetourPrevue(datePrevueRetour);
            detailEmprunt.setStatus("EN PRÊT");

            entityManager.persist(detailEmprunt);
            return detailEmprunt;
        });
    }

    @Override
    public List<Emprunt> getEmpruntsParEmprunteur(long clientId) {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT e FROM Emprunt e WHERE e.emprunteur.id = :clientId", Emprunt.class)
                        .setParameter("clientId", clientId)
                        .getResultList()
        );
    }

    @Override
    public Emprunt getEmpruntById(long idEmprunt) {
        return executeInTransaction(entityManager -> entityManager.find(Emprunt.class, idEmprunt));
    }

    @Override
    public List<EmpruntDetail> getDetailsEmprunt(long idEmprunt) {
        return executeInTransaction(entityManager ->
                entityManager.createQuery("SELECT ed FROM EmpruntDetail ed WHERE ed.emprunt.id = :idEmprunt", EmpruntDetail.class)
                        .setParameter("idEmprunt", idEmprunt)
                        .getResultList()
        );
    }

    @Override
    public boolean enregistrerRetour(long idDetailEmprunt, Date dateDeRetour) {
        return executeInTransaction(entityManager -> {
            EmpruntDetail detail = entityManager.find(EmpruntDetail.class, idDetailEmprunt);
            if (detail == null) {
                return false;
            }

            detail.setDateRetourActuelle(dateDeRetour);
            detail.setStatus("RENDUE");

            Document fichier = detail.getDocument();
            fichier.setNombreExemplaires(fichier.getNombreExemplaires() + 1);

            Emprunt empruntAssocie = detail.getEmprunt();
            boolean toutRetourne = empruntAssocie.getEmpruntDetails().stream()
                    .allMatch(d -> "RENDUE".equals(d.getStatus()));

            if (toutRetourne) {
                empruntAssocie.setStatus("CLOS");
                entityManager.merge(empruntAssocie);
            }

            return true;
        });
    }

    @Override
    public boolean updateStatutEmprunt(long idEmprunt, String statut) {
        return executeInTransaction(entityManager -> {
            Emprunt empruntModifie = entityManager.find(Emprunt.class, idEmprunt);
            if (empruntModifie == null) {
                return false;
            }

            empruntModifie.setStatus(statut);
            return true;
        });
    }
}
