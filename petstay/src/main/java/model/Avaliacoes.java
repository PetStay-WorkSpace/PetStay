package model;

import java.time.LocalDateTime;

public class Avaliacoes {
    private int id_avaliacao;
    private double nota;
    private String comentarios;
    private LocalDateTime data;

    public Avaliacoes() {
        this.id_avaliacao = 0;
        this.nota = 0.0;
        this.comentarios = "";
        this.data = LocalDateTime.now();
    }

    public Avaliacoes(int avaliacoes, double nota, String comentarios, LocalDateTime data) {
        this.id_avaliacao = avaliacoes;
        this.nota = nota;
        this.comentarios = comentarios;
        this.data = data;
    }

    public int getAvaliacoes() {
        return id_avaliacao;
    }

    public void setAvaliacoes(int avaliacoes) {
        this.id_avaliacao = avaliacoes;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    // toString para facilitar a visualização
    @Override
    public String toString() {
        return "Avaliacoes{" +
                "avaliacoes=" + id_avaliacao +
                ", nota=" + nota +
                ", comentarios='" + comentarios + '\'' +
                ", data=" + data +
                '}';
    }
}
