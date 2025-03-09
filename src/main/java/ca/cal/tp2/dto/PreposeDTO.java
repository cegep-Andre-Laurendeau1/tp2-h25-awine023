package ca.cal.tp2.dto;

import ca.cal.tp2.model.Utilisateur;

public record PreposeDTO(
        Long id,
        String name,
        String email,
        String phoneNumber
) {
    public static PreposeDTO fromEntity(Utilisateur utilisateur) {
        if (!"PREPOSE".equals(utilisateur.getTypeUtilisateur())) {
            throw new IllegalArgumentException("L'utilisateur n'est pas un préposé.");
        }
        return new PreposeDTO(
                utilisateur.getUserID(),
                utilisateur.getName(),
                utilisateur.getEmail(),
                utilisateur.getPhoneNumber()
        );
    }

    @Override
    public String toString() {
        return "PreposeDTO[id=" + id +
                ", name=" + name +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber + "]";
    }
}
