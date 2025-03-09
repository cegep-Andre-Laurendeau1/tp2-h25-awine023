package ca.cal.tp2.dto;

import ca.cal.tp2.model.EmpruntDetail;
import java.util.Date;

public record EmpruntDetailDTO(
        Long id,
        Date dateRetourPrevue,
        Date dateRetourActuelle,
        String status,
        DocumentDTO document
) {
    public static EmpruntDetailDTO fromEntity(EmpruntDetail empruntDetail) {
        return new EmpruntDetailDTO(
                empruntDetail.getLineItemID(),
                empruntDetail.getDateRetourPrevue(),
                empruntDetail.getDateRetourActuelle(),
                empruntDetail.getStatus(),
                DocumentDTO.fromEntity(empruntDetail.getDocument())
        );
    }
}
