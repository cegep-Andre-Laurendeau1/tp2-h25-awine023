package ca.cal.tp2.dto;

import ca.cal.tp2.model.Document;

public record DocumentDTO(Long id, String titre, int nombreExemplaires) {

    public static DocumentDTO fromEntity(Document document) {
        return new DocumentDTO(
                document.getDocumentID(),
                document.getTitre(),
                document.getNombreExemplaires()
        );
    }
}
