package br.com.musics_cmd.principal;
import br.com.musics_cmd.model.Artista;
import br.com.musics_cmd.model.Musicas;
import br.com.musics_cmd.repository.ArtistaRepository;
import br.com.musics_cmd.repository.MusicaRepository;
import br.com.musics_cmd.service.GeminiService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static java.lang.System.out;

public class Principal {

    private ArtistaRepository repositoryA;
    private MusicaRepository musicaR;
    private GeminiService geminiService;
    List<Artista> artistas = new ArrayList<>();
    List<Musicas> music = new ArrayList<>();
    Optional<Musicas> musicasOp;
    Optional<Artista> artistaOp;
    Scanner leitura = new Scanner(System.in);


    public Principal(ArtistaRepository repository, MusicaRepository musicare, GeminiService geminiService) {
        this.repositoryA = repository;
        this.musicaR = musicare;
        this.geminiService = geminiService;
    }

    public void exibeMenu() throws IOException, InterruptedException {
        var opcao = -1;
        while (opcao != 0) {

            var menu = """
                    1- Cadastrar artistas
                    2- Cadastrar músicas
                    3- Listar músicas
                    4- Buscar músicas por artistas
                    5- Pesquisar dados sobre um artista
                    0 - Sair
                    
                    """;

            out.println(menu);
            out.println("Digite a opção desejada:");
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {

                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listamusicas();
                    break;
                case 4:
                    buscarMusicaPorArtista();
                    break;
                case 5:
                    PesquisarDados();
                    break;
            }

        }

    }

    private void PesquisarDados() {
        out.println("Digite o nome do artista: ");
        String descricao = leitura.nextLine();
        artistaOp = repositoryA.findByNomeContainingIgnoreCase(descricao);

        if (artistaOp.isEmpty()) {
            String descricaoCompleta = geminiService.informacoesArtista(descricao);
            out.println("descrição: " + descricaoCompleta);
        }
        if (artistaOp.isPresent() && artistaOp.get().getBiografia() == null) {
            String descricaoCompleta = geminiService.informacoesArtista(descricao);
            artistaOp.get().setBiografia(descricaoCompleta);

            if (descricaoCompleta.contains(("\"error\""))) {
                out.println("O gemini não conseguiu encontrar sua descrição");

            } else {
                repositoryA.save(artistaOp.get());
                out.println("BD ATUALIZADO");
                out.println("descrição: " + descricaoCompleta);
            }

        } else if (artistaOp.isPresent() && artistaOp.get().getBiografia() != null) {
            out.println("Descrição salva no nosso BD: " + artistaOp.get().getBiografia());
        }

    }

    private void buscarMusicaPorArtista() {
        out.println("Digite o nome do artista para encontrar suas musicas: ");
        String artisM = leitura.nextLine();
        music = musicaR.findByArtistaNomeContainingIgnoreCase(artisM);

        if(music.isEmpty()){
            out.println("musica não encontrada tente novamente");
        } else {
            music.forEach( m ->
                    out.println("musicas: " + m.getMusicas())
            );
        }


    }

    private void listamusicas() {
        music = musicaR.findAll();
        music.stream().forEach(System.out::println);

    }

    private void cadastrarMusica() {
        char cadastro = 's';

        while (cadastro == 's') {

            out.println("digite o nome do artista :");
            String artista = leitura.nextLine();
            artistaOp = repositoryA.findByNomeContainingIgnoreCase(artista);

            if (artistaOp.isPresent()) {
                Artista artistaNoBD = artistaOp.get();

                out.println("Digite o nome da musica: ");
                String nomeMusica = leitura.nextLine();
                out.println("digite o nome do album: ");
                String album = leitura.nextLine();

                Musicas musicaSalva = new Musicas(nomeMusica, album, artistaNoBD);
                artistaNoBD.getMusica().add(musicaSalva);
                musicaR.save(musicaSalva);

            } else {
                out.println("Não temos esse artista cadastrado no banco, por gentileza realize o cadastro antes!");
                cadastrarArtista();
                continue;

            }

            System.out.println("Você desejar cadastrar outra musica? (S/N)");
            String resposta = leitura.nextLine().trim().toLowerCase();

            if (!resposta.isEmpty() && resposta.charAt(0) == 's') {
                cadastro = 's';
                System.out.println("A musica foi cadastrada !");
            } else {
                cadastro = 'n';
                out.println("casdastro finalizado ! ");
                out.println(" saindo do cadastro....");
            }
        }
    }

    private void cadastrarArtista() {

        char primeiraL = 's';

        while (primeiraL == 's') {
            out.println("Digite o nome do Artista: ");
            String artistaNome = leitura.nextLine();
            out.println("Informe o tipo desse artista: (solo, dupla, banda)");
            var tipo = leitura.nextLine();
            Artista novoArtista = new Artista(artistaNome, tipo);
            repositoryA.save(novoArtista);

            out.println("Cadastrar outro artista? (S/N)");
            primeiraL = leitura.next().trim().toLowerCase().charAt(0);
            leitura.nextLine();

            if (primeiraL == 'n') {
                out.println("Cadastro realizado com Sucesso!");
            } else if (primeiraL != 's') {
                primeiraL = 'n';
                out.println("opção invalida");
                out.println(" saindo do cadastro....");
            }
        }
    }
}

