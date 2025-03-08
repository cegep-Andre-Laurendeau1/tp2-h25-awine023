package ca.cal.tp2.dto;

import ca.cal.tp2.model.EmpruntDetail;
import lombok.Builder;
import java.util.Date;

@Builder
public record EmpruntDetailDTO(Long id, Date dateRetourPrevue, Date dateRetourActuelle, String status, DocumentDTO document) {

    public static EmpruntDetailDTO fromEntity(EmpruntDetail empruntDetail) {
        return EmpruntDetailDTO.builder()
                .id(empruntDetail.getLineItemID())
                .dateRetourPrevue(empruntDetail.getDateRetourPrevue())
                .dateRetourActuelle(empruntDetail.getDateRetourActuelle())
                .status(empruntDetail.getStatus())
                .document(DocumentDTO.fromEntity(empruntDetail.getDocument()))
                .build();
    }
}
