package ca.cal.tp2.dto;

import ca.cal.tp2.model.Document;
import lombok.Builder;

@Builder
public record DocumentDTO(Long id, String titre, int nombreExemplaires) {

    public static DocumentDTO fromEntity(Document document) {
        return DocumentDTO.builder()
                .id(document.getDocumentID())
                .titre(document.getTitre())
                .nombreExemplaires(document.getNombreExemplaires())
                .build();
    }
}
