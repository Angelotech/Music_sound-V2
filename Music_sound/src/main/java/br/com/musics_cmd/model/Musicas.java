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

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    public Musicas(String musicas, String album, Artista artista) {
        this.musicas = musicas;
        this.album = album;
        this.artista = artista;

    }
    public  Musicas(){

    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
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
                ", artista = '" + artista.getNome() + '\'';
    }
}
