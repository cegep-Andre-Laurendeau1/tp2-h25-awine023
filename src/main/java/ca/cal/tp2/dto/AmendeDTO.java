package ca.cal.tp2.dto;

import ca.cal.tp2.model.Amende;
import java.util.Date;

public record AmendeDTO(Long id, double montant, boolean status, Date dateCreation, EmprunteurDTO emprunteur) {

    public static AmendeDTO fromEntity(Amende amende) {
        return new AmendeDTO(
                amende.getFineID(),
                amende.getMontant(),
                amende.isStatus(),
                amende.getDateCreation(),
                EmprunteurDTO.fromEntity(amende.getEmprunteur())
        );
    }
}
