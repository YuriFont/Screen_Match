package br.com.yurifont.sreenmatch.repository;

import br.com.yurifont.sreenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByActorsContainingIgnoreCase(String actorName);
    List<Serie> findTop5ByOrderByImdbRatingDesc();
}
