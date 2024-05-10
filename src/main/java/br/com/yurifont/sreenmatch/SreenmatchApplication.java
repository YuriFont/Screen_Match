package br.com.yurifont.sreenmatch;

import br.com.yurifont.sreenmatch.model.SeriesData;
import br.com.yurifont.sreenmatch.service.ConsumeAPI;
import br.com.yurifont.sreenmatch.service.ConvertData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumeAPI consumeAPI = new ConsumeAPI();
		String json = consumeAPI.getData("http://www.omdbapi.com/?t=Breaking_Bad&apikey=dda2e16f");
		ConvertData cd = new ConvertData();
		SeriesData datas = cd.getData(json, SeriesData.class);
		System.out.println(datas);
	}
}
