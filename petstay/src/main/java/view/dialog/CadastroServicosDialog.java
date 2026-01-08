package view.dialog;

import controller.ServicoController;
import javax.swing.*;
import java.awt.Frame;
import model.Servico;

public class CadastroServicosDialog extends JDialog {

    private JTextField txtNome;
    private JTextField txtDescricao;
    private JTextField txtTipo;
    private JTextField txtValor;
    private JCheckBox chkAtivo;
    private JButton btnSalvar;

    private ServicoController controller;

    public CadastroServicosDialog(Frame parent) {
        super(parent, true);
        controller = new ServicoController();
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Cadastrar Serviço");
        setSize(420, 360);
        setResizable(false);
        setLayout(null);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 30, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(140, 30, 230, 25);
        add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(30, 70, 100, 25);
        add(lblDescricao);

        txtDescricao = new JTextField();
        txtDescricao.setBounds(140, 70, 230, 25);
        add(txtDescricao);

        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(30, 110, 100, 25);
        add(lblTipo);

        txtTipo = new JTextField();
        txtTipo.setBounds(140, 110, 230, 25);
        add(txtTipo);

        JLabel lblValor = new JLabel("Valor (R$):");
        lblValor.setBounds(30, 150, 100, 25);
        add(lblValor);

        txtValor = new JTextField();
        txtValor.setBounds(140, 150, 230, 25);
        add(txtValor);

        JLabel lblAtivo = new JLabel("Ativo:");
        lblAtivo.setBounds(30, 190, 100, 25);
        add(lblAtivo);

        chkAtivo = new JCheckBox("Disponível");
        chkAtivo.setBounds(140, 190, 120, 25);
        chkAtivo.setSelected(true);
        add(chkAtivo);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 250, 120, 35);
        btnSalvar.addActionListener(e -> salvarServico());
        add(btnSalvar);
    }

    private void salvarServico() {
        try {
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            String tipo = txtTipo.getText();
            String valorStr = txtValor.getText();
            boolean ativo = chkAtivo.isSelected();

            if (nome.isBlank() || valorStr.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios");
                return;
            }

            double valor = Double.parseDouble(valorStr);
            
            Servico servico = new Servico(0, nome, descricao, tipo, valor, ativo);

            controller.save(servico);

            JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor deve ser numérico.");
        } catch (IllegalArgumentException e) {
        
            JOptionPane.showMessageDialog(this, e.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    
    }
}
