package controller;

import model.Fatura;
import model.dao.FaturaDAO;

import java.time.LocalDateTime;
import java.util.List;

public class FaturaController {

    private FaturaDAO FD;

    public FaturaController() {
        this.FD = new FaturaDAO();
    }

    public void save(LocalDateTime data_emissao, double valor_pagamento, String metodo_pagamento, String status) {
        Fatura fatura = new Fatura(0, data_emissao, valor_pagamento, metodo_pagamento, status);
        FD.save(fatura);
    }

    public boolean delete(int id_fatura) {
        Fatura fatura = new Fatura();
        fatura.setId_fatura(id_fatura);
        return FD.delete(fatura);
    }

    public Fatura find(int id_fatura) {
        Fatura fatura = new Fatura();
        fatura.setId_fatura(id_fatura);
        return FD.find(fatura);
    }

    public List<Fatura> findAll() {
        return FD.findAll();
    }
}
