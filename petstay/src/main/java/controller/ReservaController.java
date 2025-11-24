package controller;

import model.Reserva;
import model.dao.ReservaDAO;
import javax.persistence.EntityManager;
import java.util.List;

public class ReservaController {

    private final ReservaDAO reservaDAO;

    public ReservaController(EntityManager entityManager) {
        this.reservaDAO = new ReservaDAO(entityManager);
    }

    public void save(int id_pet, int id_cliente, String data_inicio, String data_fim, String servico, boolean ativo) {
        Reserva reserva = new Reserva(0, id_pet, id_cliente, data_inicio, data_fim, servico, ativo);
        reservaDAO.save(reserva);
    }

    public void update(int id_reserva, int id_pet, int id_cliente, String data_inicio, String data_fim, String servico, boolean ativo) {
        Reserva reserva = new Reserva(id_reserva, id_pet, id_cliente, data_inicio, data_fim, servico, ativo);
        reservaDAO.update(reserva);
    }

    public void delete(int id_reserva) {
        Reserva reserva = new Reserva();
        reserva.setId_reserva(id_reserva);
        reservaDAO.delete(reserva);
    }

    public Reserva find(int id_reserva) {
        return reservaDAO.find(id_reserva);
    }

    public List<Reserva> findAll() {
        return reservaDAO.findAll();
    }
}
