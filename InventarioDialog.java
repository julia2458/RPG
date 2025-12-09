import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class InventarioDialog extends JDialog {
    private Personagem jogador;
    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;
    
    public InventarioDialog(JFrame parent, Personagem jogador) {
        super(parent, "Inventário", true);
        this.jogador = jogador;
        
        configurarDialog();
        criarComponentes();
        carregarItens();
    }
    
    private void configurarDialog() {
        setSize(700, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 40, 55));
    }
    
    private void criarComponentes() {
        // Título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
        painelTitulo.setBackground(new Color(30, 40, 55));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel lblTitulo = new JLabel("INVENTÁRIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Gerencie seus itens e poções", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelTitulo.add(lblTitulo);
        painelTitulo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelTitulo.add(lblSubtitulo);
        
        add(painelTitulo, BorderLayout.NORTH);
        
        // Painel central com tabela
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(new Color(30, 40, 55));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Criar tabela
        String[] colunas = {"Nome", "Tipo", "Quantidade", "Detalhes"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaItens = new JTable(modeloTabela);
        tabelaItens.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaItens.setRowHeight(35);
        tabelaItens.setBackground(new Color(40, 50, 65));
        tabelaItens.setForeground(Color.WHITE);
        tabelaItens.setSelectionBackground(new Color(70, 90, 120));
        tabelaItens.setSelectionForeground(Color.WHITE);
        tabelaItens.setGridColor(new Color(60, 70, 85));
        tabelaItens.setShowGrid(true);
        tabelaItens.setIntercellSpacing(new Dimension(1, 1));
        
        // Renderizador para linhas alternadas
        tabelaItens.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(40, 50, 65) : new Color(45, 55, 70));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(70, 90, 120));
                    c.setForeground(Color.WHITE);
                }
                return c;
            }
        });
        
        // Cabeçalho da tabela
        JTableHeader cabecalho = tabelaItens.getTableHeader();
        cabecalho.setFont(new Font("Arial", Font.BOLD, 15));
        cabecalho.setBackground(new Color(25, 35, 50));
        cabecalho.setForeground(new Color(255, 215, 0));
        cabecalho.setReorderingAllowed(false);
        
        // Centralizar algumas colunas
        DefaultTableCellRenderer centralizador = new DefaultTableCellRenderer();
        centralizador.setHorizontalAlignment(SwingConstants.CENTER);
        tabelaItens.getColumnModel().getColumn(1).setCellRenderer(centralizador);
        tabelaItens.getColumnModel().getColumn(2).setCellRenderer(centralizador);
        
        // Ajustar larguras das colunas
        tabelaItens.getColumnModel().getColumn(0).setPreferredWidth(180);
        tabelaItens.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelaItens.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelaItens.getColumnModel().getColumn(3).setPreferredWidth(250);
        
        JScrollPane scrollTabela = new JScrollPane(tabelaItens);
        scrollTabela.setBorder(BorderFactory.createLineBorder(new Color(100, 120, 150), 2));
        scrollTabela.getViewport().setBackground(new Color(40, 50, 65));
        
        painelCentral.add(scrollTabela, BorderLayout.CENTER);
        
        // Painel de informações
        JPanel painelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelInfo.setBackground(new Color(30, 40, 55));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JLabel lblInfo = new JLabel("Total de itens: 0");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 14));
        lblInfo.setForeground(new Color(150, 200, 255));
        
        painelInfo.add(lblInfo);
        painelCentral.add(painelInfo, BorderLayout.SOUTH);
        
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior com botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(new Color(30, 40, 55));
        
        JButton btnUsarItem = new JButton("Usar Item");
        btnUsarItem.setFont(new Font("Arial", Font.BOLD, 14));
        btnUsarItem.setBackground(new Color(50, 150, 50));
        btnUsarItem.setForeground(Color.WHITE);
        btnUsarItem.setFocusPainted(false);
        btnUsarItem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUsarItem.setToolTipText("Usar o item selecionado");
        btnUsarItem.addActionListener(e -> usarItem());
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setBackground(new Color(100, 100, 100));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFechar.setToolTipText("Fechar o inventário");
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnUsarItem);
        painelBotoes.add(btnFechar);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void carregarItens() {
        modeloTabela.setRowCount(0);
        
        try {
            Inventario inventario = jogador.getInventario();
            List<Item> itens = inventario.listarItensMochilaOrdenados();
            
            for (Item item : itens) {
                String nome = item.getNome();
                String tipo = identificarTipo(item);
                String quantidade = obterQuantidade(item);
                String detalhes = obterDetalhes(item);
                
                modeloTabela.addRow(new Object[]{nome, tipo, quantidade, detalhes});
            }
            
            if (itens.isEmpty()) {
                JLabel lblVazio = new JLabel("Inventário vazio", SwingConstants.CENTER);
                lblVazio.setFont(new Font("Arial", Font.ITALIC, 16));
                lblVazio.setForeground(Color.GRAY);
            }
            
            // Atualizar contador
            Component[] componentes = ((JPanel)((JPanel)getContentPane().getComponent(1)).getComponent(1)).getComponents();
            if (componentes.length > 0 && componentes[0] instanceof JLabel) {
                ((JLabel)componentes[0]).setText("Total de itens: " + itens.size());
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao carregar inventário: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String identificarTipo(Item item) {
        if (item instanceof PocaoDeCura) {
            return "Poção";
        } else if (item instanceof Artefato) {
            return "Artefato";
        } else if (item instanceof LanternaEncantada) {
            return "Artefato";
        }
        return "Item";
    }
    
    private String obterQuantidade(Item item) {
        if (item instanceof PocaoDeCura) {
            return String.valueOf(((PocaoDeCura)item).getQuantidade());
        } else if (item instanceof Artefato) {
            return "1";
        }
        return "1";
    }
    
    private String obterDetalhes(Item item) {
        if (item instanceof PocaoDeCura) {
            PocaoDeCura pocao = (PocaoDeCura)item;
            return "Restaura " + pocao.getPontosDeVidaRestaurados() + " HP";
        } else if (item instanceof LanternaEncantada) {
            LanternaEncantada lanterna = (LanternaEncantada)item;
            return lanterna.getDescricao();
        } else if (item instanceof Artefato) {
            Artefato artefato = (Artefato)item;
            return artefato.getDescricao();
        }
        return "-";
    }
    
    private void usarItem() {
        int linhaSelecionada = tabelaItens.getSelectedRow();
        
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Selecione um item para usar!",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String nomeItem = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String tipoItem = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
            
            if (tipoItem.equals("Poção")) {
                usarPocao(nomeItem);
            } else if (tipoItem.equals("Artefato")) {
                // Registrar artefato coletado no sistema de quests
                if (getParent() instanceof RPGGameGUI) {
                    ((RPGGameGUI)getParent()).registrarArtefatoColetado(nomeItem);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Artefato: " + nomeItem + "\n\n" +
                        "Este item é um artefato especial!\n" +
                        "Ele está equipado e oferece poderes únicos.",
                        "Artefato Mágico", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Este item não pode ser usado agora.",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao usar item: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void usarPocao(String nome) {
        try {
            Inventario inventario = jogador.getInventario();
            List<Item> itens = inventario.listarItensMochilaOrdenados();
            
            for (Item item : itens) {
                if (item instanceof PocaoDeCura && item.getNome().equals(nome)) {
                    PocaoDeCura pocao = (PocaoDeCura)item;
                    
                    if (pocao.getQuantidade() <= 0) {
                        JOptionPane.showMessageDialog(this,
                            "Você não tem mais poções!",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    int cura = pocao.getPontosDeVidaRestaurados();
                    jogador.setPontosVida(jogador.getPontosVida() + cura);
                    pocao.setQuantidade((byte)(pocao.getQuantidade() - 1));
                    
                    JOptionPane.showMessageDialog(this,
                        "Você usou " + nome + "!\nHP restaurado: +" + cura,
                        "Item Usado", JOptionPane.INFORMATION_MESSAGE);
                    
                    carregarItens();
                    return;
                }
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Erro ao usar poção: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
