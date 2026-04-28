package br.com.musics_cmd.model;
import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musicas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    private String musicas;
    private String album;
    private String artista;

    public Musicas(String musicas, String album, String artista) {
        this.musicas = musicas;
        this.album = album;
        this.artista = artista;

    }
    public  Musicas(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMusicas() {
        return musicas;
    }

    public void setMusicas(String musicas) {
        this.musicas = musicas;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "Musica: " +
                musicas + '\'' +
                ", album = '" + album + '\'' +
                ", artista = '" + artista + '\'';
    }
}
