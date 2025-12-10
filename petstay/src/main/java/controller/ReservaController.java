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

    public void save(int id_pet, int id_cliente, String data_inicio, String data_fim, String servico, boolean ativo, int modelo, double preco, String nome, String descricao) {
        Reserva reserva = new Reserva(0, id_pet, id_cliente, data_inicio, data_fim, servico, ativo, modelo, preco, nome, descricao);
        reservaDAO.save(reserva);
    }

    public void update(int id_reserva, int id_pet, int id_cliente, String data_inicio, String data_fim, String servico, boolean ativo, int modelo, double preco, String nome, String descricao) {
        Reserva reserva = new Reserva(id_reserva, id_pet, id_cliente, data_inicio, data_fim, servico, ativo, modelo, preco, nome, descricao);
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
