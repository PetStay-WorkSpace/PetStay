package controller;

import factory.DatabaseJPA;
import security.PasswordUtil;
import model.Proprietario;
import model.dao.ProprietarioDAO;

import javax.persistence.EntityManager;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ProprietarioController {

    private final ProprietarioDAO proprietarioDAO;

    public ProprietarioController() {
        EntityManager em = DatabaseJPA.getInstance().getEntityManager();
        this.proprietarioDAO = new ProprietarioDAO(em);
    }

    public void save(Proprietario proprietario) {
   
        if (proprietarioDAO.findByEmail(proprietario.getEmail()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
           
        if (proprietarioDAO.findByCpf(proprietario.getCpf()) != null) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
            

        proprietario.setEmail(proprietario.getEmail().toLowerCase().trim());
        proprietario.setCpf(proprietario.getCpf().replaceAll("\\D", ""));

        String senhaHash = PasswordUtil.criptografar(proprietario.getSenha());
        proprietario.setSenha(senhaHash);

        proprietarioDAO.save(proprietario);
        
    }

    public List<Proprietario> findAll() {
        return proprietarioDAO.findAll();
    }

    public Proprietario login(String email, String senha) {
        Proprietario p = proprietarioDAO.findByEmail(email);
        if (p != null && PasswordUtil.verificarSenha(senha, p.getSenha())) {            
            return p;
        }
        return null;
    }
    
    public void carregarTabela(DefaultTableModel model) {
    try {
        model.setRowCount(0); 

        List<Proprietario> lista = proprietarioDAO.findAll(); 

        for (Proprietario p : lista) {
            model.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getEmail(),
                p.getTelefone()
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Erro ao carregar tabela: " + e.getMessage());
        }
    }

}
