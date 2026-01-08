package view.dialog;

import controller.PetsController;
import controller.ProprietarioController;
import javax.swing.*;
import java.awt.Frame;
import java.util.List;
import model.Pets;
import model.Proprietario;

public class CadastroPetsDialog extends JDialog {

    private JTextField txtDono;
    private JTextField txtNome;
    private JTextField txtRaca;
    private JTextField txtEspecie;
    private JButton btnSalvar;

    private PetsController controller;
    private int idProprietarioPreDefinido = -1;
    private boolean petCadastrado = false;

    public CadastroPetsDialog(Frame parent) {
        super(parent, true);
        controller = new PetsController();
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    public CadastroPetsDialog(Frame parent, int idProprietario, String nomeProprietario) {
        super(parent, true);
        controller = new PetsController();
        this.idProprietarioPreDefinido = idProprietario;
        initComponents();

        txtDono.setText(idProprietario + " - " + nomeProprietario);
        txtDono.setEditable(false);
        setLocationRelativeTo(parent);
    }
    
    public boolean isPetCadastrado() {
        return petCadastrado;
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

            int dono;
            if (idProprietarioPreDefinido > 0) {
                dono = idProprietarioPreDefinido;
            } else {
                dono = Integer.parseInt(donoStr.split(" - ")[0].trim());
            }
            
            Pets pet = new Pets(0, dono, nome, raca, especie);
            
            controller.save(pet);

            petCadastrado = true;
        JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
        dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do dono deve ser um número.");
        } catch (IllegalArgumentException e) {
        
            JOptionPane.showMessageDialog(this, e.getMessage(), "Dados inválidos", JOptionPane.WARNING_MESSAGE);
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
