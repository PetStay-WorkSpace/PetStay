package model;

import java.time.LocalDateTime;

public class Avaliacao {
    private int id_hotel;
    private int id_avaliacao;
    private double nota;
    private String comentarios;
    private LocalDateTime data;

    public Avaliacao() {
        this.id_hotel = 0;
        this.id_avaliacao = 0;
        this.nota = 0.0;
        this.comentarios = "";
        this.data = LocalDateTime.now();
    }

    public Avaliacao(int id_hotel, int id_avaliacao, double nota, String comentarios, LocalDateTime data) {
        this.id_hotel = id_hotel;
        this.id_avaliacao = id_avaliacao;
        this.nota = nota;
        this.comentarios = comentarios;
        this.data = data;
    }
    
    public int getId_hotel() {
        return id_hotel;
    }
    
     public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }


    public int getId_avaliacao() {
        return id_avaliacao;
    }

    public void setId_avaliacao(int avaliacoes) {
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
    
    

    @Override
    public String toString() {
        return "Avaliacoes{" +
                "hotel=" + getId_hotel() +
                ", avaliacoes=" + id_avaliacao +
                ", nota=" + nota +
                ", comentarios='" + comentarios + '\'' +
                ", data=" + data +
                '}';
    }

    
}

    
   
