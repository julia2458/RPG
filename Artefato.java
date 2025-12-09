import java.util.*;

class ContextoJogo 
{
    boolean magiaProxima; 
    boolean mensagensInvisiveis;
    int nivelPerigoMagico; 
    int nivelDesequilibrioEmocional;
    int protecao;
    String simboloDesenhado;
    
    @Override
    public String toString()
    {
        return "--- Visualizar Magias --- " +
               "\n Magia Proxima: "+magiaProxima+
               "\n Mensagens Invisiveis: "+mensagensInvisiveis+
               "\n Nivel de Perigo Magico: "+nivelPerigoMagico+
               "\n Nivel de Desequilibrio Emocional do grupo: "+nivelDesequilibrioEmocional+
               "\n Simbolo: "+simboloDesenhado+
               "\n Turnos de protecao: "+protecao;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        
        ContextoJogo c = (ContextoJogo) obj;
        
        return magiaProxima == c.magiaProxima
            && mensagensInvisiveis == c.mensagensInvisiveis
            && nivelPerigoMagico == c.nivelPerigoMagico
            && nivelDesequilibrioEmocional == c.nivelDesequilibrioEmocional
            && protecao == c.protecao
            && Objects.equals(simboloDesenhado, c.simboloDesenhado);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(magiaProxima, mensagensInvisiveis, nivelPerigoMagico, nivelDesequilibrioEmocional, protecao, simboloDesenhado);
    }
}

public abstract class Artefato extends Item
{
    private boolean ativo;
    
    protected Artefato (String nome, String descricao, String efeito) throws Exception
    {
        super(nome, descricao, efeito, (byte)1);
        this.ativo = false;
    }
    
    public boolean estaAtivo()
    {
        return ativo;
    }
    
    public final void ativarArtefato(ContextoJogo contex) throws Exception
    {
        if(contex==null)
            throw new Exception ("Contexto nulo.");
        
        if(!estaAtivo())
            ativo = true;
        
        ligarAtivar(contex);
        
    }
    
    @Override
    public void adicionarQuantidade(byte valor) throws Exception
    {
        if (valor <= 0) return;
        throw new Exception("Artefatos não podem ter multiplas unidades empilhadas.");
    }
    
    public void desativarArtefato()
    {
        this.ativo = false;
    }
    
    protected abstract void ligarAtivar(ContextoJogo contex) throws Exception; 
}

class ColarDaVo extends Artefato {
    public ColarDaVo() throws Exception 
    {
        super("Colar da Avo", "Brilha quando a magia esta proxima.", "Detector de magia");
    }
    
    public ColarDaVo(ColarDaVo outro) throws Exception {
        super(outro.getNome(), outro.getDescricao(), outro.getEfeito());
    }
    
    @Override
    public ColarDaVo clone() {
        try {
            return new ColarDaVo(this);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void ligarAtivar(ContextoJogo contex) 
    {
        if (contex.magiaProxima) {
            System.out.println("[MAGIC] O Colar da Avo brilha intensamente! Ha magia por perto.");
        } else {
            System.out.println("O Colar da Avo permanece opaco. Nenhum traco de magia nas redondezas.");
        }
    }
}
    
class CadernoDeDesenhos extends Artefato 
{
   private static final Set<String> SIMBOLOS_VALIDOS =
    new HashSet<>(Arrays.asList("Lua", "Estrela", "Constelacao", "Riacho"));
    
    public CadernoDeDesenhos() throws Exception 
    {
        super("Caderno de Desenhos", "Revela pistas quando símbolos corretos são desenhados.", "Revelação de pistas");
    }
    
    public CadernoDeDesenhos(CadernoDeDesenhos outro) throws Exception {
        super(outro.getNome(), outro.getDescricao(), outro.getEfeito());
    }
    
    @Override
    public CadernoDeDesenhos clone() {
        try {
            return new CadernoDeDesenhos(this);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    @Override
    protected void ligarAtivar(ContextoJogo contex) 
    {
        if (contex.simboloDesenhado == null || contex.simboloDesenhado.trim().isEmpty()) 
        {
            System.out.println("O caderno esta em branco… Tente desenhar um simbolo.");
            return;
        }
    
    
        String simb = contex.simboloDesenhado.trim();
        
        if (SIMBOLOS_VALIDOS.contains(simb)) 
        {
            System.out.println("[MAGIC] O caderno reage ao simbolo '"+simb+"' e revela uma pista escondida no papel!");
        } 
        else 
        {
            System.out.println("Nada acontece com o simbolo '"+simb+"'. Talvez outro desenho...");
        }
    }
}
    
    
class LupaMagica extends Artefato 
{
    public LupaMagica() throws Exception
    {
        super("Lupa Magica", "Revela mensagens invisiveis.", "Revelacao de mensagens ocultas");
    }
    
    public LupaMagica(LupaMagica outro) throws Exception {
        super(outro.getNome(), outro.getDescricao(), outro.getEfeito());
    }
    
    @Override
    public LupaMagica clone() {
        try {
            return new LupaMagica(this);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    @Override
    protected void ligarAtivar(ContextoJogo contex) 
    {
        if (contex.mensagensInvisiveis) 
        {
            System.out.println("[MAGIC] A Lupa Magica revela inscricoes cintilantes sobre a superficie!");
        } 
        else {
            System.out.println("Voce examina o local com a Lupa, mas nao ha mensagens ocultas aqui.");
        }
    }
}
    
    
class BraceleteDeFolhas extends Artefato 
{
    public BraceleteDeFolhas() throws Exception 
    {
        super("Bracelete de Folhas", "Detecta emoções e desequilíbrios naturais.", "Empatia ambiental");
    }
    
    public BraceleteDeFolhas(BraceleteDeFolhas outro) throws Exception {
        super(outro.getNome(), outro.getDescricao(), outro.getEfeito());
    }
    
    @Override
    public BraceleteDeFolhas clone() {
        try {
            return new BraceleteDeFolhas(this);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    @Override
    protected void ligarAtivar(ContextoJogo contex) {
        int n = Math.max(0, Math.min(100, contex.nivelDesequilibrioEmocional));
        
        if (n == 0)     
        {
            System.out.println("[MAGIC] O Bracelete esta sereno - o ambiente esta em harmonia.");
        } else if (n <= 30) {
            System.out.println("O Bracelete vibra levemente: um leve desalinho nas emocoes do lago.");
        } else if (n <= 70) {
            System.out.println("O Bracelete pulsa: ha perturbacao consideravel nas criaturas proximas.");
        } else {
            System.out.println("[!] O Bracelete vibra forte! Grande desequilibrio - algo precisa ser restaurado com urgencia.");
        }
    }
}

class PulseiraDaSorte extends Artefato 
{
    public PulseiraDaSorte() throws Exception 
    {
        super("Pulseira da Sorte", "Protege temporariamente de perigos magicos.", "Protecao temporaria");
    }
    
    public PulseiraDaSorte(PulseiraDaSorte outro) throws Exception {
        super(outro.getNome(), outro.getDescricao(), outro.getEfeito());
    }
    
    @Override
    public PulseiraDaSorte clone() {
        try {
            return new PulseiraDaSorte(this);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void ligarAtivar(ContextoJogo contex) 
    {
        int bonus = Math.max(1, 2 + (contex.nivelPerigoMagico / 4));
        contex.protecao += bonus;
        System.out.println("[MAGIC] A Pulseira da Sorte cria um campo protetor por " + bonus + " turno(s)! (total: " + contex.protecao + ")");
    }
}
