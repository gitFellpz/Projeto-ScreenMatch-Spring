package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();

		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=481d1f0b");
		ConverteDados converteDados = new ConverteDados();
		DadosSerie dadosS = converteDados.obterDados(json, DadosSerie.class);
		System.out.println(dadosS);

		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=481d1f0b");
		DadosEpisodios dadosEp = converteDados.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosEp);

		List<DadosTemporada> dadosTemporadaList = new ArrayList<>();

		for(int i = 1; i <= dadosS.totalTemporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season="+ i +"&apikey=481d1f0b");
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
			dadosTemporadaList.add(dadosTemporada);
		}

		dadosTemporadaList.forEach(System.out::println);



	}
}
