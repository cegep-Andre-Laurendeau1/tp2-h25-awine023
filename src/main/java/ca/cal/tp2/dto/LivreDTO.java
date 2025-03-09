package ca.cal.tp2.dto;

import ca.cal.tp2.model.Livre;
import java.util.Date;

public record LivreDTO(
        Long id,
        String titre,
        String auteur,
        String ISBN,
        int nombrePages,
        int nombreExemplaires,
        String editeur,
        Date datePublication
) {
    public static LivreDTO fromEntity(Livre livre) {
        return new LivreDTO(
                livre.getDocumentID(),
                livre.getTitre(),
                livre.getAuteur(),
                livre.getISBN(),
                livre.getNombrePages(),
                livre.getNombreExemplaires(),
                livre.getEditeur(),
                livre.getDatePublication()
        );
    }
}
