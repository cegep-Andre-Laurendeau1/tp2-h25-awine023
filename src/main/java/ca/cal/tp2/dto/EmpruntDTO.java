package ca.cal.tp2.dto;

import java.util.List;
import java.util.Date;

public record EmpruntDTO(
        Long id,
        Date dateEmprunt,
        String status,
        EmprunteurDTO emprunteur,
        List<EmpruntDetailDTO> details
) {
}
