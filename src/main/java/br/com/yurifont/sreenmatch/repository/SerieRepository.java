package br.com.yurifont.sreenmatch.repository;

import br.com.yurifont.sreenmatch.model.Episode;
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

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE e.title ILIKE %:sectionOfEpisode%")
    List<Episode> searchEpisodeBySection(String sectionOfEpisode);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie ORDER BY rating DESC LIMIT 5")
    List<Episode> searchTopEpisodesBySerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodes e WHERE s = :serie AND YEAR(e.releaseDate) >= :limitYear")
    List<Episode> searchEpisodesFromDate(Serie serie, Integer limitYear);
}
