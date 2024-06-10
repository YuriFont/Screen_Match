package br.com.yurifont.sreenmatch.controller;

import br.com.yurifont.sreenmatch.DTO.EpisodeDTO;
import br.com.yurifont.sreenmatch.DTO.SerieDTO;
import br.com.yurifont.sreenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {
    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<SerieDTO> getSeries() {
        return serieService.getSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5Series() {
        return serieService.getTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> getTop5SeriesReleaseDates() {
        return serieService.getTop5SeriesReleaseDates();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id) {
        return serieService.getSerieById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getSeasons(@PathVariable Long id) {
        return serieService.getSeasons(id);
    }

    @GetMapping("/{id}/temporadas/{num}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Long num) {
        return serieService.getSeasonByNumber(id, num);
    }
}
