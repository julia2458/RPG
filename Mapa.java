import java.util.*;

abstract class Local {
    private final String nome;
    private final String descricao;
    private final Set<Local> vizinhos = new LinkedHashSet<>();

    protected Local(String nome, String descricao) throws Exception {
        if (nome == null || nome.isBlank()) throw new Exception("nome de Local inválido");
        if (descricao == null) throw new Exception("descrição de Local inválida");
        this.nome = nome.trim();
        this.descricao = descricao.trim();
    }

    public String getNome(){ return nome; }
    public String getDescricao(){ return descricao; }

    public Set<Local> getVizinhos(){ return Collections.unmodifiableSet(vizinhos); }

    public void conectar(Local outro){
        if (outro == null || outro == this) return;
        this.vizinhos.add(outro);
        outro.vizinhos.add(this);
    }

    public abstract void entrar(Personagem jogador, ContextoJogo contex);

    public String observar(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n== ").append(nome).append(" ==\n").append(descricao).append("\n\nCaminhos: ");
        if (vizinhos.isEmpty()) sb.append("(sem saídas)");
        else {
            boolean first = true;
            for (Local l : vizinhos){
                if (!first) sb.append(", ");
                sb.append(l.getNome());
                first = false;
            }
        }
        return sb.toString();
    }

    @Override
    public String toString(){
        return "Local{" + nome + "}";
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Local outro = (Local) obj;
        return Objects.equals(this.nome, outro.nome);
    }

    @Override
    public int hashCode(){
        return Objects.hash(getClass(), nome);
    }
}

class CabanaPrincipal extends Local {
    public CabanaPrincipal() throws Exception {
        super("Cabana Principal", "Quartos, cozinha e central de avisos. Ponto de encontro e planejamento.");
    }

    @Override
    public void entrar(Personagem jogador, ContextoJogo contex) {
        System.out.println("[LOCAL] Voce chega a Cabana Principal. Um quadro de avisos range ao vento.");
        contex.magiaProxima = false;
        contex.mensagensInvisiveis = false;
        contex.nivelPerigoMagico = 0;
        contex.nivelDesequilibrioEmocional = Math.max(0, contex.nivelDesequilibrioEmocional - 10);
    }
}

class FlorestaEncantada extends Local {
    public FlorestaEncantada() throws Exception {
        super("Floresta Encantada", "Árvores sussurrantes; trilhas que mudam; fadas do musgo e um guaxinim falante.");
    }

    @Override
    public void entrar(Personagem jogador, ContextoJogo contex) {
        System.out.println("[LOCAL] As arvores sussurram seu nome e folhas desenham simbolos no chao...");
        contex.magiaProxima = true;
        contex.mensagensInvisiveis = false;
        contex.nivelPerigoMagico = 3;
        contex.nivelDesequilibrioEmocional += 5;
    }
}

class CavernaMagica extends Local {
    public CavernaMagica() throws Exception {
        super("Caverna Mágica", "Passagens escuras com enigmas de luz e sombras. Artefatos antigos descansam aqui.");
    }

    @Override
    public void entrar(Personagem jogador, ContextoJogo contex) {
        System.out.println("[LOCAL] A escuridao engole os passos. Reflexos bailam nas paredes umidas...");
        contex.magiaProxima = true;
        contex.mensagensInvisiveis = false;
        contex.nivelPerigoMagico = 5;
        contex.nivelDesequilibrioEmocional += 10;
    }
}

class BibliotecaEsquecida extends Local {
    public BibliotecaEsquecida() throws Exception {
        super("Biblioteca Esquecida", "Estantes antigas, pergaminhos e a história oculta do acampamento.");
    }

    @Override
    public void entrar(Personagem jogador, ContextoJogo contex) {
        System.out.println("[LOCAL] Poeira danca na luz. Lombadas sussurram sobre um feitico de equilibrio...");
        contex.magiaProxima = true;
        contex.mensagensInvisiveis = true;
        contex.nivelPerigoMagico = 2;
        contex.nivelDesequilibrioEmocional = Math.max(0, contex.nivelDesequilibrioEmocional - 5);
    }
}

class LagoEncantado extends Local {
    public LagoEncantado() throws Exception {
        super("Lago Encantado", "Reflexos misteriosos e criaturas perturbadas. A Ilha do Mistério repousa no centro.");
    }

    @Override
    public void entrar(Personagem jogador, ContextoJogo contex) {
        System.out.println("[LOCAL] Ondas suaves revelam rostos tristes de criaturas do lago...");
        contex.magiaProxima = true;
        contex.mensagensInvisiveis = false;
        contex.nivelPerigoMagico = 4;
        contex.nivelDesequilibrioEmocional = Math.min(100, contex.nivelDesequilibrioEmocional + 25);
    }
}

class IlhaDoMisterio extends Local {
    public IlhaDoMisterio() throws Exception {
        super("Ilha do Mistério", "No coração do lago, o portal mágico. Decisões aqui ecoam além do mundo real.");
    }

    @Override
    public void entrar(Personagem jogador, ContextoJogo contex) {
        System.out.println("[LOCAL] A neblina abre caminho ate um circulo de pedras. O ar vibra como um canto antigo...");
        contex.magiaProxima = true;
        contex.mensagensInvisiveis = true;
        contex.nivelPerigoMagico = 6;
        contex.nivelDesequilibrioEmocional = Math.min(100, contex.nivelDesequilibrioEmocional + 15);
    }
}

//MAPA
class Mapa {
    private final Set<Local> locais = new LinkedHashSet<>();
    private Local localAtual;

    public void adicionarLocal(Local l) throws Exception {
        if (l == null) throw new Exception("Local nulo");
        locais.add(l);
        if (localAtual == null) localAtual = l;
    }

    public void conectar(Local a, Local b) { if (a != null && b != null) a.conectar(b); }

    public Local getAtual(){ return localAtual; }

    public Set<Local> getLocais(){ return Collections.unmodifiableSet(locais); }

    public Set<Local> saidas(){ return getAtual() == null ? Set.of() : getAtual().getVizinhos(); }

    public void entrarAtual(Personagem jogador, ContextoJogo contex) throws Exception {
        if (localAtual == null) throw new Exception("Mapa sem local atual.");
        System.out.println(localAtual.observar());
        localAtual.entrar(jogador, contex);
    }

    public void moverPara(Local destino, Personagem jogador, ContextoJogo contex) throws Exception {
        if (localAtual == null) throw new Exception("Mapa sem local atual.");
        if (destino == null) throw new Exception("Destino nulo.");
        if (!localAtual.getVizinhos().contains(destino))
            throw new Exception("Não há caminho direto de '" + localAtual.getNome() + "' para '" + destino.getNome() + "'.");
        localAtual = destino;
        System.out.println(destino.observar());
        destino.entrar(jogador, contex);
    }

    public Local buscarPorNome(String nome){
        if (nome == null) return null;
        for (Local l : locais) if (l.getNome().equalsIgnoreCase(nome)) return l;
        return null;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Mapa{locais=[");
        boolean first = true;
        for (Local l : locais){
            if (!first) sb.append(", ");
            sb.append(l.getNome());
            first = false;
        }
        sb.append("] atual=").append(localAtual != null ? localAtual.getNome() : "-").append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mapa outro = (Mapa) obj;
        return Objects.equals(this.locais, outro.locais) && Objects.equals(this.localAtual, outro.localAtual);
    }

    @Override
    public int hashCode(){
        return Objects.hash(locais, localAtual);
    }
}

class AcampamentoMisterioMapa extends Mapa {
    private final CabanaPrincipal cabana;
    private final FlorestaEncantada floresta;
    private final CavernaMagica caverna;
    private final BibliotecaEsquecida biblioteca;
    private final LagoEncantado lago;
    private final IlhaDoMisterio ilha;

    public AcampamentoMisterioMapa() throws Exception {
        super();
        cabana = new CabanaPrincipal();
        floresta = new FlorestaEncantada();
        caverna = new CavernaMagica();
        biblioteca = new BibliotecaEsquecida();
        lago = new LagoEncantado();
        ilha = new IlhaDoMisterio();

        adicionarLocal(cabana);
        adicionarLocal(floresta);
        adicionarLocal(caverna);
        adicionarLocal(biblioteca);
        adicionarLocal(lago);
        adicionarLocal(ilha);

        conectar(cabana, floresta);
        conectar(cabana, lago);
        conectar(floresta, caverna);
        conectar(caverna, biblioteca);
        conectar(biblioteca, lago);
        conectar(lago, ilha);
    }

    public CabanaPrincipal cabana(){ return cabana; }
    public FlorestaEncantada floresta(){ return floresta; }
    public CavernaMagica caverna(){ return caverna; }
    public BibliotecaEsquecida biblioteca(){ return biblioteca; }
    public LagoEncantado lago(){ return lago; }
    public IlhaDoMisterio ilha(){ return ilha; }
}
