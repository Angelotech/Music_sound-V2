package br.com.musics_cmd;

import br.com.musics_cmd.principal.Principal;
import br.com.musics_cmd.repository.ArtistaRepository;
import br.com.musics_cmd.repository.MusicaRepository;
import br.com.musics_cmd.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicsCmdApplication implements CommandLineRunner {
    @Autowired
    private ArtistaRepository repository;
    @Autowired
    private MusicaRepository musicare;
    @Autowired
    private GeminiService geminiService;

	public static void main(String[] args) {
		SpringApplication.run(MusicsCmdApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(repository, musicare, geminiService);
        principal.exibeMenu();

    }

}
