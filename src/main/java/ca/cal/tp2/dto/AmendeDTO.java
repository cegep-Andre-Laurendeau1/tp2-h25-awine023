package ca.cal.tp2.dto;

import ca.cal.tp2.model.Amende;
import lombok.Builder;
import java.util.Date;

@Builder
public record AmendeDTO(Long id, double montant, boolean status, Date dateCreation, EmprunteurDTO emprunteur) {

    public static AmendeDTO fromEntity(Amende amende) {
        return AmendeDTO.builder()
                .id(amende.getFineID())
                .montant(amende.getMontant())
                .status(amende.isStatus())
                .dateCreation(amende.getDateCreation())
                .emprunteur(EmprunteurDTO.fromEntity(amende.getEmprunteur()))
                .build();
    }
}
