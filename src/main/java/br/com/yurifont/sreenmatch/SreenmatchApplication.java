package br.com.yurifont.sreenmatch;

import br.com.yurifont.sreenmatch.UI.UI;
import br.com.yurifont.sreenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SreenmatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SreenmatchApplication.class, args);
	}
}
