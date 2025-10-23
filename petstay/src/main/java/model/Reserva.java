package model;

public class Reserva {
    private int id_reserva;
    private int id_pet;
    private String nome;
    private String raca;
    private String especie;
    private double peso;
    private boolean ativo;

    public Reserva() {
        this.id_reserva = 0;
        this.id_pet = 0;
        this.nome = "";
        this.raca = "";
        this.especie = "";
        this.peso = 0.0;
        this.ativo = false;
    }

    public Reserva(int id_reserva, int id_pet, String nome, String raca, String especie, double peso, boolean ativo) {
        this.id_reserva = id_reserva;
        this.id_pet = id_pet;
        this.nome = nome;
        this.raca = raca;
        this.especie = especie;
        this.peso = peso;
        this.ativo = ativo;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public int getId_pet() {
        return id_pet;
    }

    public void setId_pet(int id_pet) {
        this.id_pet = id_pet;
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

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    
    @Override
    public String toString() {
        return "Reservas{" +
                "id_reserva=" + id_reserva +
                ", id_pet=" + id_pet +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", especie='" + especie + '\'' +
                ", peso=" + peso +
                ", ativo=" + ativo +
                '}';
    }
}
