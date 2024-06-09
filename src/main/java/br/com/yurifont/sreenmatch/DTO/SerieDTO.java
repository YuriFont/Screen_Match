package br.com.yurifont.sreenmatch.DTO;

import br.com.yurifont.sreenmatch.model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(Long id, String title, Double imdbRating, Integer totalSeasons, Category genre, String actors, String poster, String plot) {
}
