package model;

public class Hotel {

    private int id;
    private String titulo;
    private String subtitulo;
    private double preco;

    public Hotel() {
        this.id = 0;
        this.titulo = "";
        this.subtitulo = "";
        this.preco = 0.0;
    }

    public Hotel(int id, String titulo, String subtitulo, double preco) {
        this.id = id;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", subtitulo='" + subtitulo + '\'' +
                ", preco=" + preco +
                '}';
    }
}
