package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_reserva;
    private int id_pet;
    private int id_cliente;
    private String data_inicio;
    private String data_fim;
    private String servico;
    private boolean ativo;

    public Reserva() {}

    public Reserva(int id_reserva, int id_pet, int id_cliente, String data_inicio, String data_fim, String servico, boolean ativo) {
        this.id_reserva = id_reserva;
        this.id_pet = id_pet;
        this.id_cliente = id_cliente;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.servico = servico;
        this.ativo = ativo;
    }

    public int getId_reserva() {
        return id_reserva; 
    }
    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva; }

    
    public int getId_pet() { 
        return id_pet; 
    }
    public void setId_pet(int id_pet) { 
        this.id_pet = id_pet; 
    }

    public int getId_cliente() { 
        return id_cliente; 
    }
    public void setId_cliente(int id_cliente) { 
        this.id_cliente = id_cliente; 
    }

    public String getData_inicio() { 
        return data_inicio; 
    }
    public void setData_inicio(String data_inicio) { 
        this.data_inicio = data_inicio; 
    }

    public String getData_fim() { 
        return data_fim; 
    }
    public void setData_fim(String data_fim) { 
        this.data_fim = data_fim; 
    }

    public String getServico() { 
        return servico; 
    }
    public void setServico(String servico) { 
        this.servico = servico; 
    }

    public boolean isAtivo() { 
        return ativo; 
    }
    public void setAtivo(boolean ativo) { 
        this.ativo = ativo; 
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id_reserva=" + id_reserva +
                ", id_pet=" + id_pet +
                ", id_cliente=" + id_cliente +
                ", data_inicio='" + data_inicio + '\'' +
                ", data_fim='" + data_fim + '\'' +
                ", servico='" + servico + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
