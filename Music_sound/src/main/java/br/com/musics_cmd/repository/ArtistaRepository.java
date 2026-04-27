package br.com.musics_cmd.repository;

import br.com.musics_cmd.model.Artista;
import br.com.musics_cmd.model.Musicas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista,Integer> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nomeDoArtista);

}
