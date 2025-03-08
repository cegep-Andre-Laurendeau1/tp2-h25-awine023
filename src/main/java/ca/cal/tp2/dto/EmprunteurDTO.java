package ca.cal.tp2.dto;

import ca.cal.tp2.model.Emprunteur;
import lombok.Builder;

@Builder
public record EmprunteurDTO(Long id, String name, String email, String phoneNumber) {

    public static EmprunteurDTO fromEntity(Emprunteur emprunteur) {
        return EmprunteurDTO.builder()
                .id(emprunteur.getUserID())
                .name(emprunteur.getName())
                .email(emprunteur.getEmail())
                .phoneNumber(emprunteur.getPhoneNumber())
                .build();
    }
}
