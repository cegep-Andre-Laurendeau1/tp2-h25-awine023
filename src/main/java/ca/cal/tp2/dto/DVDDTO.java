package ca.cal.tp2.dto;

import lombok.Builder;

@Builder
public record DVDDTO(Long id, String titre, String director, int duree, String rating) {
}
