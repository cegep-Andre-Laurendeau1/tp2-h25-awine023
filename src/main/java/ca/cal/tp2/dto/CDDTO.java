package ca.cal.tp2.dto;

import lombok.Builder;

@Builder
public record CDDTO(Long id, String titre, String artiste, int duree, String genre) {
}
