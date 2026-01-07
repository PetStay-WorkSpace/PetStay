
package view.dialog;

import controller.ProprietarioController;
import java.awt.Frame;
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
    
    public void salvarUsuario() {
        try {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String cpf = txtCpf.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            if (nome.isBlank() || email.isBlank() || senha.isBlank()
                    || telefone.isBlank() || cpf.isBlank()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos.",
                    "Dados incompletos",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Proprietario proprietario =
                    new Proprietario(0, nome, email, telefone, senha, cpf, true);

            ProprietarioController controller = new ProprietarioController();
            controller.save(proprietario);

            JOptionPane.showMessageDialog(
                this,
                "Usuário cadastrado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "Cadastro inválido",
                JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Erro interno ao salvar cadastro.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
            ex.printStackTrace();
        }
    }
}
