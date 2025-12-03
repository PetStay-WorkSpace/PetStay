package view.dialog;

import controller.PetsController;
import controller.ProprietarioController;
import javax.swing.*;
import java.awt.Frame;
import java.util.List;
import model.Proprietario;

public class CadastroPetsDialog extends JDialog {

    private JTextField txtDono;
    private JTextField txtNome;
    private JTextField txtRaca;
    private JTextField txtEspecie;
    private JButton btnSalvar;

    private PetsController controller;

    public CadastroPetsDialog(Frame parent) {
        super(parent, true);
        controller = new PetsController();
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Cadastrar Pet");
        setSize(420, 320);
        setResizable(false);
        setLayout(null);

        JLabel lblDono = new JLabel("ID Dono:");
        lblDono.setBounds(30, 30, 100, 25);
        add(lblDono);
        
        JButton btnBuscarDono = new JButton("Buscar");
        btnBuscarDono.setBounds(290, 30, 80, 25);
        btnBuscarDono.addActionListener(e -> buscarDono());
        add(btnBuscarDono);

        txtDono = new JTextField();
        txtDono.setBounds(140, 30, 230, 25);
        add(txtDono);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 70, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(140, 70, 230, 25);
        add(txtNome);

        JLabel lblRaca = new JLabel("Raça:");
        lblRaca.setBounds(30, 110, 100, 25);
        add(lblRaca);

        txtRaca = new JTextField();
        txtRaca.setBounds(140, 110, 230, 25);
        add(txtRaca);

        JLabel lblEspecie = new JLabel("Espécie:");
        lblEspecie.setBounds(30, 150, 100, 25);
        add(lblEspecie);

        txtEspecie = new JTextField();
        txtEspecie.setBounds(140, 150, 230, 25);
        add(txtEspecie);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(140, 210, 120, 35);
        btnSalvar.addActionListener(e -> salvarPet());
        add(btnSalvar);
    }

    private void salvarPet() {
        try {
            String donoStr = txtDono.getText();
            String nome = txtNome.getText();
            String raca = txtRaca.getText();
            String especie = txtEspecie.getText();

            if (donoStr.isBlank() || nome.isBlank() || especie.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios");
                return;
            }

            int dono = Integer.parseInt(donoStr);

            boolean ok = controller.save(dono, nome, raca, especie);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o pet.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do dono deve ser um número.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
    
    private void buscarDono() {
        ProprietarioController proprietarioController = new ProprietarioController();
        List<Proprietario> lista = proprietarioController.findAll();

        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum proprietário encontrado!");
            return;
        }

        String[] nomes = lista.stream()
                .map(p -> p.getId() + " - " + p.getNome())
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o Proprietário:",
                "Buscar Dono",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nomes,
                nomes[0]
        );

        if (escolha != null) {
            txtDono.setText(escolha.split(" - ")[0]);
        }
    }
}
