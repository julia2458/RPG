import javax.swing.*;
import java.awt.*;
import java.util.List;

public class QuestsDialog extends JDialog {
    private SistemaQuests sistemaQuests;
    
    public QuestsDialog(JFrame parent, SistemaQuests sistemaQuests) {
        super(parent, "Missões e Conquistas", true);
        this.sistemaQuests = sistemaQuests;
        
        configurarDialog();
        criarComponentes();
    }
    
    private void configurarDialog() {
        setSize(750, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(25, 30, 45));
    }
    
    private void criarComponentes() {
        // Título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
        painelTitulo.setBackground(new Color(25, 30, 45));
        painelTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));
        
        JLabel lblTitulo = new JLabel("MISSÕES E CONQUISTAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Complete missões para ganhar recompensas");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelTitulo.add(lblTitulo);
        painelTitulo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelTitulo.add(lblSubtitulo);
        
        add(painelTitulo, BorderLayout.NORTH);
        
        // Painel central com tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(30, 40, 55));
        tabbedPane.setForeground(Color.WHITE);
        
        // Tab de Missões Ativas
        JPanel painelAtivas = criarPainelQuests(sistemaQuests.getQuestsAtivas(), false);
        tabbedPane.addTab("Ativas", painelAtivas);
        
        // Tab de Missões Concluídas
        JPanel painelConcluidas = criarPainelQuests(sistemaQuests.getQuestsConcluidas(), true);
        tabbedPane.addTab("Concluídas", painelConcluidas);
        
        // Tab de Conquistas
        JPanel painelConquistas = criarPainelConquistas();
        tabbedPane.addTab("Conquistas", painelConquistas);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Botão fechar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(new Color(25, 30, 45));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(new Font("Arial", Font.BOLD, 14));
        btnFechar.setBackground(new Color(50, 150, 50));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setPreferredSize(new Dimension(150, 40));
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnFechar);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelQuests(List<Quest> quests, boolean concluidas) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(30, 40, 55));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        if (quests.isEmpty()) {
            JLabel lblVazio = new JLabel(concluidas ? 
                "Nenhuma missão concluída ainda!" : 
                "Todas as missões foram concluídas!");
            lblVazio.setFont(new Font("Arial", Font.ITALIC, 16));
            lblVazio.setForeground(new Color(150, 150, 150));
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(Box.createVerticalGlue());
            painel.add(lblVazio);
            painel.add(Box.createVerticalGlue());
        } else {
            for (Quest quest : quests) {
                JPanel cartaoQuest = criarCartaoQuest(quest);
                painel.add(cartaoQuest);
                painel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        
        JScrollPane scroll = new JScrollPane(painel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }
    
    private JPanel criarCartaoQuest(Quest quest) {
        JPanel cartao = new JPanel();
        cartao.setLayout(new BorderLayout(10, 10));
        cartao.setBackground(quest.concluida ? new Color(40, 60, 50) : new Color(45, 55, 70));
        cartao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                quest.concluida ? new Color(80, 200, 120) : new Color(100, 120, 150), 
                2
            ),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        cartao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        
        // Ícone do tipo de quest
        String icone = switch (quest.tipo) {
            case PRINCIPAL -> "[P]";
            case EXPLORACAO -> "[E]";
            case COMBATE -> "[B]";
            case COLETA -> "[C]";
        };
        
        JLabel lblIcone = new JLabel(icone);
        lblIcone.setFont(new Font("Arial", Font.PLAIN, 36));
        cartao.add(lblIcone, BorderLayout.WEST);
        
        // Informações da quest
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBackground(quest.concluida ? new Color(40, 60, 50) : new Color(45, 55, 70));
        
        JLabel lblNome = new JLabel(quest.nome);
        lblNome.setFont(new Font("Arial", Font.BOLD, 18));
        lblNome.setForeground(quest.concluida ? new Color(120, 255, 150) : new Color(255, 215, 0));
        
        JLabel lblDescricao = new JLabel(quest.descricao);
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDescricao.setForeground(new Color(200, 200, 200));
        
        JLabel lblDica = new JLabel("Dica: " + quest.dica);
        lblDica.setFont(new Font("Arial", Font.ITALIC, 12));
        lblDica.setForeground(new Color(150, 180, 255));
        
        // Barra de progresso
        JPanel painelProgresso = new JPanel(new BorderLayout(5, 0));
        painelProgresso.setBackground(quest.concluida ? new Color(40, 60, 50) : new Color(45, 55, 70));
        painelProgresso.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        
        JLabel lblProgresso = new JLabel(quest.concluida ? "✓ COMPLETA" : quest.progresso + "/" + quest.objetivo);
        lblProgresso.setFont(new Font("Arial", Font.BOLD, 12));
        lblProgresso.setForeground(Color.WHITE);
        lblProgresso.setPreferredSize(new Dimension(100, 20));
        
        JProgressBar barraProgresso = new JProgressBar(0, 100);
        barraProgresso.setValue(quest.getProgresso());
        barraProgresso.setStringPainted(false);
        barraProgresso.setForeground(quest.concluida ? new Color(80, 200, 120) : new Color(100, 150, 255));
        barraProgresso.setBackground(new Color(30, 40, 50));
        
        painelProgresso.add(lblProgresso, BorderLayout.WEST);
        painelProgresso.add(barraProgresso, BorderLayout.CENTER);
        
        JLabel lblRecompensa = new JLabel(quest.recompensaXP + " XP");
        lblRecompensa.setFont(new Font("Arial", Font.BOLD, 13));
        lblRecompensa.setForeground(new Color(255, 200, 100));
        lblRecompensa.setPreferredSize(new Dimension(80, 20));
        painelProgresso.add(lblRecompensa, BorderLayout.EAST);
        
        painelInfo.add(lblNome);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelInfo.add(lblDescricao);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelInfo.add(lblDica);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        painelInfo.add(painelProgresso);
        
        cartao.add(painelInfo, BorderLayout.CENTER);
        
        return cartao;
    }
    
    private JPanel criarPainelConquistas() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(30, 40, 55));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        List<String> conquistas = sistemaQuests.getConquistas();
        
        if (conquistas.isEmpty()) {
            JLabel lblVazio = new JLabel("Complete missões para desbloquear conquistas!");
            lblVazio.setFont(new Font("Arial", Font.ITALIC, 16));
            lblVazio.setForeground(new Color(150, 150, 150));
            lblVazio.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(Box.createVerticalGlue());
            painel.add(lblVazio);
            painel.add(Box.createVerticalGlue());
        } else {
            JLabel lblTitulo = new JLabel("Conquistas Desbloqueadas:");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(new Color(255, 215, 0));
            painel.add(lblTitulo);
            painel.add(Box.createRigidArea(new Dimension(0, 15)));
            
            for (String conquista : conquistas) {
                JLabel lblConquista = new JLabel(conquista);
                lblConquista.setFont(new Font("Arial", Font.PLAIN, 16));
                lblConquista.setForeground(Color.WHITE);
                lblConquista.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
                lblConquista.setOpaque(true);
                lblConquista.setBackground(new Color(50, 60, 75));
                lblConquista.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                
                painel.add(lblConquista);
                painel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
            
            painel.add(Box.createVerticalGlue());
        }
        
        JScrollPane scroll = new JScrollPane(painel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }
}
