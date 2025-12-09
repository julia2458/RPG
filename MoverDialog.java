import javax.swing.*;
import java.awt.*;
import java.util.*;

public class MoverDialog extends JDialog {
    private AcampamentoMisterioMapa mapa;
    private Personagem jogador;
    private ContextoJogo contexto;
    
    public MoverDialog(JFrame parent, AcampamentoMisterioMapa mapa, 
                       Personagem jogador, ContextoJogo contexto,
                       Set<String> locaisVisitados) {
        super(parent, "Mover para Outro Local", true);
        this.mapa = mapa;
        this.jogador = jogador;
        this.contexto = contexto;
        // locaisVisitados não é mais usado - só marca como visitado ao explorar
        
        configurarDialog();
        criarComponentes();
    }
    
    private void configurarDialog() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 40, 55));
    }
    
    private void criarComponentes() {
        // Título
        JLabel lblTitulo = new JLabel("Escolha seu destino", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(255, 215, 0));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        JLabel lblSubtitulo = new JLabel("Locais disponíveis para exploração", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(new Color(180, 180, 180));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JPanel painelTitulo = new JPanel();
        painelTitulo.setLayout(new BoxLayout(painelTitulo, BoxLayout.Y_AXIS));
        painelTitulo.setBackground(new Color(30, 40, 55));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelTitulo.add(lblTitulo);
        painelTitulo.add(lblSubtitulo);
        
        add(painelTitulo, BorderLayout.NORTH);
        
        // Painel central com locais
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBackground(new Color(30, 40, 55));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        java.util.Set<Local> locaisAdjacentes = mapa.saidas();
        
        if (locaisAdjacentes.isEmpty()) {
            JLabel lblSemLocais = new JLabel("Nao ha locais adjacentes disponiveis!");
            lblSemLocais.setFont(new Font("Arial", Font.BOLD, 16));
            lblSemLocais.setForeground(new Color(255, 150, 150));
            lblSemLocais.setAlignmentX(Component.CENTER_ALIGNMENT);
            painelCentral.add(Box.createVerticalGlue());
            painelCentral.add(lblSemLocais);
            painelCentral.add(Box.createVerticalGlue());
        } else {
            for (Local local : locaisAdjacentes) {
                JButton btnLocal = criarBotaoLocal(local);
                btnLocal.addActionListener(e -> moverPara(local));
                painelCentral.add(btnLocal);
                painelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        JScrollPane scroll = new JScrollPane(painelCentral);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
        
        // Botão cancelar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(new Color(30, 40, 55));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(100, 100, 100));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JButton criarBotaoLocal(Local local) {
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout(10, 10));
        btn.setBackground(new Color(40, 50, 70));
        btn.setForeground(Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(60, 70, 95));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(40, 50, 70));
            }
        });
        
        JPanel painelInfo = new JPanel();
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBackground(new Color(40, 50, 70));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        JLabel lblNome = new JLabel(local.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 18));
        lblNome.setForeground(new Color(255, 215, 0));
        
        String descricaoLimitada = limitarTexto(local.getDescricao(), 100);
        JLabel lblDescricao = new JLabel("<html><div style='width:450px'>" + descricaoLimitada + "</div></html>");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDescricao.setForeground(new Color(200, 200, 200));
        
        painelInfo.add(lblNome);
        painelInfo.add(Box.createRigidArea(new Dimension(0, 8)));
        painelInfo.add(lblDescricao);
        
        btn.add(painelInfo, BorderLayout.CENTER);
        
        return btn;
    }
    
    private String limitarTexto(String texto, int maxChars) {
        if (texto.length() > maxChars) {
            return texto.substring(0, maxChars) + "...";
        }
        return texto;
    }
    
    private void moverPara(Local local) {
        try {
            mapa.moverPara(local, jogador, contexto);
            // NÃO adicionar aos locais visitados aqui!
            // Só deve ser marcado como visitado quando EXPLORAR
            
            JOptionPane.showMessageDialog(this,
                "Voce se moveu para: " + local.getNome() + "\n\n" +
                "Explore o local para descobrir seus segredos!",
                "Movimento Realizado", JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "ERRO ao mover: " + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
