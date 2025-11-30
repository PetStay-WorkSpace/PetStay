package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int dono;
    private String nome;
    private String raca;
    private String especie;
    
    public Pets(){
        this.id = 0;
        this.dono = -1;
        this.nome = "";
        this.raca = "";
        this.especie = "";
    }
    
    public Pets(int id, int dono, String nome, String raca, String especie) {
        this.id = id;
        this.dono = dono;
        this.nome = nome;
        this.raca = raca;
        this.especie = especie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    @Override
    public String toString() {
        return "Pets{" +
                "id=" + id +
                ", dono='" + dono + '\'' +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", especie='" + especie + '\'' +
                '}';
    }
}
