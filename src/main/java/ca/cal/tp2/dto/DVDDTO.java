package ca.cal.tp2.dto;

import ca.cal.tp2.model.DVD;

public record DVDDTO(Long id, String titre, String directeur, int duree, String rating, int nombreExemplaires) {

    public static DVDDTO fromEntity(DVD dvd) {
        return new DVDDTO(
                dvd.getDocumentID(),
                dvd.getTitre(),
                dvd.getDirecteur(),
                dvd.getDuree(),
                dvd.getRating(),
                dvd.getNombreExemplaires()
        );
    }
}
