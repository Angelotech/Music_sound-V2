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
                case  4:
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

        if (artistaOp.isPresent() && artistaOp.get().getBiografia() != null){
            out.println("descrição: " + artistaOp.get().getBiografia());

        } else if (artistaOp.isPresent() && descricao == null) {
            String descricaoCompleta = geminiService.informacoesArtista(descricao);
            Artista novaDescricao  = new Artista();
            novaDescricao.setBiografia(descricaoCompleta);

            out.println("descrição salva no banco ! ");
            out.println("Descrição: " + descricaoCompleta);
        }

        String descricaoCompleta = geminiService.informacoesArtista(descricao);
        out.println("descrição: " + descricaoCompleta);

    }

    private void buscarMusicaPorArtista (){
        out.println("Digite o nome do artista para encontrar suas msuica: ");
        String artisM = leitura.nextLine();
         musicasOp = musicaR.findByartistaContainingIgnoreCase(artisM);
        if (musicasOp.isPresent()){
            out.println("sua musica foi encontrada: " + musicasOp.get());
        }else {
            out.println("musica não encontrada tente novamente");
        }

    }

    private void listamusicas() {
        music = musicaR.findAll();
        music.stream().forEach(System.out::println);

    }

    private void cadastrarMusica() {
        char cadastro = 's';

        while (cadastro == 's') {
            out.println("Digite o nome da musica: ");
            String nomeMusica = leitura.nextLine();
            out.println("digite o nome do album: ");
            String album = leitura.nextLine();
            out.println("digite o nome do artista :");
            String artista = leitura.nextLine();
            Musicas musicaSalva = new Musicas(nomeMusica,album,artista);
            musicaR.save(musicaSalva);

            System.out.println("Você desejar cadastrar outra musica? (S/N)");
            cadastro =  leitura.next().trim().toLowerCase().charAt(0);
            leitura.nextLine();

            if (cadastro == 'n'){
                System.out.println("A musica " + nomeMusica + " foi cadastrada !" );
            } else if (cadastro != 's') {
                cadastro = 'n';
                out.println("opção invalida");
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
                    out.println("O artista " + novoArtista.getNome() + " foi cadastrado");
                } else if (primeiraL != 's') {
                    primeiraL = 'n';
                    out.println("opção invalida");
                    out.println(" saindo do cadastro....");
                }
            }
        }
    }

