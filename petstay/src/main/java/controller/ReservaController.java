package controller;

import model.Reserva;
import model.dao.ReservaDAO;

import java.util.List;

public class ReservaController {

    private ReservaDAO RD;

    public ReservaController() {
        this.RD = new ReservaDAO();
    }

    public void save(int id_pet, String nome, String raca, String especie, double peso, boolean ativo) {
        Reserva reserva = new Reserva(0, id_pet, nome, raca, especie, peso, ativo);
        RD.save(reserva);
    }

    public boolean delete(int id_reserva) {
        Reserva reserva = new Reserva();
        reserva.setId_reserva(id_reserva);
        return RD.delete(reserva);
    }

    public Reserva find(int id_reserva) {
        Reserva reserva = new Reserva();
        reserva.setId_reserva(id_reserva);
        return RD.find(reserva);
    }

    public List<Reserva> findAll() {
        return RD.findAll();
    }
}
