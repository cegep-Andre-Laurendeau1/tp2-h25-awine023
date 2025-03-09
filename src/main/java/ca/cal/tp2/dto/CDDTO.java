package ca.cal.tp2.dto;

import ca.cal.tp2.model.CD;

public record CDDTO(Long id, String titre, String artiste, int duree, String genre, int nombreExemplaires) {

    public static CDDTO fromEntity(CD cd) {
        return new CDDTO(
                cd.getDocumentID(),
                cd.getTitre(),
                cd.getArtiste(),
                cd.getDuree(),
                cd.getGenre(),
                cd.getNombreExemplaires()
        );
    }
}
