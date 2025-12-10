package controller;

import model.Servico;
import model.dao.ServicoDAO;
import factory.DatabaseJPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

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
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar serviço: " + e.getMessage(), e);
        }
        
    }

    public boolean delete(int id_servico) {
        try {
            Servico servico = new Servico();
            servico.setId_servico(id_servico);
            servicoDAO.delete(servico);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar serviço: " + e.getMessage(), e);
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
        }
    }
    
   

    public void carregarTabela(DefaultTableModel model) {
        model.setRowCount(0); 

        List<Servico> servicos = servicoDAO.findAll();

        for (Servico s : servicos) {
            model.addRow(new Object[]{
                s.getId_servico(),
                s.getNome(),
                s.getDescricao(),
                s.getTipo(),
                s.getValor(),
                s.isAtivo()
            });
        }
    }
    
    public List<String> findAllNomes() {
        List<String> nomes = new java.util.ArrayList<>();
        List<Servico> servicos = servicoDAO.findAll();

        for (Servico s : servicos) {
            if(s.isAtivo()){
                 nomes.add(s.getNome());
            }
        }

    return nomes;
}

    public Servico findByNome(String nome) {
        List<Servico> servicos = servicoDAO.findAll();
        for (Servico s : servicos) {
            if (s.getNome().equals(nome)) {
                return s;
            }
        }
        return null;
    }

}
