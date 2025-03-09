package ca.cal.tp2.dto;

import ca.cal.tp2.model.Emprunteur;

public record EmprunteurDTO(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
    public static EmprunteurDTO fromEntity(Emprunteur emprunteur) {
        return new EmprunteurDTO(
                emprunteur.getUserID(),
                emprunteur.getName(),
                emprunteur.getEmail(),
                emprunteur.getPhoneNumber()
        );
    }
}
