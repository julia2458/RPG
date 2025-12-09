import java.util.*;

public class EventosAleatorios {
    private Random random;
    private List<Evento> eventosDisponiveis;
    
    public EventosAleatorios() {
        random = new Random();
        eventosDisponiveis = new ArrayList<>();
        carregarEventos();
    }
    
    private void carregarEventos() {
        // Eventos positivos
        eventosDisponiveis.add(new Evento(
            "Fonte Magica",
            "Você encontrou uma fonte mágica! Suas energias foram restauradas.",
            EventoTipo.CURA,
            30
        ));
        
        eventosDisponiveis.add(new Evento(
            "Baú do Tesouro",
            "Um baú antigo contém uma poção de cura!",
            EventoTipo.ITEM,
            1
        ));
        
        eventosDisponiveis.add(new Evento(
            "Sabedoria Ancestral",
            "Você decifrou runas antigas e ganhou experiência!",
            EventoTipo.XP_BONUS,
            25
        ));
        
        // Eventos neutros/escolha
        eventosDisponiveis.add(new Evento(
            "Coruja Sábia",
            "Uma coruja oferece conselhos... você escuta?",
            EventoTipo.ESCOLHA,
            0
        ));
        
        eventosDisponiveis.add(new Evento(
            "Cristal Pulsante",
            "Um cristal emite uma luz estranha. Você toca nele?",
            EventoTipo.ESCOLHA_RISCO,
            0
        ));
        
        // Eventos negativos (raros)
        eventosDisponiveis.add(new Evento(
            "Armadilha!",
            "Você caiu em uma armadilha antiga!",
            EventoTipo.DANO,
            15
        ));
    }
    
    public Evento gerarEvento(int chancePercent) {
        if (random.nextInt(100) < chancePercent) {
            return eventosDisponiveis.get(random.nextInt(eventosDisponiveis.size()));
        }
        return null;
    }
    
    public ResultadoEvento processarEscolha(Evento evento, boolean escolhaPositiva) {
        ResultadoEvento resultado = new ResultadoEvento();
        
        if (evento.tipo == EventoTipo.ESCOLHA) {
            if (escolhaPositiva) {
                resultado.mensagem = "A coruja compartilha um segredo: +15 XP!";
                resultado.xpGanho = 15;
            } else {
                resultado.mensagem = "Você segue seu caminho...";
            }
        } else if (evento.tipo == EventoTipo.ESCOLHA_RISCO) {
            if (escolhaPositiva) {
                if (random.nextBoolean()) {
                    resultado.mensagem = "O cristal te concede poder! +2 ATK!";
                    resultado.bonusAtaque = 2;
                } else {
                    resultado.mensagem = "O cristal drena sua energia! -20 HP!";
                    resultado.dano = 20;
                }
            } else {
                resultado.mensagem = "Você decide não arriscar.";
            }
        }
        
        return resultado;
    }
}

class Evento {
    String titulo;
    String descricao;
    EventoTipo tipo;
    int valor;
    
    public Evento(String titulo, String descricao, EventoTipo tipo, int valor) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
    }
}

enum EventoTipo {
    CURA, ITEM, XP_BONUS, ESCOLHA, ESCOLHA_RISCO, DANO
}

class ResultadoEvento {
    String mensagem = "";
    int xpGanho = 0;
    int cura = 0;
    int dano = 0;
    int bonusAtaque = 0;
    int bonusDefesa = 0;
    boolean itemGanho = false;
}
