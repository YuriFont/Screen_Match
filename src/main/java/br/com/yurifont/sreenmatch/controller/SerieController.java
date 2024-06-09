package br.com.yurifont.sreenmatch.controller;

import br.com.yurifont.sreenmatch.DTO.SerieDTO;
import br.com.yurifont.sreenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
        return serieService.getAllSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5Series() {
        return serieService.getTop5Series();
    }
}
