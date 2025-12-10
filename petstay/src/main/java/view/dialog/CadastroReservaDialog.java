package view.dialog;

import factory.DatabaseJPA;
import model.Reserva;
import model.dao.ReservaDAO;

import java.awt.Frame;
import javax.persistence.EntityManager;
import javax.swing.*;

import com.github.lgooddatepicker.components.DateTimePicker;
import controller.ServicoController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CadastroReservaDialog extends JDialog {

    private JTextField txtIdPet;
    private JTextField txtIdCliente;
    private DateTimePicker dateInicio;
    private DateTimePicker dateFim;
    private JComboBox<String> cbServico;
    private JCheckBox chkAtivo;
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JTextField txtPreco;
    private JComboBox<String> cbModelo;
    private JButton btnSalvar;

    public CadastroReservaDialog(Frame parent) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setTitle("Cadastrar Reserva");
        setSize(450, 600);
        setResizable(false);
        setLayout(null);

        JLabel lblIdPet = new JLabel("ID do Pet:");
        lblIdPet.setBounds(30, 30, 120, 25);
        add(lblIdPet);

        txtIdPet = new JTextField();
        txtIdPet.setBounds(150, 30, 230, 25);
        add(txtIdPet);

        JLabel lblIdCliente = new JLabel("ID do Cliente:");
        lblIdCliente.setBounds(30, 70, 120, 25);
        add(lblIdCliente);

        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(150, 70, 230, 25);
        add(txtIdCliente);

        JLabel lblDataInicio = new JLabel("Data Início:");
        lblDataInicio.setBounds(30, 110, 120, 25);
        add(lblDataInicio);

        dateInicio = new DateTimePicker();
        dateInicio.setBounds(150, 110, 230, 30);
        add(dateInicio);

        JLabel lblDataFim = new JLabel("Data Fim:");
        lblDataFim.setBounds(30, 150, 120, 25);
        add(lblDataFim);

        dateFim = new DateTimePicker();
        dateFim.setBounds(150, 150, 230, 30);
        add(dateFim);

        JLabel lblServico = new JLabel("Serviço:");
        lblServico.setBounds(30, 190, 120, 25);
        add(lblServico);
        
        cbServico = new JComboBox<>();
        cbServico.setBounds(150, 190, 230, 25);
        add(cbServico);
        carregarServicos();

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 230, 120, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 230, 230, 25);
        add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(30, 270, 120, 25);
        add(lblDescricao);

        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBounds(150, 270, 230, 60);
        add(scrollDescricao);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(30, 340, 120, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(150, 340, 230, 25);
        add(txtPreco);

        chkAtivo = new JCheckBox("Reserva Ativa", true);
        chkAtivo.setBounds(150, 420, 150, 25);
        add(chkAtivo);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(150, 470, 120, 35);
        btnSalvar.addActionListener(e -> salvarReserva());
        add(btnSalvar);
    }

    private void salvarReserva() {
        try {
            
            if (txtIdPet.getText().isBlank() || txtIdCliente.getText().isBlank() || 
                txtNome.getText().isBlank() || txtPreco.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
                return;
            }

            int idPet = Integer.parseInt(txtIdPet.getText());
            int idCliente = Integer.parseInt(txtIdCliente.getText());

            
            LocalDateTime inicio = dateInicio.getDateTimeStrict();
            LocalDateTime fim = dateFim.getDateTimeStrict();

            if (inicio == null || fim == null) {
                JOptionPane.showMessageDialog(this, "Selecione data e hora válidas!");
                return;
            }

            
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String dataInicio = inicio.format(fmt);
            String dataFim = fim.format(fmt);

            String servico = cbServico.getSelectedItem().toString();
            boolean ativo = chkAtivo.isSelected();
            
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            int modelo = 0;
        
            
            Reserva r = new Reserva(0, idPet, idCliente, dataInicio, dataFim, servico, ativo, modelo, preco, nome, descricao);
            
            EntityManager em = DatabaseJPA.getInstance().getEntityManager();
            ReservaDAO dao = new ReservaDAO(em);
            dao.save(r);

            JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique se os campos numéricos estão corretos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
    
    private void carregarServicos() {
    try {
        ServicoController controller = new ServicoController();
        java.util.List<String> servicos = controller.findAllNomes();

        cbServico.removeAllItems();

        for (String nome : servicos) {
            cbServico.addItem(nome);
        }

        if (servicos.isEmpty()) {
            cbServico.addItem("Nenhum serviço cadastrado");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Erro ao carregar serviços: " + e.getMessage());
    }
}

}
