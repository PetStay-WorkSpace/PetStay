package controller;

import model.Servico;
import model.dao.ServicoDAO;
import factory.DatabaseJPA;
import java.util.List;
import javax.persistence.EntityManager;

public class ServicoController {

    private final ServicoDAO servicoDAO;
    private final EntityManager entityManager;

    public ServicoController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.servicoDAO = new ServicoDAO(entityManager);
    }

    public boolean save(String nome, String descricao, String tipo, double valor, boolean ativo) {
        try {
            Servico servico = new Servico(0, nome, descricao, tipo, valor, ativo);
            servicoDAO.save(servico);
            System.out.println(" Servico salvo com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println(" Erro ao salvar servico: " + e.getMessage());
            return false;
        }
        
    }

    public boolean delete(int id_servico) {
        try {
            Servico servico = new Servico();
            servico.setId_servico(id_servico);
            servicoDAO.delete(servico);
            System.out.println("️ Servico removida com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println(" Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public Servico find(int id_servico) {
        return servicoDAO.find(id_servico);
    }

    public List<Servico> findAll() {
        return servicoDAO.findAll();
    }
    
    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println(" EntityManager fechado com sucesso.");
        }
    }
}
