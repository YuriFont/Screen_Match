package br.com.yurifont.sreenmatch.service;

import br.com.yurifont.sreenmatch.DTO.SerieDTO;
import br.com.yurifont.sreenmatch.model.Serie;
import br.com.yurifont.sreenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getAllSeries() {
        return convertsData(serieRepository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return convertsData(serieRepository.findTop5ByOrderByImdbRatingDesc());
    }

    public List<SerieDTO> getTop5ReleaseDates() {
        return convertsData(serieRepository.findTop5ByOrderByEpisodesReleaseDateDesc());
    }

    private List<SerieDTO> convertsData(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getImdbRating(), s.getTotalSeasons(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot()))
                .collect(Collectors.toList());
    }
}
