package ca.cal.tp2.dto;

import lombok.Builder;
import java.util.List;
import java.util.Date;

@Builder
public record EmpruntDTO(Long id, Date dateEmprunt, String status, EmprunteurDTO emprunteur, List<EmpruntDetailDTO> details) {
}
