package model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_fatura;
    private LocalDateTime data_emissao;
    private double valor_pagamento;
    private String metodo_pagamento;
    private String status;

    public Fatura() {
        this.id_fatura = 0;
        this.data_emissao = null;
        this.valor_pagamento = 0.0;
        this.metodo_pagamento = "";
        this.status = "";
    }

   
    public Fatura(int id_fatura, LocalDateTime data_emissao, double valor_pagamento, String metodo_pagamento, String status) {
        this.id_fatura = id_fatura;
        this.data_emissao = data_emissao;
        this.valor_pagamento = valor_pagamento;
        this.metodo_pagamento = metodo_pagamento;
        this.status = status;
    }

    
    public int getId_fatura() {
        return id_fatura;
    }

    public void setId_fatura(int id_fatura) {
        this.id_fatura = id_fatura;
    }

    public LocalDateTime getData_emissao() {
        return data_emissao;
    }

    public void setData_emissao(LocalDateTime data_emissao) {
        this.data_emissao = data_emissao;
    }

    public double getValor_pagamento() {
        return valor_pagamento;
    }

    public void setValor_pagamento(double valor_pagamento) {
        this.valor_pagamento = valor_pagamento;
    }

    public String getMetodo_pagamento() {
        return metodo_pagamento;
    }

    public void setMetodo_pagamento(String metodo_pagamento) {
        this.metodo_pagamento = metodo_pagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Faturas{" +
                "id_fatura=" + id_fatura +
                ", data_emissao=" + data_emissao +
                ", valor_pagamento=" + valor_pagamento +
                ", metodo_pagamento='" + metodo_pagamento + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
