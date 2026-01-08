package controller;

import model.Servico;
import model.dao.ServicoDAO;
import factory.DatabaseJPA;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;

public class ServicoController {

    private final ServicoDAO servicoDAO;

    public ServicoController() {
        EntityManager entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.servicoDAO = new ServicoDAO(entityManager);
    }

    public void save(Servico servico) {
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não pode ser nulo.");
        }

        if (servico.getNome() == null || servico.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do serviço é obrigatório.");
        }

        if (servico.getTipo() == null || servico.getTipo().isBlank()) {
            throw new IllegalArgumentException("Tipo do serviço é obrigatório.");
        }

        if (servico.getValor() < 0) {
            throw new IllegalArgumentException("Valor do serviço não pode ser negativo.");
        }

        servicoDAO.save(servico);
        
    }

    public void delete(int idServico) {
        Servico servico = servicoDAO.find(idServico);

        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado.");
        }

        servicoDAO.delete(servico);
    }

    public Servico find(int id_servico) {
        return servicoDAO.find(id_servico);
    }

    public List<Servico> findAll() {
        return servicoDAO.findAll();
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
