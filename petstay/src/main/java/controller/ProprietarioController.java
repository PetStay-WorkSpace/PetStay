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
    private final EntityManager entityManager;

    public ProprietarioController() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.proprietarioDAO = new ProprietarioDAO(entityManager);
    }

    public void save(String nome, String email, String telefone, String senha, String cpf, boolean ativo) throws Exception {
        try {
            if (proprietarioDAO.findByEmail(email) != null) {
                throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
            }

            if (proprietarioDAO.findByCpf(cpf) != null) {
                throw new IllegalArgumentException("Já existe um usuário com este CPF.");
            }
            
            Proprietario proprietario = new Proprietario(0, nome, email, telefone, senha, cpf, ativo);
            
            String senhaHash = PasswordUtil.criptografar(senha);
            proprietario.setSenha(senhaHash);
            
            proprietarioDAO.save(proprietario);
            System.out.println("Proprietário salvo com sucesso!");
            
        } catch (IllegalArgumentException e) {
            throw e;
            
        } catch (Exception e) {
            throw new Exception("Erro ao salvar proprietário no banco: " + e.getMessage());
        }
    }

    public List<Proprietario> findAll() {
        try {
            return proprietarioDAO.findAll();
        } catch (Exception e) {
            System.err.println("Erro ao listar proprietários: " + e.getMessage());
            return List.of();
        }
    }

    public Proprietario login(String email, String senha) {
        try {
            Proprietario proprietario = proprietarioDAO.validateLogin(email, senha);
            if (proprietario != null) {
                System.out.println("Login realizado com sucesso!");
            } else {
                System.out.println(" Email ou senha incorretos.");
            }
            return proprietario;
        } catch (Exception e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return null;
        }
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

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
            System.out.println("EntityManager fechado com sucesso.");
        }
    }
}
