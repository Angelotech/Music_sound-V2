package br.com.musics_cmd.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Integer id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Categoria tipo;
    private String biografia;

    public Artista(){
    }

    @OneToMany(mappedBy = "artista",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musicas> musica = new ArrayList<>();

    public Artista(String nome, String tipo) {
        this.nome = nome;
        this.tipo = Categoria.fromString(tipo);

    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getTipo() {
        return tipo;
    }

    public void setTipo(Categoria tipo) {
        this.tipo = tipo;
    }


}
