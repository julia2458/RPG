import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RPGGameGUI extends JFrame {
    // Componentes principais
    private JPanel painelPrincipal;
    private CardLayout cardLayout;
    
    // Painéis de diferentes telas
    private JPanel painelInicio;
    private JPanel painelSelecaoPersonagem;
    private JPanel painelJogo;
    
    // Componentes do jogo
    private Personagem jogador;
    private AcampamentoMisterioMapa mapa;
    private ContextoJogo contexto;
    private int xpTotal = 0;
    private int nivel = 1;
    private int hpMaximo;
    private Set<String> locaisVisitados = new HashSet<>();
    private Set<String> artefatosColetados = new HashSet<>();
    private boolean bossVencido = false;
    
    // Componentes visuais dinâmicos
    private JProgressBar barraHP;
    private JProgressBar barraXP;
    private JLabel lblHPTexto;
    private JLabel lblXPTexto;
    
    // Novos sistemas de jogo
    private SistemaQuests sistemaQuests;
    private EventosAleatorios eventosAleatorios;
    private int inimigosDerrotados = 0;
    private Random random = new Random();
    private String questProgressMessage = "";
    
    public RPGGameGUI() {
        sistemaQuests = new SistemaQuests();
        eventosAleatorios = new EventosAleatorios();
        configurarJanela();
        inicializarComponentes();
        mostrarTelaInicio();
    }
    
    private void configurarJanela() {
        setTitle("Acampamento Misterio - RPG Interativo");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);
        add(painelPrincipal);
    }
    
    private void inicializarComponentes() {
        criarPainelInicio();
        criarPainelSelecaoPersonagem();
        criarPainelJogo();
        
        painelPrincipal.add(painelInicio, "INICIO");
        painelPrincipal.add(painelSelecaoPersonagem, "SELECAO");
        painelPrincipal.add(painelJogo, "JOGO");
    }
    
    private void criarPainelInicio() {
        painelInicio = new JPanel();
        painelInicio.setLayout(new BorderLayout());
        painelInicio.setBackground(new Color(20, 30, 48));
        
        // Painel central com título e introdução
        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBackground(new Color(20, 30, 48));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // Título
        JLabel lblTitulo = new JLabel("ACAMPAMENTO MISTÉRIO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 42));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("RPG Interativo");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 24));
        lblSubtitulo.setForeground(new Color(200, 200, 200));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Área de texto com história
        JTextArea txtHistoria = new JTextArea();
        txtHistoria.setText(
            "\n\nBem-vindo ao Acampamento Mistério!\n\n" +
            "Um lugar mágico onde aventuras e enigmas aguardam...\n\n" +
            "Uma força misteriosa está desequilibrando a harmonia do acampamento.\n" +
            "Você e suas amigas devem explorar locais mágicos, enfrentar criaturas\n" +
            "sombrias e descobrir os segredos que mantêm este lugar em equilíbrio.\n\n" +
            "Prepare-se para uma aventura inesquecível!"
        );
        txtHistoria.setFont(new Font("Arial", Font.PLAIN, 16));
        txtHistoria.setForeground(Color.WHITE);
        txtHistoria.setBackground(new Color(30, 40, 60));
        txtHistoria.setEditable(false);
        txtHistoria.setLineWrap(true);
        txtHistoria.setWrapStyleWord(true);
        txtHistoria.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botão iniciar
        JButton btnIniciar = new JButton("INICIAR AVENTURA");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 20));
        btnIniciar.setBackground(new Color(50, 150, 50));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorderPainted(false);
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.setMaximumSize(new Dimension(300, 50));
        btnIniciar.addActionListener(e -> mostrarSelecaoPersonagem());
        
        painelCentro.add(lblTitulo);
        painelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
        painelCentro.add(lblSubtitulo);
        painelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
        painelCentro.add(txtHistoria);
        painelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
        painelCentro.add(btnIniciar);
        
        painelInicio.add(painelCentro, BorderLayout.CENTER);
    }
    
    private void criarPainelSelecaoPersonagem() {
        painelSelecaoPersonagem = new JPanel();
        painelSelecaoPersonagem.setLayout(new BorderLayout());
        painelSelecaoPersonagem.setBackground(new Color(25, 35, 50));
        
        // Título
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
        painelTitulo.setBackground(new Color(25, 35, 50));
        
        JLabel lblTitulo = new JLabel("ESCOLHA SUA HEROÍNA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Cada personagem possui habilidades únicas", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelTitulo.add(Box.createRigidArea(new Dimension(0, 20)));
        painelTitulo.add(lblTitulo);
        painelTitulo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelTitulo.add(lblSubtitulo);
        painelTitulo.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Painel de personagens
        JPanel painelPersonagens = new JPanel();
        painelPersonagens.setLayout(new GridLayout(1, 5, 15, 15));
        painelPersonagens.setBackground(new Color(25, 35, 50));
        painelPersonagens.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        
        // Criar botões para cada personagem com informações detalhadas
        String[][] personagens = {
            {"Luna", "Tanque Defensiva", "180", "20", "30", 
             "Resistente e corajosa", "Excelente para iniciantes",
             "Alta sobrevivência", "Dano moderado"},
            {"Clara", "Maga Poderosa", "95", "35", "10",
             "Inteligente e estratégica", "Alto risco, alta recompensa",
             "Máximo dano mágico", "Baixa defesa"},
            {"Bia", "Guerreira Versátil", "110", "25", "20",
             "Equilibrada e adaptável", "Boa para qualquer situação",
             "Stats balanceados", "Sem fraquezas críticas"},
            {"Sofia", "Guardiã Inabalável", "130", "15", "35",
             "Protetora e resiliente", "Dificilmente derrotada",
             "Defesa máxima", "Dano baixo"},
            {"Yasmin", "Assassina Ágil", "110", "30", "15",
             "Rápida e letal", "Para jogadores agressivos",
             "Alto dano físico", "Defesa razoável"}
        };
        
        for (int i = 0; i < personagens.length; i++) {
            final int index = i;
            JButton btnPersonagem = criarBotaoPersonagemDetalhado(personagens[i]);
            btnPersonagem.addActionListener(e -> selecionarPersonagem(index));
            painelPersonagens.add(btnPersonagem);
        }
        
        painelSelecaoPersonagem.add(painelTitulo, BorderLayout.NORTH);
        painelSelecaoPersonagem.add(painelPersonagens, BorderLayout.CENTER);
    }
    
    private JButton criarBotaoPersonagemDetalhado(String[] info) {
        JButton btn = new JButton();
        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setBackground(new Color(40, 50, 70));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 120, 150), 2),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        btn.setFocusPainted(false);
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 80, 110));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 0), 3),
                    BorderFactory.createEmptyBorder(14, 9, 14, 9)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(40, 50, 70));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 120, 150), 2),
                    BorderFactory.createEmptyBorder(15, 10, 15, 10)
                ));
            }
        });
        
        // Nome e tipo
        JLabel lblNome = new JLabel(info[0], SwingConstants.CENTER);
        lblNome.setFont(new Font("Arial", Font.BOLD, 20));
        lblNome.setForeground(new Color(255, 215, 0));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTipo = new JLabel(info[1], SwingConstants.CENTER);
        lblTipo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTipo.setForeground(new Color(150, 200, 255));
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Stats com ícones
        JPanel painelStats = new JPanel();
        painelStats.setLayout(new BoxLayout(painelStats, BoxLayout.Y_AXIS));
        painelStats.setOpaque(false);
        painelStats.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JLabel lblHP = new JLabel("HP: " + info[2], SwingConstants.CENTER);
        lblHP.setFont(new Font("Arial", Font.BOLD, 12));
        lblHP.setForeground(new Color(255, 100, 100));
        lblHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblATK = new JLabel("ATK: " + info[3], SwingConstants.CENTER);
        lblATK.setFont(new Font("Arial", Font.BOLD, 12));
        lblATK.setForeground(new Color(255, 150, 50));
        lblATK.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDEF = new JLabel("DEF: " + info[4], SwingConstants.CENTER);
        lblDEF.setFont(new Font("Arial", Font.BOLD, 12));
        lblDEF.setForeground(new Color(100, 200, 255));
        lblDEF.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelStats.add(lblHP);
        painelStats.add(Box.createRigidArea(new Dimension(0, 3)));
        painelStats.add(lblATK);
        painelStats.add(Box.createRigidArea(new Dimension(0, 3)));
        painelStats.add(lblDEF);
        
        // Separador
        JSeparator separador = new JSeparator();
        separador.setForeground(new Color(100, 120, 150));
        separador.setMaximumSize(new Dimension(150, 1));
        
        // Descrição
        JLabel lblDesc1 = new JLabel(info[5], SwingConstants.CENTER);
        lblDesc1.setFont(new Font("Arial", Font.ITALIC, 11));
        lblDesc1.setForeground(new Color(200, 200, 200));
        lblDesc1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDesc2 = new JLabel(info[6], SwingConstants.CENTER);
        lblDesc2.setFont(new Font("Arial", Font.PLAIN, 10));
        lblDesc2.setForeground(new Color(180, 180, 180));
        lblDesc2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Vantagens
        JLabel lblVantagem = new JLabel("+ " + info[7], SwingConstants.CENTER);
        lblVantagem.setFont(new Font("Arial", Font.BOLD, 10));
        lblVantagem.setForeground(new Color(100, 255, 100));
        lblVantagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDesvantagem = new JLabel("- " + info[8], SwingConstants.CENTER);
        lblDesvantagem.setFont(new Font("Arial", Font.BOLD, 10));
        lblDesvantagem.setForeground(new Color(255, 150, 150));
        lblDesvantagem.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Montar botão
        btn.add(lblNome);
        btn.add(Box.createRigidArea(new Dimension(0, 3)));
        btn.add(lblTipo);
        btn.add(Box.createRigidArea(new Dimension(0, 8)));
        btn.add(painelStats);
        btn.add(Box.createRigidArea(new Dimension(0, 8)));
        btn.add(separador);
        btn.add(Box.createRigidArea(new Dimension(0, 8)));
        btn.add(lblDesc1);
        btn.add(Box.createRigidArea(new Dimension(0, 3)));
        btn.add(lblDesc2);
        btn.add(Box.createRigidArea(new Dimension(0, 8)));
        btn.add(lblVantagem);
        btn.add(Box.createRigidArea(new Dimension(0, 2)));
        btn.add(lblDesvantagem);
        
        return btn;
    }
    
    private void selecionarPersonagem(int escolha) {
        try {
            switch (escolha) {
                case 0: jogador = new Luna("Luna"); break;
                case 1: jogador = new Clara("Clara"); break;
                case 2: jogador = new Bia("Bia"); break;
                case 3: jogador = new Sofia("Sofia"); break;
                case 4: jogador = new Yasmin("Yasmin"); break;
            }
            hpMaximo = jogador.getPontosVida();
            inicializarJogo();
            mostrarTelaJogo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao selecionar personagem: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void inicializarJogo() throws Exception {
        contexto = new ContextoJogo();
        mapa = new AcampamentoMisterioMapa();
        
        Inventario mochila = new Inventario();
        mochila.adicionarItemNaMochila(new PocaoDeCura("Pocao de Cura", 50, (byte)3));
        
        // Adicionar alguns artefatos iniciais para teste
        mochila.adicionarItemNaMochila(new LanternaEncantada());
        
        jogador.setInventario(mochila);
        
        mapa.entrarAtual(jogador, contexto);
        // Não adicionar local inicial - deixar que primeira exploração conte para quest
    }
    
    private void criarPainelJogo() {
        painelJogo = new JPanel(new BorderLayout());
        painelJogo.setBackground(new Color(30, 40, 55));
    }
    
    private void mostrarTelaInicio() {
        cardLayout.show(painelPrincipal, "INICIO");
    }
    
    private void mostrarSelecaoPersonagem() {
        cardLayout.show(painelPrincipal, "SELECAO");
    }
    
    private void mostrarTelaJogo() {
        // Limpar painel de jogo anterior
        painelJogo.removeAll();
        
        // Criar novo layout para o jogo
        JPanel painelSuperior = criarPainelStatus();
        JPanel painelCentral = criarPainelMapa();
        JPanel painelInferior = criarPainelAcoes();
        
        painelJogo.add(painelSuperior, BorderLayout.NORTH);
        painelJogo.add(painelCentral, BorderLayout.CENTER);
        painelJogo.add(painelInferior, BorderLayout.SOUTH);
        
        painelJogo.revalidate();
        painelJogo.repaint();
        
        cardLayout.show(painelPrincipal, "JOGO");
    }
    
    private JPanel criarPainelStatus() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout());
        painel.setBackground(new Color(20, 30, 45));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel superior com info básica
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelSuperior.setBackground(new Color(20, 30, 45));
        
        JLabel lblNome = new JLabel(jogador.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 18));
        lblNome.setForeground(new Color(255, 215, 0));
        
        JLabel lblNivel = new JLabel("  |  Nivel: " + nivel);
        lblNivel.setFont(new Font("Arial", Font.BOLD, 16));
        lblNivel.setForeground(Color.WHITE);
        
        JLabel lblATK = new JLabel("  |  ATK: " + jogador.getAtaque());
        lblATK.setFont(new Font("Arial", Font.PLAIN, 14));
        lblATK.setForeground(new Color(255, 150, 50));
        
        JLabel lblDEF = new JLabel("  |  DEF: " + jogador.getDefesa());
        lblDEF.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDEF.setForeground(new Color(100, 150, 255));
        
        JLabel lblLocal = new JLabel("  |  Local: " + mapa.getAtual().getNome());
        lblLocal.setFont(new Font("Arial", Font.PLAIN, 14));
        lblLocal.setForeground(new Color(150, 255, 150));
        
        painelSuperior.add(lblNome);
        painelSuperior.add(lblNivel);
        painelSuperior.add(lblATK);
        painelSuperior.add(lblDEF);
        painelSuperior.add(lblLocal);
        
        // Painel inferior com barras de progresso
        JPanel painelBarras = new JPanel(new GridLayout(2, 1, 5, 5));
        painelBarras.setBackground(new Color(20, 30, 45));
        painelBarras.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        // Barra de HP
        JPanel painelHP = new JPanel(new BorderLayout(10, 0));
        painelHP.setBackground(new Color(20, 30, 45));
        lblHPTexto = new JLabel("HP: " + jogador.getPontosVida() + "/" + hpMaximo);
        lblHPTexto.setFont(new Font("Arial", Font.BOLD, 13));
        lblHPTexto.setForeground(new Color(255, 100, 100));
        lblHPTexto.setPreferredSize(new Dimension(150, 20));
        
        barraHP = new JProgressBar(0, hpMaximo);
        barraHP.setValue(jogador.getPontosVida());
        barraHP.setStringPainted(false);
        barraHP.setForeground(new Color(220, 50, 50));
        barraHP.setBackground(new Color(60, 20, 20));
        barraHP.setBorderPainted(true);
        
        painelHP.add(lblHPTexto, BorderLayout.WEST);
        painelHP.add(barraHP, BorderLayout.CENTER);
        
        // Barra de XP
        JPanel painelXP = new JPanel(new BorderLayout(10, 0));
        painelXP.setBackground(new Color(20, 30, 45));
        lblXPTexto = new JLabel("XP: " + xpTotal + "/100");
        lblXPTexto.setFont(new Font("Arial", Font.BOLD, 13));
        lblXPTexto.setForeground(new Color(100, 200, 255));
        lblXPTexto.setPreferredSize(new Dimension(150, 20));
        
        barraXP = new JProgressBar(0, 100);
        barraXP.setValue(xpTotal);
        barraXP.setStringPainted(false);
        barraXP.setForeground(new Color(100, 180, 255));
        barraXP.setBackground(new Color(20, 40, 80));
        barraXP.setBorderPainted(true);
        
        painelXP.add(lblXPTexto, BorderLayout.WEST);
        painelXP.add(barraXP, BorderLayout.CENTER);
        
        painelBarras.add(painelHP);
        painelBarras.add(painelXP);
        
        painel.add(painelSuperior, BorderLayout.NORTH);
        painel.add(painelBarras, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelMapa() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(30, 40, 55));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Área de texto para descrição do local
        JTextArea txtDescricao = new JTextArea();
        txtDescricao.setText(obterDescricaoLocal());
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDescricao.setForeground(Color.WHITE);
        txtDescricao.setBackground(new Color(40, 50, 65));
        txtDescricao.setEditable(false);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 120, 150), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setBorder(null);
        
        painel.add(scrollDescricao, BorderLayout.CENTER);
        
        return painel;
    }
    
    private String obterDescricaoLocal() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════\n");
        sb.append("   ").append(mapa.getAtual().getNome().toUpperCase()).append("\n");
        sb.append("═══════════════════════════════════════════════\n\n");
        sb.append(mapa.getAtual().getDescricao()).append("\n\n");
        
        if (!mapa.saidas().isEmpty()) {
            sb.append("─────────────────────────────────────────────\n");
            sb.append("Locais adjacentes:\n");
            for (Local local : mapa.saidas()) {
                sb.append("  → ").append(local.getNome()).append("\n");
            }
        }
        
        return sb.toString();
    }
    
    private JPanel criarPainelAcoes() {
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(2, 3, 10, 10));
        painel.setBackground(new Color(25, 35, 50));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botões de ação com ícones e tooltips
        JButton btnExplorar = criarBotaoAcao("Explorar & Viajar (E)", new Color(50, 120, 180));
        btnExplorar.setToolTipText("Explore o local atual ou viaje para outro lugar");
        
        JButton btnInventario = criarBotaoAcao("Inventario (I)", new Color(120, 80, 150));
        btnInventario.setToolTipText("Gerencie seus itens e pocoes");
        
        JButton btnCombate = criarBotaoAcao("Combate (C)", new Color(180, 50, 50));
        btnCombate.setToolTipText("Enfrente inimigos em batalha");
        
        JButton btnQuests = criarBotaoAcao("Missoes (Q)", new Color(150, 100, 180));
        btnQuests.setToolTipText("Veja suas missões e conquistas");
        
        JButton btnStatus = criarBotaoAcao("Status (S)", new Color(80, 150, 80));
        btnStatus.setToolTipText("Veja suas estatísticas detalhadas");
        
        JButton btnSair = criarBotaoAcao("Sair (ESC)", new Color(100, 100, 100));
        btnSair.setToolTipText("Sair do jogo");
        
        // Atalhos de teclado
        btnExplorar.setMnemonic(KeyEvent.VK_E);
        btnInventario.setMnemonic(KeyEvent.VK_I);
        btnCombate.setMnemonic(KeyEvent.VK_C);
        btnQuests.setMnemonic(KeyEvent.VK_Q);
        btnStatus.setMnemonic(KeyEvent.VK_S);
        btnSair.setMnemonic(KeyEvent.VK_ESCAPE);
        
        btnExplorar.addActionListener(e -> acaoExplorarEViajar());
        btnInventario.addActionListener(e -> acaoInventario());
        btnCombate.addActionListener(e -> acaoCombate());
        btnQuests.addActionListener(e -> acaoQuests());
        btnStatus.addActionListener(e -> acaoStatus());
        btnSair.addActionListener(e -> acaoSair());
        
        painel.add(btnExplorar);
        painel.add(btnInventario);
        painel.add(btnCombate);
        painel.add(btnQuests);
        painel.add(btnStatus);
        painel.add(btnSair);
        
        return painel;
    }
    
    private JButton criarBotaoAcao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        return btn;
    }
    
    private void acaoExplorarEViajar() {
        String localAtual = mapa.getAtual().getNome();
        java.util.Set<Local> locaisAdjacentes = mapa.saidas();
        
        // Criar painel customizado com opções
        JPanel painelOpcoes = new JPanel();
        painelOpcoes.setLayout(new BoxLayout(painelOpcoes, BoxLayout.Y_AXIS));
        painelOpcoes.setBackground(new Color(30, 40, 55));
        painelOpcoes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título
        JLabel lblTitulo = new JLabel(localAtual);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelOpcoes.add(lblTitulo);
        painelOpcoes.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Opção 1: Explorar local atual
        JButton btnExplorarLocal = new JButton("Explorar este local");
        btnExplorarLocal.setFont(new Font("Arial", Font.BOLD, 14));
        btnExplorarLocal.setBackground(new Color(50, 120, 180));
        btnExplorarLocal.setForeground(Color.WHITE);
        btnExplorarLocal.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExplorarLocal.setMaximumSize(new Dimension(300, 40));
        btnExplorarLocal.setFocusPainted(false);
        
        painelOpcoes.add(btnExplorarLocal);
        painelOpcoes.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Opção 2: Viajar para outro local
        JButton btnViajar = new JButton("Viajar para outro local (" + locaisAdjacentes.size() + " disponiveis)");
        btnViajar.setFont(new Font("Arial", Font.BOLD, 14));
        btnViajar.setBackground(new Color(150, 100, 50));
        btnViajar.setForeground(Color.WHITE);
        btnViajar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnViajar.setMaximumSize(new Dimension(300, 40));
        btnViajar.setFocusPainted(false);
        btnViajar.setEnabled(!locaisAdjacentes.isEmpty());
        
        painelOpcoes.add(btnViajar);
        
        // Diálogo de escolha
        JDialog dialog = new JDialog(this, "O que deseja fazer?", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(painelOpcoes, BorderLayout.CENTER);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(30, 40, 55));
        
        btnExplorarLocal.addActionListener(e -> {
            dialog.dispose();
            explorarLocalAtual();
        });
        
        btnViajar.addActionListener(e -> {
            dialog.dispose();
            acaoMover();
        });
        
        dialog.setVisible(true);
    }
    
    private void explorarLocalAtual() {
        try {
            String localAtual = mapa.getAtual().getNome();
            boolean localNovo = !locaisVisitados.contains(localAtual);
            
            mapa.entrarAtual(jogador, contexto);
            locaisVisitados.add(localAtual);
            
            // Atualizar quest de exploração se for local novo
            if (localNovo) {
                sistemaQuests.atualizarProgresso("EXPLORACAO", 1);
                
                // Mostrar progresso da quest IMEDIATAMENTE
                Quest questExplorar = null;
                for (Quest q : sistemaQuests.getQuestsAtivas()) {
                    if (q.tipo == QuestTipo.EXPLORACAO) {
                        questExplorar = q;
                        break;
                    }
                }
                
                if (questExplorar != null) {
                    String msgQuest = "\n\nQuest Atualizada: " + questExplorar.nome + 
                                     "\n" + questExplorar.progresso + "/" + questExplorar.objetivo + 
                                     " locais visitados";
                    
                    if (questExplorar.concluida) {
                        msgQuest += "\n[COMPLETA] +" + questExplorar.recompensaXP + " XP";
                        ganharXP(questExplorar.recompensaXP);
                    }
                    
                    // Adicionar mensagem de quest aos diálogos de exploração
                    questProgressMessage = msgQuest;
                }
            }
            
            // Evento aleatório (40% de chance)
            Evento evento = eventosAleatorios.gerarEvento(40);
            
            if (evento != null) {
                processarEvento(evento, localAtual);
            } else {
                // Exploração normal
                int chance = random.nextInt(100);
                if (chance < 25) {
                    int xpGanho = 10 + random.nextInt(11); // 10-20 XP
                    ganharXP(xpGanho);
                    JOptionPane.showMessageDialog(this, 
                        "Você explorou " + localAtual + "!\n\n" +
                        "Você encontrou pistas valiosas!\n" +
                        "+" + xpGanho + " XP" + questProgressMessage,
                        "Descoberta!", JOptionPane.INFORMATION_MESSAGE);
                    questProgressMessage = "";
                } else if (chance < 50) {
                    JOptionPane.showMessageDialog(this, 
                        "Você explorou " + localAtual + "!\n\n" +
                        "O local está impregnado de magia antiga...\n" +
                        "Continue explorando para descobrir mais!" + questProgressMessage,
                        "Exploração", JOptionPane.INFORMATION_MESSAGE);
                    questProgressMessage = "";
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Você explorou " + localAtual + "!\n\n" +
                        "Nada de especial foi encontrado desta vez.\n" +
                        "Tente outro local!" + questProgressMessage,
                        "Exploração", JOptionPane.INFORMATION_MESSAGE);
                    questProgressMessage = "";
                }
            }
            
            atualizarTelaJogo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "ERRO ao explorar: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void processarEvento(Evento evento, String local) {
        if (evento.tipo == EventoTipo.ESCOLHA || evento.tipo == EventoTipo.ESCOLHA_RISCO) {
            int resposta = JOptionPane.showConfirmDialog(this,
                evento.titulo + "\n\n" + evento.descricao + "\n\n" +
                (evento.tipo == EventoTipo.ESCOLHA_RISCO ? "Pode ser arriscado!" : ""),
                "Evento Especial!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            ResultadoEvento resultado = eventosAleatorios.processarEscolha(evento, resposta == JOptionPane.YES_OPTION);
            
            if (resultado.xpGanho > 0) ganharXP(resultado.xpGanho);
            if (resultado.cura > 0) {
                try {
                    jogador.setPontosVida(Math.min(hpMaximo, jogador.getPontosVida() + resultado.cura));
                } catch (Exception e) {}
            }
            if (resultado.dano > 0) {
                try {
                    jogador.setPontosVida(jogador.getPontosVida() - resultado.dano);
                } catch (Exception e) {}
            }
            if (resultado.bonusAtaque > 0) {
                try {
                    jogador.setAtaque(jogador.getAtaque() + resultado.bonusAtaque);
                } catch (Exception e) {}
            }
            
            JOptionPane.showMessageDialog(this, resultado.mensagem,
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else if (evento.tipo != EventoTipo.ESCOLHA && evento.tipo != EventoTipo.ESCOLHA_RISCO) {
            // Eventos automáticos (não de escolha)
            String mensagem = evento.titulo + "\n\n" + evento.descricao + "\n\n";
            
            switch (evento.tipo) {
                case CURA -> {
                    try {
                        jogador.setPontosVida(Math.min(hpMaximo, jogador.getPontosVida() + evento.valor));
                        mensagem += "+" + evento.valor + " HP";
                    } catch (Exception e) {}
                }
                case XP_BONUS -> {
                    ganharXP(evento.valor);
                    mensagem += "+" + evento.valor + " XP";
                }
                case ITEM -> {
                    try {
                        jogador.getInventario().adicionarItemNaMochila(
                            new PocaoDeCura("Poção de Cura", 50, (byte)1));
                        mensagem += "+1 Pocao de Cura";
                    } catch (Exception e) {}
                }
                case DANO -> {
                    try {
                        jogador.setPontosVida(jogador.getPontosVida() - evento.valor);
                        mensagem += "-" + evento.valor + " HP";
                    } catch (Exception e) {}
                }
                default -> {
                    mensagem += "(Evento não processado)";
                }
            }
            
            JOptionPane.showMessageDialog(this, mensagem,
                "Evento!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void acaoInventario() {
        new InventarioDialog(this, jogador).setVisible(true);
        atualizarTelaJogo();
    }
    
    private void acaoQuests() {
        new QuestsDialog(this, sistemaQuests).setVisible(true);
        atualizarTelaJogo();
    }
    
    private void acaoStatus() {
        // Criar painel customizado para status
        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        painelStatus.setBackground(new Color(30, 40, 55));
        painelStatus.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titulo = new JLabel("STATUS DO PERSONAGEM");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(titulo);
        painelStatus.add(Box.createRigidArea(new Dimension(0, 15)));
        
        String[] info = {
            "⚔ Nome: " + jogador.getNome(),
            "Nivel: " + nivel + " (" + (xpTotal) + "/100 XP ate proximo nivel)",
            "❤ HP: " + jogador.getPontosVida() + "/" + hpMaximo + " (" + (jogador.getPontosVida() * 100 / hpMaximo) + "%)",
            "🗡 Ataque: " + jogador.getAtaque(),
            "🛡 Defesa: " + jogador.getDefesa(),
            "",
            "🗺 Locais Visitados: " + locaisVisitados.size(),
            "Artefatos Coletados: " + artefatosColetados.size()
        };
        
        for (String linha : info) {
            if (linha.isEmpty()) {
                painelStatus.add(Box.createRigidArea(new Dimension(0, 10)));
            } else {
                JLabel lbl = new JLabel(linha);
                lbl.setFont(new Font("Arial", Font.PLAIN, 14));
                lbl.setForeground(Color.WHITE);
                lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
                painelStatus.add(lbl);
                painelStatus.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        JOptionPane.showMessageDialog(this, painelStatus, 
            "Status Detalhado", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void acaoCombate() {
        new CombateDialog(this, jogador, mapa, contexto, xp -> {
            ganharXP(xp);
            inimigosDerrotados++;
            sistemaQuests.atualizarProgresso("COMBATE", 1);
            
            // Mostrar progresso da quest de combate
            for (Quest q : sistemaQuests.getQuestsAtivas()) {
                if (q.tipo == QuestTipo.COMBATE && !q.concluida) {
                    JOptionPane.showMessageDialog(this,
                        "Inimigo derrotado!\n\n" +
                        "Quest: " + q.nome + "\n" +
                        "Progresso: " + q.progresso + "/" + q.objetivo + " inimigos\n" +
                        (q.concluida ? "[COMPLETA] +" + q.recompensaXP + " XP" : ""),
                        "Quest Atualizada", JOptionPane.INFORMATION_MESSAGE);
                    
                    if (q.concluida) {
                        ganharXP(q.recompensaXP);
                    }
                    break;
                }
            }
        }).setVisible(true);
        atualizarTelaJogo();
        
        if (jogador.getPontosVida() <= 0) {
            gameOver();
        }
    }
    
    private void acaoMover() {
        new MoverDialog(this, mapa, jogador, contexto, locaisVisitados).setVisible(true);
        // Apenas mudar de local, NÃO marcar como visitado
        // O local só é marcado como visitado quando EXPLORAR
        atualizarTelaJogo();
    }
    
    private void acaoSair() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja realmente sair do jogo?",
            "Confirmar Saída",
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
    private void ganharXP(int xp) {
        xpTotal += xp;
        while (xpTotal >= 100) {
            xpTotal -= 100;
            nivel++;
            subirNivel();
        }
        // Atualizar barra de XP
        if (barraXP != null) {
            barraXP.setValue(xpTotal);
            lblXPTexto.setText("XP: " + xpTotal + "/100");
        }
    }
    
    private void subirNivel() {
        try {
            hpMaximo += 20;
            jogador.setPontosVida(jogador.getPontosVida() + 20);
            jogador.setAtaque(jogador.getAtaque() + 3);
            jogador.setDefesa(jogador.getDefesa() + 2);
            
            JOptionPane.showMessageDialog(this,
                "LEVEL UP! Agora você está no nível " + nivel + "!\n" +
                "+20 HP | +3 ATK | +2 DEF",
                "Level Up!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void atualizarTelaJogo() {
        // Atualizar barras de progresso
        if (barraHP != null && lblHPTexto != null) {
            barraHP.setValue(jogador.getPontosVida());
            barraHP.setMaximum(hpMaximo);
            lblHPTexto.setText("❤ HP: " + jogador.getPontosVida() + "/" + hpMaximo);
            
            // Mudar cor da barra baseado no HP
            int percentHP = (jogador.getPontosVida() * 100) / hpMaximo;
            if (percentHP > 60) {
                barraHP.setForeground(new Color(50, 200, 50));
            } else if (percentHP > 30) {
                barraHP.setForeground(new Color(255, 200, 50));
            } else {
                barraHP.setForeground(new Color(220, 50, 50));
            }
        }
        
        if (barraXP != null && lblXPTexto != null) {
            barraXP.setValue(xpTotal);
            lblXPTexto.setText("XP: " + xpTotal + "/100");
        }
        
        mostrarTelaJogo();
    }
    
    private void gameOver() {
        JOptionPane.showMessageDialog(this,
            "GAME OVER\n\n" +
            "Você foi derrotado!\n" +
            "XP Total: " + xpTotal + "\n" +
            "Nível: " + nivel,
            "Game Over", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
    
    // Método público para rastrear coleta de artefatos
    public void registrarArtefatoColetado(String nomeArtefato) {
        if (!artefatosColetados.contains(nomeArtefato)) {
            artefatosColetados.add(nomeArtefato);
            sistemaQuests.atualizarProgresso("COLETA", 1);
            
            JOptionPane.showMessageDialog(this,
                "Artefato Coletado!\n\n" +
                nomeArtefato + "\n\n" +
                "Quest de Colecionador atualizada!",
                "Artefato!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Getter para sistema de quests (para dialogs)
    public SistemaQuests getSistemaQuests() {
        return sistemaQuests;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RPGGameGUI game = new RPGGameGUI();
            game.setVisible(true);
        });
    }
}
