/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import view.dialog.CadastroUsuarioDialog;
import view.dialog.CadastroPetsDialog;
import view.dialog.CadastroReservaDialog;
import controller.PetsController;
import controller.ProprietarioController;
import controller.ReservaController;
import controller.ServicoController;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Hotel;
import model.Session;
import model.Proprietario;
import model.Reserva;
import view.components.panelCards;
import view.components.hoteis.cardHotel;
import view.dialog.CadastroServicosDialog;

/**
 *
 * @author lohran
 */
public class FrHome extends javax.swing.JFrame {
    private final ProprietarioController controller = new ProprietarioController();
    private final PetsController petsController = new PetsController();
    private final ReservaController reservasController = new ReservaController();
    private final ServicoController servicoController = new ServicoController();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrHome.class.getName());

    /**
     * Creates new form TelaLogin
     */
    public FrHome() {
        initComponents();
        estilizarTabelas();
        carregarTabela();
        carregarTabelaPets();
        carregarTabelaReservas();
        carregarTabelaServicos();
        carregarRelatorio();
        configurarListenerTabelaPets();
        setBackground(new Color(0,0,0,0));
        winButton1.initEvent(this);
        try {
            tabbledPanel.setUI(new BasicTabbedPaneUI() {
                @Override
                protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                    return 0;
                }

                @Override
                protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
                }

                @Override
                protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                }
            });

            tabbledPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
            tabbledPanel.setOpaque(true);

            java.awt.Color contentBg = new java.awt.Color(247, 247, 248);
            javax.swing.JPanel[] inner = new javax.swing.JPanel[] { painelHotel, painelProprietarios, painelPets, painelReservas, jPanel6, jPanel7 };
            for (javax.swing.JPanel p : inner) {
                if (p != null) {
                    p.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                    p.setOpaque(true);
                    p.setBackground(contentBg);
                }
            }

            tabbledPanel.setFocusable(false);

        } catch (Exception ex) {
            logger.log(java.util.logging.Level.FINE, "Falha ao ocultar abas do jTabbedPane1", ex);
        }
        try {
            Proprietario user = Session.getInstance().getUser();
            if (user != null && user.getNome() != null && !user.getNome().isEmpty()) {
                userName.setText(user.getNome());
            } 
            
            for (int modelo = 1; modelo <= 3; modelo++) {
                List<Reserva> reservasModelo = reservasController.findByModelo(modelo);

                System.out.println("Reservas para o modelo " + modelo + ": " + reservasModelo.size());
                
                if (!reservasModelo.isEmpty()) {
                    for (Reserva reserva : reservasModelo) {
                        Hotel h = new Hotel(
                            reserva.getId_reserva(), 
                            reserva.getNome(),
                            reserva.getDescricao(),
                            reserva.getPreco()
                        );
                        addCard(h);
                    }
                }
            }            
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.FINE, "Erro ao carregar reservas: " + ex.getMessage(), ex);
        }
    }
        
    public void addCard(Hotel data) {
        //importar dados dos Hoteis
        cardHotel card = new cardHotel();
        card.setData(data);
        panelCards.add(card);
        panelCards.repaint();
        panelCards.revalidate();
    }
    private void carregarTabela() {
        DefaultTableModel model = (DefaultTableModel) jTableProprietarios.getModel();
        controller.carregarTabela(model);
    }
    
    private void carregarTabelaPets() {
        DefaultTableModel model = (DefaultTableModel) jTablePets.getModel();
        petsController.carregarTabela(model);
    }
    
    private void carregarTabelaReservas() {
        DefaultTableModel model = (DefaultTableModel) jTableReservas.getModel();
        reservasController.carregarTabela(model);
    }
    
    private void carregarTabelaServicos() {
        DefaultTableModel model = (DefaultTableModel) jTableServicos.getModel();
        servicoController.carregarTabela(model);
    }
    
    private void carregarRelatorio() {
        try {
            int totalPets = petsController.findAll().size();
            valTotalPets.setText(String.valueOf(totalPets));

            List<Reserva> reservas = reservasController.findAll();

            long totalReservas = reservas.stream()
                .filter(Reserva::isAtivo)
                .count();
            valTotalReservas.setText(String.valueOf(totalReservas));

            double totalVendas = reservas.stream()
                .filter(Reserva::isAtivo)
                .mapToDouble(Reserva::getPreco)
                .sum();
            valTotalVendas.setText(String.format("R$ %.2f", totalVendas));

            int totalServicos = servicoController.findAll().size();
            valTotalServicos.setText(String.valueOf(totalServicos));

            int totalProprietarios = controller.findAll().size();
            valTotalProprietarios.setText(String.valueOf(totalProprietarios));

        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao carregar relatório", ex);
        }
    }
    
    private void estilizarTabelas() {
        // Cores personalizadas
        Color headerColor = new Color(0, 130, 243);
        Color headerTextColor = Color.WHITE;
        Color rowColor1 = Color.WHITE;
        Color rowColor2 = new Color(247, 247, 248);
        Color selectionColor = new Color(173, 216, 230);
        Color gridColor = new Color(220, 220, 220);
        
        javax.swing.JTable[] tabelas = {
            jTableProprietarios, 
            jTablePets, 
            jTableReservas, 
            jTableServicos
        };
        
        for (javax.swing.JTable tabela : tabelas) {
            // Configurar cabeçalho
            tabela.getTableHeader().setFont(new java.awt.Font("Poppins SemiBold", java.awt.Font.PLAIN, 13));
            tabela.getTableHeader().setBackground(headerColor);
            tabela.getTableHeader().setForeground(headerTextColor);
            tabela.getTableHeader().setOpaque(false);
            tabela.getTableHeader().setPreferredSize(new java.awt.Dimension(tabela.getTableHeader().getWidth(), 35));
            
            // Configurar corpo da tabela
            tabela.setFont(new java.awt.Font("Poppins", java.awt.Font.PLAIN, 12));
            tabela.setRowHeight(30);
            tabela.setShowGrid(true);
            tabela.setGridColor(gridColor);
            tabela.setSelectionBackground(selectionColor);
            tabela.setSelectionForeground(Color.BLACK);
            tabela.setIntercellSpacing(new java.awt.Dimension(10, 5));
            
            // Renderizador customizado para linhas alternadas
            tabela.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                    
                    java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    
                    if (!isSelected) {
                        c.setBackground(row % 2 == 0 ? rowColor1 : rowColor2);
                    }
                    
                    // Padding nas células
                    if (c instanceof javax.swing.JLabel) {
                        ((javax.swing.JLabel) c).setBorder(
                            javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10)
                        );
                    }
                    
                    return c;
                }
            });
            
            // Centralizar texto do cabeçalho
            ((javax.swing.table.DefaultTableCellRenderer) tabela.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(javax.swing.JLabel.CENTER);
        }
    }
    
    private void configurarListenerTabelaPets() {
        jTablePets.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    int row = jTablePets.getSelectedRow();
                    if (row != -1) {
                        int idPet = (int) jTablePets.getValueAt(row, 0);
                        mostrarReservasDoPet(idPet);
                    }
                }
            }
        });
    }
    
    private void mostrarReservasDoPet(int idPet) {
        try {
            List<Reserva> reservas = reservasController.findByIdPet(idPet);
            
            if (reservas.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Este pet não possui reservas cadastradas.", 
                    "Reservas do Pet", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("Reservas do Pet ID ").append(idPet).append(":\n\n");
            
            for (Reserva r : reservas) {
                mensagem.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                mensagem.append("ID Reserva: ").append(r.getId_reserva()).append("\n");
                mensagem.append("Cliente ID: ").append(r.getId_cliente()).append("\n");
                mensagem.append("Data Início: ").append(r.getData_inicio()).append("\n");
                mensagem.append("Data Fim: ").append(r.getData_fim()).append("\n");
                mensagem.append("Serviço: ").append(r.getServico()).append("\n");
                mensagem.append("Preço: R$ ").append(String.format("%.2f", r.getPreco())).append("\n");
                mensagem.append("Ativo: ").append(r.isAtivo() ? "Sim" : "Não").append("\n");
                mensagem.append("\n");
            }
            
            javax.swing.JTextArea textArea = new javax.swing.JTextArea(mensagem.toString());
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
            javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
            
            JOptionPane.showMessageDialog(this, 
                scrollPane, 
                "Histórico de Reservas - Pet ID " + idPet, 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao buscar reservas do pet: " + ex.getMessage(), ex);
            JOptionPane.showMessageDialog(this, 
                "Erro ao carregar reservas do pet.", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialog_cadProprietario = new javax.swing.JDialog();
        panelRound1 = new view.components.PanelRound();
        panelRound3 = new view.components.PanelRound();
        jLabel4 = new javax.swing.JLabel();
        winButton2 = new view.components.win_button.WinButton();
        painelGeral = new view.components.PanelRound();
        header = new view.components.PanelRound();
        winButton1 = new view.components.win_button.WinButton();
        body = new view.components.PanelRound();
        painelLateral = new view.components.PanelRound();
        area_rodape = new view.components.PanelRound();
        btnLogout = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        btn_home = new view.components.PanelRound();
        labelHoteis = new javax.swing.JLabel();
        labelIconesHoteis = new javax.swing.JLabel();
        btn_proprietarios = new view.components.PanelRound();
        labelProprietario = new javax.swing.JLabel();
        labelIconeProprietario = new javax.swing.JLabel();
        btn_pets = new view.components.PanelRound();
        labelPets = new javax.swing.JLabel();
        labelIconPets = new javax.swing.JLabel();
        btn_reservas = new view.components.PanelRound();
        labelReservas = new javax.swing.JLabel();
        labelIconReservas = new javax.swing.JLabel();
        btn_servicos = new view.components.PanelRound();
        labelServicos = new javax.swing.JLabel();
        labelIconServicos = new javax.swing.JLabel();
        btn_relatorios = new view.components.PanelRound();
        labelRelatorios = new javax.swing.JLabel();
        labelIconRelatorios = new javax.swing.JLabel();
        panelRound2 = new view.components.PanelRound();
        tabbledPanel = new javax.swing.JTabbedPane();
        painelHotel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelCards = new view.components.panelCards();
        painelProprietarios = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableProprietarios = new javax.swing.JTable();
        painelPets = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTablePets = new javax.swing.JTable();
        painelReservas = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableReservas = new javax.swing.JTable();
        painelServicos = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableServicos = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        painelPets1 = new view.components.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        valTotalPets = new javax.swing.JLabel();
        painelReservas1 = new view.components.panelCards();
        jLabel18 = new javax.swing.JLabel();
        valTotalReservas = new javax.swing.JLabel();
        painelServicos1 = new view.components.panelCards();
        jLabel24 = new javax.swing.JLabel();
        valTotalServicos = new javax.swing.JLabel();
        painelProprietarios1 = new view.components.panelCards();
        jLabel27 = new javax.swing.JLabel();
        valTotalProprietarios = new javax.swing.JLabel();
        painelFinanceiro = new view.components.PanelRound();
        valTotalVendas = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();

        dialog_cadProprietario.setTitle("Cadastrar Proprietário");
        dialog_cadProprietario.setBackground(new java.awt.Color(247, 247, 248));
        dialog_cadProprietario.setFont(new java.awt.Font("Poppins", 0, 10)); // NOI18N

        panelRound1.setBackground(new java.awt.Color(247, 247, 248));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        jLabel4.setText("Cadastrar Proprietário");

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(239, Short.MAX_VALUE))
            .addComponent(winButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addComponent(winButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 429, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialog_cadProprietarioLayout = new javax.swing.GroupLayout(dialog_cadProprietario.getContentPane());
        dialog_cadProprietario.getContentPane().setLayout(dialog_cadProprietarioLayout);
        dialog_cadProprietarioLayout.setHorizontalGroup(
            dialog_cadProprietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        dialog_cadProprietarioLayout.setVerticalGroup(
            dialog_cadProprietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PetStay");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);
        setResizable(false);

        painelGeral.setBackground(new java.awt.Color(255, 255, 255));
        painelGeral.setRoundBottomLeft(30);
        painelGeral.setRoundBottomRight(30);
        painelGeral.setRoundTopLeft(30);
        painelGeral.setRoundTopRight(30);
        painelGeral.setLayout(new javax.swing.BoxLayout(painelGeral, javax.swing.BoxLayout.PAGE_AXIS));

        header.setBackground(new java.awt.Color(255, 255, 255));
        header.setMaximumSize(new java.awt.Dimension(32767, 25));
        header.setRoundTopLeft(30);
        header.setRoundTopRight(30);

        winButton1.setMinimumSize(new java.awt.Dimension(57, 20));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(winButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(900, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(winButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addContainerGap())
        );

        painelGeral.add(header);

        body.setBackground(new java.awt.Color(255, 255, 255));
        body.setRoundBottomLeft(30);
        body.setRoundBottomRight(30);

        painelLateral.setBackground(new java.awt.Color(255, 255, 255));
        painelLateral.setRoundBottomLeft(30);

        area_rodape.setBackground(new java.awt.Color(247, 247, 248));
        area_rodape.setRoundBottomLeft(25);
        area_rodape.setRoundBottomRight(25);
        area_rodape.setRoundTopLeft(25);
        area_rodape.setRoundTopRight(25);

        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sair-alt.png"))); // NOI18N
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        userName.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        userName.setText("Lohran");

        javax.swing.GroupLayout area_rodapeLayout = new javax.swing.GroupLayout(area_rodape);
        area_rodape.setLayout(area_rodapeLayout);
        area_rodapeLayout.setHorizontalGroup(
            area_rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, area_rodapeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout)
                .addContainerGap())
        );
        area_rodapeLayout.setVerticalGroup(
            area_rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(area_rodapeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(area_rodapeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(userName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        btn_home.setBackground(new java.awt.Color(247, 247, 248));
        btn_home.setMaximumSize(new java.awt.Dimension(135, 41));
        btn_home.setRoundBottomLeft(25);
        btn_home.setRoundBottomRight(25);
        btn_home.setRoundTopLeft(25);
        btn_home.setRoundTopRight(25);
        btn_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_homeMouseClicked(evt);
            }
        });

        labelHoteis.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        labelHoteis.setForeground(new java.awt.Color(54, 54, 54));
        labelHoteis.setText("Hoteis");

        labelIconesHoteis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon-home.png"))); // NOI18N

        javax.swing.GroupLayout btn_homeLayout = new javax.swing.GroupLayout(btn_home);
        btn_home.setLayout(btn_homeLayout);
        btn_homeLayout.setHorizontalGroup(
            btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_homeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelIconesHoteis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelHoteis, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btn_homeLayout.setVerticalGroup(
            btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_homeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelHoteis, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(labelIconesHoteis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_proprietarios.setBackground(new java.awt.Color(247, 247, 248));
        btn_proprietarios.setMaximumSize(new java.awt.Dimension(135, 41));
        btn_proprietarios.setRoundBottomLeft(25);
        btn_proprietarios.setRoundBottomRight(25);
        btn_proprietarios.setRoundTopLeft(25);
        btn_proprietarios.setRoundTopRight(25);
        btn_proprietarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_proprietariosMouseClicked(evt);
            }
        });

        labelProprietario.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        labelProprietario.setForeground(new java.awt.Color(54, 54, 54));
        labelProprietario.setText("Proprietários");

        labelIconeProprietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon-proprietarios.png"))); // NOI18N

        javax.swing.GroupLayout btn_proprietariosLayout = new javax.swing.GroupLayout(btn_proprietarios);
        btn_proprietarios.setLayout(btn_proprietariosLayout);
        btn_proprietariosLayout.setHorizontalGroup(
            btn_proprietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_proprietariosLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(labelIconeProprietario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelProprietario, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btn_proprietariosLayout.setVerticalGroup(
            btn_proprietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_proprietariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_proprietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelProprietario, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(labelIconeProprietario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_pets.setBackground(new java.awt.Color(247, 247, 248));
        btn_pets.setMaximumSize(new java.awt.Dimension(135, 41));
        btn_pets.setRoundBottomLeft(25);
        btn_pets.setRoundBottomRight(25);
        btn_pets.setRoundTopLeft(25);
        btn_pets.setRoundTopRight(25);
        btn_pets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_petsMouseClicked(evt);
            }
        });

        labelPets.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        labelPets.setForeground(new java.awt.Color(54, 54, 54));
        labelPets.setText("Pets");

        labelIconPets.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon-pets.png"))); // NOI18N

        javax.swing.GroupLayout btn_petsLayout = new javax.swing.GroupLayout(btn_pets);
        btn_pets.setLayout(btn_petsLayout);
        btn_petsLayout.setHorizontalGroup(
            btn_petsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_petsLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(labelIconPets)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPets, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btn_petsLayout.setVerticalGroup(
            btn_petsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_petsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_petsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelPets, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(labelIconPets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_reservas.setBackground(new java.awt.Color(247, 247, 248));
        btn_reservas.setMaximumSize(new java.awt.Dimension(135, 41));
        btn_reservas.setRoundBottomLeft(25);
        btn_reservas.setRoundBottomRight(25);
        btn_reservas.setRoundTopLeft(25);
        btn_reservas.setRoundTopRight(25);
        btn_reservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_reservasMouseClicked(evt);
            }
        });

        labelReservas.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        labelReservas.setForeground(new java.awt.Color(54, 54, 54));
        labelReservas.setText("Reservas");

        labelIconReservas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon-reservas.png"))); // NOI18N

        javax.swing.GroupLayout btn_reservasLayout = new javax.swing.GroupLayout(btn_reservas);
        btn_reservas.setLayout(btn_reservasLayout);
        btn_reservasLayout.setHorizontalGroup(
            btn_reservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_reservasLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(labelIconReservas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btn_reservasLayout.setVerticalGroup(
            btn_reservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_reservasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_reservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelReservas, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(labelIconReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_servicos.setBackground(new java.awt.Color(247, 247, 248));
        btn_servicos.setMaximumSize(new java.awt.Dimension(135, 41));
        btn_servicos.setRoundBottomLeft(25);
        btn_servicos.setRoundBottomRight(25);
        btn_servicos.setRoundTopLeft(25);
        btn_servicos.setRoundTopRight(25);
        btn_servicos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_servicosMouseClicked(evt);
            }
        });

        labelServicos.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        labelServicos.setForeground(new java.awt.Color(54, 54, 54));
        labelServicos.setText("Serviços");

        labelIconServicos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon-servicos.png"))); // NOI18N

        javax.swing.GroupLayout btn_servicosLayout = new javax.swing.GroupLayout(btn_servicos);
        btn_servicos.setLayout(btn_servicosLayout);
        btn_servicosLayout.setHorizontalGroup(
            btn_servicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_servicosLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(labelIconServicos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btn_servicosLayout.setVerticalGroup(
            btn_servicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_servicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_servicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelServicos, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(labelIconServicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btn_relatorios.setBackground(new java.awt.Color(247, 247, 248));
        btn_relatorios.setMaximumSize(new java.awt.Dimension(135, 41));
        btn_relatorios.setRoundBottomLeft(25);
        btn_relatorios.setRoundBottomRight(25);
        btn_relatorios.setRoundTopLeft(25);
        btn_relatorios.setRoundTopRight(25);
        btn_relatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_relatoriosMouseClicked(evt);
            }
        });

        labelRelatorios.setBackground(new java.awt.Color(247, 247, 248));
        labelRelatorios.setFont(new java.awt.Font("Poppins SemiBold", 0, 13)); // NOI18N
        labelRelatorios.setForeground(new java.awt.Color(54, 54, 54));
        labelRelatorios.setText("Relatórios");

        labelIconRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon-relatorio.png"))); // NOI18N

        javax.swing.GroupLayout btn_relatoriosLayout = new javax.swing.GroupLayout(btn_relatorios);
        btn_relatorios.setLayout(btn_relatoriosLayout);
        btn_relatoriosLayout.setHorizontalGroup(
            btn_relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btn_relatoriosLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(labelIconRelatorios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelRelatorios, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btn_relatoriosLayout.setVerticalGroup(
            btn_relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_relatoriosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btn_relatoriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelRelatorios, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(labelIconRelatorios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout painelLateralLayout = new javax.swing.GroupLayout(painelLateral);
        painelLateral.setLayout(painelLateralLayout);
        painelLateralLayout.setHorizontalGroup(
            painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLateralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(area_rodape, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_proprietarios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_pets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_reservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_servicos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_relatorios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelLateralLayout.setVerticalGroup(
            painelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelLateralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_proprietarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_reservas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_servicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_relatorios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(area_rodape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelRound2.setBackground(new java.awt.Color(247, 247, 248));
        panelRound2.setRoundBottomRight(30);
        panelRound2.setRoundTopLeft(40);

        tabbledPanel.setBackground(new java.awt.Color(247, 247, 248));
        tabbledPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 247, 248)));
        tabbledPanel.setOpaque(true);

        painelHotel.setBackground(new java.awt.Color(247, 247, 248));
        painelHotel.setForeground(new java.awt.Color(247, 247, 248));

        jScrollPane2.setBackground(new java.awt.Color(247, 247, 248));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(247, 247, 248));
        jScrollPane2.setViewportView(panelCards);

        javax.swing.GroupLayout painelHotelLayout = new javax.swing.GroupLayout(painelHotel);
        painelHotel.setLayout(painelHotelLayout);
        painelHotelLayout.setHorizontalGroup(
            painelHotelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelHotelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        painelHotelLayout.setVerticalGroup(
            painelHotelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelHotelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbledPanel.addTab("tab1", painelHotel);

        painelProprietarios.setBackground(new java.awt.Color(247, 247, 248));

        jLabel5.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel5.setText("Proprietarios");

        jButton1.setBackground(new java.awt.Color(0, 130, 243));
        jButton1.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cadastrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTableProprietarios.setBackground(new java.awt.Color(247, 247, 248));
        jTableProprietarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "CPF", "Email", "Telefone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableProprietarios);

        javax.swing.GroupLayout painelProprietariosLayout = new javax.swing.GroupLayout(painelProprietarios);
        painelProprietarios.setLayout(painelProprietariosLayout);
        painelProprietariosLayout.setHorizontalGroup(
            painelProprietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProprietariosLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 539, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelProprietariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        painelProprietariosLayout.setVerticalGroup(
            painelProprietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProprietariosLayout.createSequentialGroup()
                .addGroup(painelProprietariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbledPanel.addTab("tab2", painelProprietarios);

        painelPets.setBackground(new java.awt.Color(247, 247, 248));

        jButton2.setBackground(new java.awt.Color(0, 130, 243));
        jButton2.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Cadastrar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel19.setText("Pets");

        jTablePets.setBackground(new java.awt.Color(247, 247, 248));
        jTablePets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Dono", "Nome", "Raça", "Espécie"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTablePets);

        javax.swing.GroupLayout painelPetsLayout = new javax.swing.GroupLayout(painelPets);
        painelPets.setLayout(painelPetsLayout);
        painelPetsLayout.setHorizontalGroup(
            painelPetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPetsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPetsLayout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelPetsLayout.setVerticalGroup(
            painelPetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPetsLayout.createSequentialGroup()
                .addGroup(painelPetsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel19))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbledPanel.addTab("tab3", painelPets);

        painelReservas.setBackground(new java.awt.Color(247, 247, 248));

        jButton3.setBackground(new java.awt.Color(0, 130, 243));
        jButton3.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cadastrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel20.setText("Reservas");

        jTableReservas.setBackground(new java.awt.Color(247, 247, 248));
        jTableReservas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "ID Pet", "ID Cliente", "Data Inicio", "Data Fim", "Serviço", "Ativo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTableReservas);
        if (jTableReservas.getColumnModel().getColumnCount() > 0) {
            jTableReservas.getColumnModel().getColumn(6).setHeaderValue("Ativo");
        }

        javax.swing.GroupLayout painelReservasLayout = new javax.swing.GroupLayout(painelReservas);
        painelReservas.setLayout(painelReservasLayout);
        painelReservasLayout.setHorizontalGroup(
            painelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelReservasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelReservasLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelReservasLayout.setVerticalGroup(
            painelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelReservasLayout.createSequentialGroup()
                .addGroup(painelReservasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel20))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbledPanel.addTab("tab4", painelReservas);

        painelServicos.setBackground(new java.awt.Color(247, 247, 248));

        jButton4.setBackground(new java.awt.Color(0, 130, 243));
        jButton4.setFont(new java.awt.Font("Poppins SemiBold", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cadastrar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel21.setText("Serviços");

        jTableServicos.setBackground(new java.awt.Color(247, 247, 248));
        jTableServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Descrição", "Tipo", "Valor", "Ativo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(jTableServicos);

        javax.swing.GroupLayout painelServicosLayout = new javax.swing.GroupLayout(painelServicos);
        painelServicos.setLayout(painelServicosLayout);
        painelServicosLayout.setHorizontalGroup(
            painelServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelServicosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelServicosLayout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
                .addContainerGap())
        );
        painelServicosLayout.setVerticalGroup(
            painelServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelServicosLayout.createSequentialGroup()
                .addGroup(painelServicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jLabel21))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbledPanel.addTab("tab5", painelServicos);

        jPanel6.setBackground(new java.awt.Color(247, 247, 248));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 798, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 473, Short.MAX_VALUE)
        );

        tabbledPanel.addTab("tab6", jPanel6);

        jPanel7.setBackground(new java.awt.Color(247, 247, 248));

        painelPets1.setBackground(new java.awt.Color(255, 255, 255));
        painelPets1.setMaximumSize(new java.awt.Dimension(150, 60));
        painelPets1.setMinimumSize(new java.awt.Dimension(150, 60));
        painelPets1.setPreferredSize(new java.awt.Dimension(150, 60));
        painelPets1.setRoundBottomLeft(25);
        painelPets1.setRoundBottomRight(25);
        painelPets1.setRoundTopLeft(25);
        painelPets1.setRoundTopRight(25);

        jLabel2.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel2.setText("Total de Pets");

        valTotalPets.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        valTotalPets.setText("1000");

        javax.swing.GroupLayout painelPets1Layout = new javax.swing.GroupLayout(painelPets1);
        painelPets1.setLayout(painelPets1Layout);
        painelPets1Layout.setHorizontalGroup(
            painelPets1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPets1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(painelPets1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valTotalPets, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        painelPets1Layout.setVerticalGroup(
            painelPets1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPets1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(valTotalPets)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(painelPets1);

        painelReservas1.setBackground(new java.awt.Color(255, 255, 255));
        painelReservas1.setPreferredSize(new java.awt.Dimension(150, 60));
        painelReservas1.setRoundBottomLeft(25);
        painelReservas1.setRoundBottomRight(25);
        painelReservas1.setRoundTopLeft(25);
        painelReservas1.setRoundTopRight(25);

        jLabel18.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel18.setText("Total de Reservas");

        valTotalReservas.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        valTotalReservas.setText("1000");

        javax.swing.GroupLayout painelReservas1Layout = new javax.swing.GroupLayout(painelReservas1);
        painelReservas1.setLayout(painelReservas1Layout);
        painelReservas1Layout.setHorizontalGroup(
            painelReservas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelReservas1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(painelReservas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valTotalReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        painelReservas1Layout.setVerticalGroup(
            painelReservas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelReservas1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel18)
                .addGap(5, 5, 5)
                .addComponent(valTotalReservas))
        );

        jPanel7.add(painelReservas1);

        painelServicos1.setBackground(new java.awt.Color(255, 255, 255));
        painelServicos1.setPreferredSize(new java.awt.Dimension(150, 60));
        painelServicos1.setRoundBottomLeft(25);
        painelServicos1.setRoundBottomRight(25);
        painelServicos1.setRoundTopLeft(25);
        painelServicos1.setRoundTopRight(25);

        jLabel24.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel24.setText("Total de Serviços");

        valTotalServicos.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        valTotalServicos.setText("1000");

        javax.swing.GroupLayout painelServicos1Layout = new javax.swing.GroupLayout(painelServicos1);
        painelServicos1.setLayout(painelServicos1Layout);
        painelServicos1Layout.setHorizontalGroup(
            painelServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelServicos1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(painelServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valTotalServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        painelServicos1Layout.setVerticalGroup(
            painelServicos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelServicos1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel24)
                .addGap(5, 5, 5)
                .addComponent(valTotalServicos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(painelServicos1);

        painelProprietarios1.setBackground(new java.awt.Color(255, 255, 255));
        painelProprietarios1.setMaximumSize(new java.awt.Dimension(150, 60));
        painelProprietarios1.setMinimumSize(new java.awt.Dimension(150, 60));
        painelProprietarios1.setPreferredSize(new java.awt.Dimension(150, 60));
        painelProprietarios1.setRoundBottomLeft(25);
        painelProprietarios1.setRoundBottomRight(25);
        painelProprietarios1.setRoundTopLeft(25);
        painelProprietarios1.setRoundTopRight(25);

        jLabel27.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel27.setText("Total de Proprietarios");

        valTotalProprietarios.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        valTotalProprietarios.setText("1000");

        javax.swing.GroupLayout painelProprietarios1Layout = new javax.swing.GroupLayout(painelProprietarios1);
        painelProprietarios1.setLayout(painelProprietarios1Layout);
        painelProprietarios1Layout.setHorizontalGroup(
            painelProprietarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProprietarios1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(painelProprietarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valTotalProprietarios, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        painelProprietarios1Layout.setVerticalGroup(
            painelProprietarios1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelProprietarios1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel27)
                .addGap(5, 5, 5)
                .addComponent(valTotalProprietarios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(painelProprietarios1);

        painelFinanceiro.setBackground(new java.awt.Color(255, 255, 255));
        painelFinanceiro.setRoundBottomLeft(25);
        painelFinanceiro.setRoundBottomRight(25);
        painelFinanceiro.setRoundTopLeft(25);
        painelFinanceiro.setRoundTopRight(25);

        valTotalVendas.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        valTotalVendas.setText("R$10.000");

        jLabel29.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel29.setText("Total de Vendas");

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dog_money.png"))); // NOI18N

        javax.swing.GroupLayout painelFinanceiroLayout = new javax.swing.GroupLayout(painelFinanceiro);
        painelFinanceiro.setLayout(painelFinanceiroLayout);
        painelFinanceiroLayout.setHorizontalGroup(
            painelFinanceiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFinanceiroLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(painelFinanceiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valTotalVendas)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel22)
                .addGap(23, 23, 23))
        );
        painelFinanceiroLayout.setVerticalGroup(
            painelFinanceiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFinanceiroLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(painelFinanceiroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelFinanceiroLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valTotalVendas))
                    .addComponent(jLabel22))
                .addGap(39, 39, 39))
        );

        jPanel7.add(painelFinanceiro);

        tabbledPanel.addTab("tab7", jPanel7);

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbledPanel)
                .addContainerGap())
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbledPanel)
                .addContainerGap())
        );

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyLayout.createSequentialGroup()
                .addComponent(painelLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        painelGeral.add(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelGeral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelGeral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // fazer logout e ir para login
        int resp = JOptionPane.showConfirmDialog(this, "Deseja sair e retornar para a tela de login?", "Confirmar logout", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            try {
                Session.getInstance().clear();
            } catch (Exception ex) {
                logger.log(java.util.logging.Level.WARNING, "Erro ao limpar sessão", ex);
            }
            FrLogin login = new FrLogin();
            login.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void btn_relatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_relatoriosMouseClicked
        tabbledPanel.setSelectedIndex(6);
        carregarRelatorio();
    }//GEN-LAST:event_btn_relatoriosMouseClicked

    private void btn_servicosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_servicosMouseClicked
       tabbledPanel.setSelectedIndex(4);
    }//GEN-LAST:event_btn_servicosMouseClicked

    private void btn_reservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_reservasMouseClicked
        tabbledPanel.setSelectedIndex(3);
    }//GEN-LAST:event_btn_reservasMouseClicked

    private void btn_petsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_petsMouseClicked
        tabbledPanel.setSelectedIndex(2);
    }//GEN-LAST:event_btn_petsMouseClicked

    private void btn_proprietariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_proprietariosMouseClicked
        tabbledPanel.setSelectedIndex(1);
    }//GEN-LAST:event_btn_proprietariosMouseClicked

    private void btn_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseClicked
        tabbledPanel.setSelectedIndex(0);
    }//GEN-LAST:event_btn_homeMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new CadastroUsuarioDialog(this).setVisible(true);
        carregarTabela();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       new CadastroPetsDialog(this).setVisible(true);
       carregarTabelaPets();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       new CadastroReservaDialog(this).setVisible(true);
       carregarTabelaReservas();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new CadastroServicosDialog(this).setVisible(true);
        carregarTabelaServicos();
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.components.PanelRound area_rodape;
    private view.components.PanelRound body;
    private javax.swing.JLabel btnLogout;
    private view.components.PanelRound btn_home;
    private view.components.PanelRound btn_pets;
    private view.components.PanelRound btn_proprietarios;
    private view.components.PanelRound btn_relatorios;
    private view.components.PanelRound btn_reservas;
    private view.components.PanelRound btn_servicos;
    private javax.swing.JDialog dialog_cadProprietario;
    private view.components.PanelRound header;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTablePets;
    private javax.swing.JTable jTableProprietarios;
    private javax.swing.JTable jTableReservas;
    private javax.swing.JTable jTableServicos;
    private javax.swing.JLabel labelHoteis;
    private javax.swing.JLabel labelIconPets;
    private javax.swing.JLabel labelIconRelatorios;
    private javax.swing.JLabel labelIconReservas;
    private javax.swing.JLabel labelIconServicos;
    private javax.swing.JLabel labelIconeProprietario;
    private javax.swing.JLabel labelIconesHoteis;
    private javax.swing.JLabel labelPets;
    private javax.swing.JLabel labelProprietario;
    private javax.swing.JLabel labelRelatorios;
    private javax.swing.JLabel labelReservas;
    private javax.swing.JLabel labelServicos;
    private view.components.PanelRound painelFinanceiro;
    private view.components.PanelRound painelGeral;
    private javax.swing.JPanel painelHotel;
    private view.components.PanelRound painelLateral;
    private javax.swing.JPanel painelPets;
    private view.components.PanelRound painelPets1;
    private javax.swing.JPanel painelProprietarios;
    private view.components.panelCards painelProprietarios1;
    private javax.swing.JPanel painelReservas;
    private view.components.panelCards painelReservas1;
    private javax.swing.JPanel painelServicos;
    private view.components.panelCards painelServicos1;
    private view.components.panelCards panelCards;
    private view.components.PanelRound panelRound1;
    private view.components.PanelRound panelRound2;
    private view.components.PanelRound panelRound3;
    private javax.swing.JTabbedPane tabbledPanel;
    private javax.swing.JLabel userName;
    private javax.swing.JLabel valTotalPets;
    private javax.swing.JLabel valTotalProprietarios;
    private javax.swing.JLabel valTotalReservas;
    private javax.swing.JLabel valTotalServicos;
    private javax.swing.JLabel valTotalVendas;
    private view.components.win_button.WinButton winButton1;
    private view.components.win_button.WinButton winButton2;
    // End of variables declaration//GEN-END:variables
}
