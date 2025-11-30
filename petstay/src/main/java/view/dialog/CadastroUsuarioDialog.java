
package view.dialog;

import factory.DatabaseJPA;
import model.dao.ProprietarioDAO;
import java.awt.Frame;
import javax.persistence.EntityManager;
import javax.swing.*;
import model.Proprietario;

public class CadastroUsuarioDialog extends JDialog {
    
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtCpf;
    private JPasswordField txtSenha;
    private JButton btnSalvar;
    
    public CadastroUsuarioDialog(Frame parent) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setTitle("Cadastrar Usuário");
        setSize(420, 360);
        setResizable(false);
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 30, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(140, 30, 230, 25);
        add(txtNome);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 70, 100, 25);
        add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(140, 70, 230, 25);
        add(txtEmail);

        JLabel lblTelefone = new JLabel("Telefone:");
        lblTelefone.setBounds(30, 110, 100, 25);
        add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(140, 110, 230, 25);
        add(txtTelefone);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(30, 150, 100, 25);
        add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(140, 150, 230, 25);
        add(txtCpf);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(30, 190, 100, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(140, 190, 230, 25);
        add(txtSenha);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 240, 120, 35);
        btnSalvar.addActionListener(e -> salvarUsuario());
        add(btnSalvar);
    }
    
    public void salvarUsuario(){
        try {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String telefone = txtTelefone.getText();
            String cpf = txtCpf.getText();
            String senha = new String(txtSenha.getPassword());
            
            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatorios");
                return;
            }
            
            Proprietario p = new Proprietario(0, nome, email, telefone, senha, cpf, true);
            
            EntityManager em = DatabaseJPA.getInstance().getEntityManager();
            ProprietarioDAO dao = new ProprietarioDAO(em);
            dao.save(p);
            
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
    }
    }
}
