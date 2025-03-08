package ca.cal.tp2.dto;

import ca.cal.tp2.model.Livre;
import lombok.Builder;

@Builder
public record LivreDTO(Long id, String titre, String auteur, String ISBN, int nombrePages, int nombreExemplaires) {

    public static LivreDTO fromEntity(Livre livre) {
        return LivreDTO.builder()
                .id(livre.getDocumentID())
                .titre(livre.getTitre())
                .auteur(livre.getAuteur())
                .ISBN(livre.getISBN())
                .nombrePages(livre.getNombrePages())
                .nombreExemplaires(livre.getNombreExemplaires())
                .build();
    }
}
