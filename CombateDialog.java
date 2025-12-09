import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.function.Consumer;

public class CombateDialog extends JDialog {
    private Personagem jogador;
    private AcampamentoMisterioMapa mapa;
    private ContextoJogo contexto;
    private Consumer<Integer> ganharXPCallback;
    private Inimigo inimigo;
    private Random random = new Random();
    private boolean combateAtivo = true;
    
    private JTextArea txtLog;
    private JLabel lblHPJogador;
    private JLabel lblHPInimigo;
    private JProgressBar barraHPJogador;
    private JProgressBar barraHPInimigo;
    private JButton btnAtacar;
    private JButton btnUsarPocao;
    private JButton btnFugir;
    
    public CombateDialog(JFrame parent, Personagem jogador, 
                        AcampamentoMisterioMapa mapa, ContextoJogo contexto,
                        Consumer<Integer> ganharXPCallback) {
        super(parent, "Combate", true);
        this.jogador = jogador;
        this.mapa = mapa;
        this.contexto = contexto;
        this.ganharXPCallback = ganharXPCallback;
        
        configurarDialog();
        gerarInimigo();
        criarComponentes();
        adicionarLog("Um inimigo selvagem apareceu: " + inimigo.getNome() + "!");
        adicionarLog("Prepare-se para o combate!\n");
    }
    
    private void configurarDialog() {
        setSize(700, 600);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 40));
    }
    
    private void gerarInimigo() {
        String[] nomesInimigos = {
            "Sombra Rastejante",
            "Lobo das Trevas",
            "Espírito Inquieto",
            "Guardião Corrompido",
            "Criatura da Névoa"
        };
        
        String nome = nomesInimigos[random.nextInt(nomesInimigos.length)];
        int hp = 40 + random.nextInt(41); // 40-80
        int ataque = 10 + random.nextInt(11); // 10-20
        int defesa = 5 + random.nextInt(11); // 5-15
        
        try {
            inimigo = new Inimigo(nome, hp, ataque, defesa, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void criarComponentes() {
        // Painel superior - Título
        JLabel lblTitulo = new JLabel("COMBATE!", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(255, 50, 50));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitulo, BorderLayout.NORTH);
        
        // Painel central
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.setBackground(new Color(30, 30, 40));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel de status dos combatentes
        JPanel painelStatus = new JPanel(new GridLayout(1, 2, 20, 0));
        painelStatus.setBackground(new Color(30, 30, 40));
        painelStatus.setPreferredSize(new Dimension(0, 150));
        
        painelStatus.add(criarPainelCombatente(true));
        painelStatus.add(criarPainelCombatente(false));
        
        // Área de log
        txtLog = new JTextArea();
        txtLog.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtLog.setForeground(Color.WHITE);
        txtLog.setBackground(new Color(20, 20, 30));
        txtLog.setEditable(false);
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        txtLog.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 120), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane scrollLog = new JScrollPane(txtLog);
        scrollLog.setBorder(null);
        scrollLog.setPreferredSize(new Dimension(0, 250));
        
        painelCentral.add(painelStatus, BorderLayout.NORTH);
        painelCentral.add(scrollLog, BorderLayout.CENTER);
        
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior - Botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(new Color(30, 30, 40));
        
        btnAtacar = criarBotao("Atacar", new Color(180, 50, 50));
        btnAtacar.setToolTipText("Atacar o inimigo com sua arma");
        
        btnUsarPocao = criarBotao("Usar Poção", new Color(50, 150, 50));
        btnUsarPocao.setToolTipText("Usar uma poção de cura");
        
        btnFugir = criarBotao("Fugir", new Color(100, 100, 100));
        btnFugir.setToolTipText("Tentar fugir do combate (50% de chance)");
        
        btnAtacar.addActionListener(e -> atacarInimigo());
        btnUsarPocao.addActionListener(e -> usarPocao());
        btnFugir.addActionListener(e -> fugir());
        
        painelBotoes.add(btnAtacar);
        painelBotoes.add(btnUsarPocao);
        painelBotoes.add(btnFugir);
        
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelCombatente(boolean isJogador) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(40, 45, 55));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(isJogador ? new Color(50, 150, 255) : new Color(255, 50, 50), 3),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        String nome = isJogador ? jogador.getNome() : inimigo.getNome();
        int hp = isJogador ? jogador.getPontosVida() : inimigo.getPontosVida();
        int hpMax = hp;
        int atk = isJogador ? jogador.getAtaque() : inimigo.getAtaque();
        int def = isJogador ? jogador.getDefesa() : inimigo.getDefesa();
        
        JLabel lblNome = new JLabel((isJogador ? "⚔ " : "👾 ") + nome);
        lblNome.setFont(new Font("Arial", Font.BOLD, 20));
        lblNome.setForeground(isJogador ? new Color(100, 200, 255) : new Color(255, 100, 100));
        lblNome.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblHP = new JLabel("HP: " + hp + "/" + hpMax);
        lblHP.setFont(new Font("Arial", Font.BOLD, 16));
        lblHP.setForeground(Color.WHITE);
        lblHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Barra de HP visual
        JProgressBar barraHP = new JProgressBar(0, hpMax);
        barraHP.setValue(hp);
        barraHP.setStringPainted(false);
        barraHP.setForeground(isJogador ? new Color(50, 200, 50) : new Color(220, 50, 50));
        barraHP.setBackground(new Color(60, 60, 70));
        barraHP.setPreferredSize(new Dimension(200, 15));
        barraHP.setMaximumSize(new Dimension(200, 15));
        barraHP.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (isJogador) {
            lblHPJogador = lblHP;
            barraHPJogador = barraHP;
        } else {
            lblHPInimigo = lblHP;
            barraHPInimigo = barraHP;
        }
        
        JLabel lblStats = new JLabel("Ataque: " + atk + "  |  Defesa: " + def);
        lblStats.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStats.setForeground(new Color(200, 200, 200));
        lblStats.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painel.add(lblNome);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(lblHP);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));
        painel.add(barraHP);
        painel.add(Box.createRigidArea(new Dimension(0, 10)));
        painel.add(lblStats);
        
        return painel;
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBackground(cor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(150, 45));
        return btn;
    }
    
    private void atacarInimigo() {
        try {
            // Sistema de dado de sorte
            int dadoJogador = 1 + random.nextInt(20); // D20
            int dadoInimigo = 1 + random.nextInt(20);
            
            boolean criticoJogador = dadoJogador >= 18;
            boolean criticoInimigo = dadoInimigo >= 18;
            boolean errouJogador = dadoJogador <= 3;
            
            // Jogador ataca
            if (errouJogador) {
                adicionarLog("⚔️ " + jogador.getNome() + " atacou mas ERROU!");
                adicionarLog("Dado: " + dadoJogador + " (Falha critica!)\n");
            } else {
                int danoBase = Math.max(1, jogador.getAtaque() - inimigo.getDefesa());
                int danoJogador = danoBase + random.nextInt(5);
                
                if (criticoJogador) {
                    danoJogador *= 2;
                    adicionarLog("CRÍTICO! " + jogador.getNome() + " acertou em cheio!");
                } else {
                    adicionarLog(jogador.getNome() + " atacou " + inimigo.getNome() + "!");
                }
                
                adicionarLog("Dado: " + dadoJogador + (criticoJogador ? " (Critico!)" : ""));
                adicionarLog("Dano causado: " + danoJogador + " HP\n");
                
                inimigo.setPontosVida(inimigo.getPontosVida() - danoJogador);
            }
            
            atualizarHP();
            
            if (inimigo.getPontosVida() <= 0) {
                vitoria();
                return;
            }
            
            // Inimigo ataca
            turnoInimigo();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void turnoInimigo() {
        try {
            Thread.sleep(500);
            
            int dadoInimigo = 1 + random.nextInt(20);
            boolean criticoInimigo = dadoInimigo >= 18;
            boolean errouInimigo = dadoInimigo <= 3;
            
            if (errouInimigo) {
                adicionarLog(inimigo.getNome() + " atacou mas você DESVIOU!");
                adicionarLog("Dado inimigo: " + dadoInimigo + " (Falha!)\n");
            } else {
                int danoBase = Math.max(1, inimigo.getAtaque() - jogador.getDefesa());
                int danoInimigo = danoBase + random.nextInt(5);
                
                if (criticoInimigo) {
                    danoInimigo *= 2;
                    adicionarLog("CRÍTICO INIMIGO! Você recebeu um golpe devastador!");
                } else {
                    adicionarLog(inimigo.getNome() + " contra-atacou!");
                }
                
                adicionarLog("Dado inimigo: " + dadoInimigo + (criticoInimigo ? " (Critico!)" : ""));
                adicionarLog("Voce recebeu: " + danoInimigo + " de dano\n");
                
                jogador.setPontosVida(jogador.getPontosVida() - danoInimigo);
            }
            
            atualizarHP();
            
            if (jogador.getPontosVida() <= 0) {
                derrota();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void usarPocao() {
        try {
            Inventario inv = jogador.getInventario();
            java.util.List<Item> itens = inv.listarItensMochilaOrdenados();
            
            PocaoDeCura pocao = null;
            for (Item item : itens) {
                if (item instanceof PocaoDeCura) {
                    pocao = (PocaoDeCura) item;
                    break;
                }
            }
            
            if (pocao == null || pocao.getQuantidade() <= 0) {
                adicionarLog("Você não tem poções disponíveis!\n");
                return;
            }
            
            int cura = pocao.getPontosDeVidaRestaurados();
            jogador.setPontosVida(jogador.getPontosVida() + cura);
            pocao.setQuantidade((byte)(pocao.getQuantidade() - 1));
            
            adicionarLog("Você usou uma Poção de Cura!");
            adicionarLog("HP restaurado: +" + cura + " HP\n");
            
            atualizarHP();
            
            // Inimigo ataca depois
            turnoInimigo();
            
        } catch (Exception ex) {
            adicionarLog("Erro ao usar poção: " + ex.getMessage() + "\n");
        }
    }
    
    private void fugir() {
        int chance = random.nextInt(100);
        
        if (chance < 50) {
            adicionarLog("Você conseguiu fugir do combate!\n");
            JOptionPane.showMessageDialog(this,
                "Você fugiu do combate com sucesso!",
                "Fuga", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            adicionarLog("Tentativa de fuga falhou!\n");
            turnoInimigo();
        }
    }
    
    private void vitoria() {
        int xpGanho = 30 + random.nextInt(21); // 30-50 XP
        
        adicionarLog("═══════════════════════════════");
        adicionarLog("VITÓRIA!");
        adicionarLog("Você derrotou " + inimigo.getNome() + "!");
        adicionarLog("XP ganho: " + xpGanho);
        adicionarLog("═══════════════════════════════\n");
        
        desabilitarBotoes();
        
        ganharXPCallback.accept(xpGanho);
        
        JOptionPane.showMessageDialog(this,
            "Vitória!\n\nVocê derrotou " + inimigo.getNome() + "!\nXP ganho: " + xpGanho,
            "Vitória!", JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
    
    private void derrota() {
        adicionarLog("═══════════════════════════════");
        adicionarLog("DERROTA!");
        adicionarLog("Você foi derrotado...");
        adicionarLog("═══════════════════════════════\n");
        
        desabilitarBotoes();
        
        JOptionPane.showMessageDialog(this,
            "Você foi derrotado!\nGame Over",
            "Derrota", JOptionPane.ERROR_MESSAGE);
        
        dispose();
    }
    
    private void atualizarHP() {
        int hpJogador = Math.max(0, jogador.getPontosVida());
        int hpInimigo = Math.max(0, inimigo.getPontosVida());
        
        lblHPJogador.setText("HP: " + hpJogador + "/" + barraHPJogador.getMaximum());
        lblHPInimigo.setText("HP: " + hpInimigo + "/" + barraHPInimigo.getMaximum());
        
        // Animação suave da barra de HP
        animarBarraHP(barraHPJogador, hpJogador);
        animarBarraHP(barraHPInimigo, hpInimigo);
    }
    
    private void animarBarraHP(JProgressBar barra, int valorFinal) {
        int valorAtual = barra.getValue();
        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            if (valorAtual < valorFinal) {
                barra.setValue(Math.min(valorAtual + 2, valorFinal));
                if (barra.getValue() >= valorFinal) ((Timer)e.getSource()).stop();
            } else if (valorAtual > valorFinal) {
                barra.setValue(Math.max(valorAtual - 2, valorFinal));
                if (barra.getValue() <= valorFinal) ((Timer)e.getSource()).stop();
            } else {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.start();
    }
    
    private void adicionarLog(String texto) {
        txtLog.append(texto + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
    
    private void desabilitarBotoes() {
        btnAtacar.setEnabled(false);
        btnUsarPocao.setEnabled(false);
        btnFugir.setEnabled(false);
    }
}
