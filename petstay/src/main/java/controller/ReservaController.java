package controller;

import factory.DatabaseJPA;
import model.Reserva;
import model.dao.ReservaDAO;
import javax.persistence.EntityManager;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ReservaController {

    private final ReservaDAO reservaDAO;
    private final EntityManager entityManager;

    public ReservaController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.reservaDAO = new ReservaDAO(entityManager);
    }

    public void save(Reserva reserva) {
        reservaDAO.save(reserva);
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

    public List<Reserva> findByModelo(int modelo) {
        return reservaDAO.findByModelo(modelo);
    }
    
    public void carregarTabela(DefaultTableModel model) {
        model.setRowCount(0); 

        List<Reserva> reservas = reservaDAO.findAll();

        for (Reserva r : reservas) {
            model.addRow(new Object[]{
                r.getId_reserva(),
                r.getId_pet(),
                r.getId_cliente(),
                r.getData_inicio(),
                r.getData_fim(),
                r.getServico(),
                r.isAtivo()
            });
        }
    }

}
