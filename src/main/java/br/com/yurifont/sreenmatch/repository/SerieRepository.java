package br.com.yurifont.sreenmatch.repository;

import br.com.yurifont.sreenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.yurifont.sreenmatch.model.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByActorsContainingIgnoreCase(String actorName);
    List<Serie> findTop5ByOrderByImdbRatingDesc();
    List<Serie> findByGenre(Category category);
    @Query("SELECT s FROM Serie s WHERE s.totalSeasons <= :totalSeasons AND s.imdbRating >= :rating")
    List<Serie> filterBySeasonsAndRating(int totalSeasons, double rating);
}
