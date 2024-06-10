package br.com.yurifont.sreenmatch.service;

import br.com.yurifont.sreenmatch.DTO.EpisodeDTO;
import br.com.yurifont.sreenmatch.DTO.SerieDTO;
import br.com.yurifont.sreenmatch.model.Episode;
import br.com.yurifont.sreenmatch.model.Serie;
import br.com.yurifont.sreenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> getSeries() {
        return convertsData(serieRepository.findAll());
    }

    public List<SerieDTO> getTop5Series() {
        return convertsData(serieRepository.findTop5ByOrderByImdbRatingDesc());
    }

    public List<SerieDTO> getTop5SeriesReleaseDates() {
        return convertsData(serieRepository.getSeriesLatestEpisodes());
    }

    public SerieDTO getSerieById(Long id) {
        Optional<Serie> serieOptional = serieRepository.findById(id);

        if (serieOptional.isPresent()) {
            Serie s = serieOptional.get();

            return new SerieDTO(s.getId(), s.getTitle(), s.getImdbRating(), s.getTotalSeasons(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot());
        }
        return null;
    }

    public List<EpisodeDTO> getSeasons(Long id) {
        Optional<Serie> serieOptional = serieRepository.findById(id);

        if (serieOptional.isPresent()) {
            Serie s = serieOptional.get();

            return s.getEpisodes().stream()
                    .map(e -> new EpisodeDTO(e.getSeason(), e.getTitle(), e.getEpisodeNumber()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodeDTO> getSeasonByNumber(Long id, Long num) {
        List<Episode> episodeList = serieRepository.getEpisodesBySeason(id, num);

        return episodeList.stream()
                .map(e -> new EpisodeDTO(e.getSeason(), e.getTitle(), e.getEpisodeNumber()))
                .collect(Collectors.toList());
    }

    private List<SerieDTO> convertsData(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getImdbRating(), s.getTotalSeasons(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot()))
                .collect(Collectors.toList());
    }
}
