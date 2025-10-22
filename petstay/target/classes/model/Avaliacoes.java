package model;

import java.time.LocalDateTime;

public class Avaliacoes {
    private int avaliacoes;
    private double nota;
    private String comentarios;
    private LocalDateTime data;

    public Avaliacoes() {
        this.avaliacoes = 0;
        this.nota = 0.0;
        this.comentarios = "";
        this.data = LocalDateTime.now();
    }

    public Avaliacoes(int avaliacoes, double nota, String comentarios, LocalDateTime data) {
        this.avaliacoes = avaliacoes;
        this.nota = nota;
        this.comentarios = comentarios;
        this.data = data;
    }

    public int getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(int avaliacoes) {
        this.avaliacoes = avaliacoes;
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
                "avaliacoes=" + avaliacoes +
                ", nota=" + nota +
                ", comentarios='" + comentarios + '\'' +
                ", data=" + data +
                '}';
    }
}
