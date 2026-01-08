package view.dialog;

import factory.DatabaseJPA;
import model.Reserva;
import model.Servico;
import model.Pets;
import model.Proprietario;
import model.dao.ReservaDAO;

import java.awt.Frame;
import javax.persistence.EntityManager;
import javax.swing.*;

import com.github.lgooddatepicker.components.DateTimePicker;
import controller.ServicoController;
import controller.PetsController;
import controller.ProprietarioController;
import controller.ReservaController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import model.Hotel;

public class CadastroReservaDialog extends JDialog {

    private JComboBox<String> cbPet;
    private JComboBox<String> cbCliente;
    private DateTimePicker dateInicio;
    private DateTimePicker dateFim;
    private JList<String> listServicos;
    private DefaultListModel<String> listModel;
    private JCheckBox chkAtivo;
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JTextField txtPreco;
    private JButton btnSalvar;
    
    private Hotel hotelSelecionado;
    private double precoBase = 0.0;
    private List<Servico> servicosDisponiveis = new ArrayList<>();
    private List<Pets> petsDisponiveis = new ArrayList<>();
    private List<Proprietario> proprietariosDisponiveis = new ArrayList<>();

    public CadastroReservaDialog(Frame parent) {
        super(parent, true);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    public CadastroReservaDialog(Frame parent, Hotel hotel) {
        super(parent, true);
        this.hotelSelecionado = hotel;
        this.precoBase = hotel.getPreco();
        initComponents();
        preencherDados();
        setLocationRelativeTo(parent);
    }
    
    private void preencherDados() {
        if (hotelSelecionado != null) {
            txtNome.setText(hotelSelecionado.getTitulo());
            txtDescricao.setText(hotelSelecionado.getSubtitulo());
            // Atualiza preço considerando os dias
            atualizarPrecoComServicos();
        }
    }
    
    private void atualizarPrecoComServicos() {
        try {
            // Calcular número de dias
            long numDias = calcularNumeroDias();
            if (numDias < 1) numDias = 1; // Mínimo de 1 dia
            
            // Preço base multiplicado pelo número de dias
            double precoTotal = precoBase * numDias;
            
            // Adicionar serviços selecionados
            List<String> servicosSelecionados = listServicos.getSelectedValuesList();
            
            for (String itemServico : servicosSelecionados) {
                String nomeServico = extrairNomeServico(itemServico);
                for (Servico s : servicosDisponiveis) {
                    if (s.getNome().equals(nomeServico)) {
                        precoTotal += s.getValor();
                        break;
                    }
                }
            }
            
            txtPreco.setText(String.format("%.2f", precoTotal).replace(",", "."));
        } catch (Exception e) {
            System.err.println("Erro ao atualizar preço: " + e.getMessage());
        }
    }
    
    private long calcularNumeroDias() {
        try {
            LocalDateTime inicio = dateInicio.getDateTimePermissive();
            LocalDateTime fim = dateFim.getDateTimePermissive();
            
            if (inicio == null || fim == null) {
                return 1;
            }
            
            // Calcular diferença em dias (arredondando para cima)
            long horas = ChronoUnit.HOURS.between(inicio, fim);
            long dias = (horas + 23) / 24; // Arredonda para cima
            
            return dias < 1 ? 1 : dias;
        } catch (Exception e) {
            return 1;
        }
    }
    
    private String extrairNomeServico(String itemLista) {
        // Remove o preço do nome do serviço (ex: "Banho (R$ 50.00)" -> "Banho")
        int idx = itemLista.lastIndexOf(" (R$");
        if (idx > 0) {
            return itemLista.substring(0, idx);
        }
        return itemLista;
    }
    
    private void initComponents() {
        setTitle("Cadastrar Reserva");
        setSize(500, 680);
        setResizable(false);
        setLayout(null);

        JLabel lblCliente = new JLabel("Proprietário:");
        lblCliente.setBounds(30, 30, 120, 25);
        add(lblCliente);

        cbCliente = new JComboBox<>();
        cbCliente.setBounds(150, 30, 280, 25);
        cbCliente.addActionListener(e -> atualizarPetsPorProprietario());
        add(cbCliente);
        carregarProprietarios();

        JLabel lblPet = new JLabel("Pet:");
        lblPet.setBounds(30, 70, 120, 25);
        add(lblPet);

        cbPet = new JComboBox<>();
        cbPet.setBounds(150, 70, 240, 25);
        add(cbPet);
        
        JButton btnAddPet = new JButton("+");
        btnAddPet.setBounds(395, 70, 40, 25);
        btnAddPet.setToolTipText("Cadastrar novo pet");
        btnAddPet.addActionListener(e -> abrirDialogNovoPet());
        add(btnAddPet);
        
        carregarPets();

        JLabel lblDataInicio = new JLabel("Data Início:");
        lblDataInicio.setBounds(30, 110, 120, 25);
        add(lblDataInicio);

        dateInicio = new DateTimePicker();
        dateInicio.setBounds(150, 110, 280, 30);
        // Data padrão: agora
        dateInicio.setDateTimePermissive(LocalDateTime.now());
        dateInicio.addDateTimeChangeListener(e -> atualizarPrecoComServicos());
        add(dateInicio);

        JLabel lblDataFim = new JLabel("Data Fim:");
        lblDataFim.setBounds(30, 150, 120, 25);
        add(lblDataFim);

        dateFim = new DateTimePicker();
        dateFim.setBounds(150, 150, 280, 30);
        // Data padrão: 24 horas depois
        dateFim.setDateTimePermissive(LocalDateTime.now().plusDays(1));
        dateFim.addDateTimeChangeListener(e -> atualizarPrecoComServicos());
        add(dateFim);

        JLabel lblServico = new JLabel("Serviços:");
        lblServico.setBounds(30, 190, 120, 25);
        add(lblServico);
        
        JLabel lblServicoHint = new JLabel("(Opcional - Ctrl+Click)");
        lblServicoHint.setFont(lblServicoHint.getFont().deriveFont(10f));
        lblServicoHint.setBounds(30, 210, 120, 20);
        add(lblServicoHint);
        
        listModel = new DefaultListModel<>();
        listServicos = new JList<>(listModel);
        listServicos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listServicos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                atualizarPrecoComServicos();
            }
        });
        JScrollPane scrollServicos = new JScrollPane(listServicos);
        scrollServicos.setBounds(150, 190, 280, 80);
        add(scrollServicos);
        carregarServicos();

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 280, 120, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 280, 280, 25);
        add(txtNome);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(30, 320, 120, 25);
        add(lblDescricao);

        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBounds(150, 320, 280, 60);
        add(scrollDescricao);

        JLabel lblPreco = new JLabel("Preço Total:");
        lblPreco.setBounds(30, 390, 120, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(150, 390, 280, 25);
        txtPreco.setEditable(false);
        add(txtPreco);
        
        JButton btnLimparServicos = new JButton("Limpar Seleção");
        btnLimparServicos.setBounds(150, 425, 130, 25);
        btnLimparServicos.addActionListener(e -> {
            listServicos.clearSelection();
            atualizarPrecoComServicos();
        });
        add(btnLimparServicos);

        chkAtivo = new JCheckBox("Reserva Ativa", true);
        chkAtivo.setBounds(290, 425, 140, 25);
        add(chkAtivo);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 470, 140, 35);
        btnSalvar.addActionListener(e -> salvarReserva());
        add(btnSalvar);
    }

    private void salvarReserva() {
        try {
            
            if (cbPet.getSelectedIndex() == -1 || cbCliente.getSelectedIndex() == -1 || 
                txtNome.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
                return;
            }
            
            List<String> servicosSelecionados = listServicos.getSelectedValuesList();
            // Serviços são opcionais agora

            // Extrair IDs dos ComboBox
            int idPet = extrairIdDoComboBox((String) cbPet.getSelectedItem());
            int idCliente = extrairIdDoComboBox((String) cbCliente.getSelectedItem());
            
            if (idPet == -1 || idCliente == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um proprietário e um pet válidos!");
                return;
            }

            
            LocalDateTime inicio = dateInicio.getDateTimeStrict();
            LocalDateTime fim = dateFim.getDateTimeStrict();

            if (inicio == null || fim == null) {
                JOptionPane.showMessageDialog(this, "Selecione data e hora válidas!");
                return;
            }

            
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String dataInicio = inicio.format(fmt);
            String dataFim = fim.format(fmt);

            // Concatena os serviços selecionados separados por vírgula (apenas os nomes, sem preço)
            List<String> nomesServicos = new ArrayList<>();
            for (String item : servicosSelecionados) {
                nomesServicos.add(extrairNomeServico(item));
            }
            String servicos = nomesServicos.isEmpty() ? "Nenhum" : String.join(", ", nomesServicos);
            boolean ativo = chkAtivo.isSelected();
            
            String nome = txtNome.getText();
            String descricao = txtDescricao.getText();
            double preco = Double.parseDouble(txtPreco.getText().isEmpty() ? "0" : txtPreco.getText());
      
            int modelo = 0;
        
            
            Reserva r = new Reserva(0, idPet, idCliente, dataInicio, dataFim, servicos, ativo, modelo, preco, nome, descricao);
            
            
            ReservaController controller = new ReservaController();
            
            controller.save(r);
   
            String msgServicos = nomesServicos.isEmpty() ? "Nenhum serviço adicional" : servicos;
            JOptionPane.showMessageDialog(this, "Reserva cadastrada com sucesso!\nServiços: " + msgServicos + "\nPreço Total: R$ " + String.format("%.2f", preco));
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique se os campos numéricos estão corretos!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }
    
    private void carregarProprietarios() {
        try {
            ProprietarioController controller = new ProprietarioController();
            proprietariosDisponiveis = controller.findAll();
            
            cbCliente.removeAllItems();
            cbCliente.addItem("-- Selecione um proprietário --");
            
            for (Proprietario p : proprietariosDisponiveis) {
                cbCliente.addItem(p.getId() + " - " + p.getNome() + " (" + p.getCpf() + ")");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar proprietários: " + e.getMessage());
        }
    }
    
    private void carregarPets() {
        try {
            PetsController controller = new PetsController();
            petsDisponiveis = controller.findAll();
            
            cbPet.removeAllItems();
            cbPet.addItem("-- Selecione um pet --");
            
            for (Pets p : petsDisponiveis) {
                cbPet.addItem(p.getId() + " - " + p.getNome() + " (" + p.getEspecie() + " - " + p.getRaca() + ")");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pets: " + e.getMessage());
        }
    }
    
    private void atualizarPetsPorProprietario() {
        try {
            int idProprietario = extrairIdDoComboBox((String) cbCliente.getSelectedItem());
            
            cbPet.removeAllItems();
            cbPet.addItem("-- Selecione um pet --");
            
            if (idProprietario > 0) {
                for (Pets p : petsDisponiveis) {
                    if (p.getDono() == idProprietario) {
                        cbPet.addItem(p.getId() + " - " + p.getNome() + " (" + p.getEspecie() + " - " + p.getRaca() + ")");
                    }
                }
                
                // Se não houver pets do proprietário, mostrar mensagem
                if (cbPet.getItemCount() == 1) {
                    cbPet.addItem("Nenhum pet cadastrado para este proprietário");
                }
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao filtrar pets: " + e.getMessage());
        }
    }
    
    private int extrairIdDoComboBox(String item) {
        if (item == null || item.startsWith("--") || item.startsWith("Nenhum")) {
            return -1;
        }
        try {
            // Formato: "ID - Nome (Info)"
            String idStr = item.split(" - ")[0].trim();
            return Integer.parseInt(idStr);
        } catch (Exception e) {
            return -1;
        }
    }
    
    private void carregarServicos() {
        try {
            ServicoController controller = new ServicoController();
            servicosDisponiveis = controller.findAll();

            listModel.clear();

            for (Servico s : servicosDisponiveis) {
                if (s.isAtivo()) {
                    listModel.addElement(s.getNome() + " (R$ " + String.format("%.2f", s.getValor()) + ")");
                }
            }
            
            // Atualiza a lista de serviços para usar apenas o nome (sem preço) para busca
            servicosDisponiveis.removeIf(s -> !s.isAtivo());

            if (listModel.isEmpty()) {
                listModel.addElement("Nenhum serviço cadastrado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar serviços: " + e.getMessage());
        }
    }
    
    private void abrirDialogNovoPet() {
        // Verificar se um proprietário foi selecionado
        int idProprietario = extrairIdDoComboBox((String) cbCliente.getSelectedItem());
        
        if (idProprietario <= 0) {
            JOptionPane.showMessageDialog(this, "Selecione um proprietário primeiro!");
            return;
        }
        
        // Extrair nome do proprietário
        String itemSelecionado = (String) cbCliente.getSelectedItem();
        String nomeProprietario = "";
        try {
            // Formato: "ID - Nome (CPF)"
            String[] partes = itemSelecionado.split(" - ");
            if (partes.length > 1) {
                nomeProprietario = partes[1].split(" \\(")[0].trim();
            }
        } catch (Exception e) {
            nomeProprietario = "Proprietário " + idProprietario;
        }
        
        // Abrir dialog de cadastro de pet com proprietário pré-selecionado
        CadastroPetsDialog dialog = new CadastroPetsDialog(
            (Frame) getParent(), 
            idProprietario, 
            idProprietario + " - " + nomeProprietario
        );
        dialog.setVisible(true);
        
        // Após fechar o dialog, verificar se um pet foi cadastrado
        if (dialog.isPetCadastrado()) {
            // Recarregar lista de pets
            PetsController controller = new PetsController();
            petsDisponiveis = controller.findAll();
            
            // Atualizar ComboBox mantendo o filtro por proprietário
            atualizarPetsPorProprietario();
            
            // Selecionar o último pet cadastrado (provavelmente é o novo)
            if (cbPet.getItemCount() > 1) {
                cbPet.setSelectedIndex(cbPet.getItemCount() - 1);
            }
        }
    }

}
