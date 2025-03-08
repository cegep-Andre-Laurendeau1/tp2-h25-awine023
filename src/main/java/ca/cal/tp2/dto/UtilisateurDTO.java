package ca.cal.tp2.dto;

import ca.cal.tp2.model.Utilisateur;
import lombok.Builder;

@Builder
public record UtilisateurDTO(Long id, String name, String email, String phoneNumber, String typeUtilisateur) {

    public static UtilisateurDTO fromEntity(Utilisateur utilisateur) {
        return new UtilisateurDTO(
                utilisateur.getUserID(),
                utilisateur.getName(),
                utilisateur.getEmail(),
                utilisateur.getPhoneNumber(),
                utilisateur.getTypeUtilisateur()
        );
    }

    @Override
    public String toString() {
        return "UtilisateurDTO[id=" + id + ", name=" + name + ", email=" + email +
                ", phoneNumber=" + phoneNumber + ", typeUtilisateur=" + typeUtilisateur + "]";
    }
}
