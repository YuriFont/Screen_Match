package br.com.yurifont.sreenmatch.controller;

import br.com.yurifont.sreenmatch.DTO.SerieDTO;
import br.com.yurifont.sreenmatch.model.Serie;
import br.com.yurifont.sreenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<SerieDTO> getSeries() {
        return serieRepository.findAll()
                .stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitle(), s.getImdbRating(), s.getTotalSeasons(), s.getGenre(), s.getActors(), s.getPoster(), s.getPlot()))
                .collect(Collectors.toList());
    }

}
