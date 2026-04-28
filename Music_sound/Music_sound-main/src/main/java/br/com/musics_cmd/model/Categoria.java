package br.com.musics_cmd.model;

public enum Categoria {
    DUPLA("dupla"),
    SOLO("solo"),
    BANDA("banda");

    private String categoriaUsuario;

    // Construtor do enum para associar o texto
    Categoria(String categoriaUsuario) {
        this.categoriaUsuario = categoriaUsuario;
    }


    public static Categoria fromString(String text) {
        for (Categoria c : Categoria.values()) {
            if (c.categoriaUsuario.equalsIgnoreCase(text)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }


}
