package controller;

import model.Proprietario;
import model.dao.ProprietarioDAO;

import java.util.List;

/**
 * @author lohra
 */
public class ProprietarioController {

    private ProprietarioDAO PD;

    public ProprietarioController() {
        this.PD = new ProprietarioDAO();
    }

    public void save(String nome, String email, String telefone, String senha, String cpf, boolean ativo) {
        Proprietario proprietario = new Proprietario(0, nome, email, telefone, senha, cpf, ativo);
        PD.save(proprietario);
    }

    public List<Object> findAll() {
        return PD.findAll();
    }

    /**
     * Attempt to login with email and senha. Returns the Proprietario if successful or null.
     */
    public Proprietario login(String email, String senha) {
        return PD.validateLogin(email, senha);
    }
}
