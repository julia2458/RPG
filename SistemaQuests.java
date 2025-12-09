import java.util.*;

public class SistemaQuests {
    private List<Quest> questsAtivas;
    private List<Quest> questsConcluidas;
    private List<String> conquistas;
    
    public SistemaQuests() {
        questsAtivas = new ArrayList<>();
        questsConcluidas = new ArrayList<>();
        conquistas = new ArrayList<>();
        inicializarQuests();
    }
    
    private void inicializarQuests() {
        // Quest principal
        questsAtivas.add(new Quest(
            "O Mistério do Acampamento",
            "Descubra a fonte do desequilíbrio mágico",
            QuestTipo.PRINCIPAL,
            300,
            "Visite todos os locais místicos e colete os 3 artefatos sagrados"
        ));
        
        // Quests secundárias
        questsAtivas.add(new Quest(
            "Explorador Iniciante",
            "Visite 3 locais diferentes",
            QuestTipo.EXPLORACAO,
            50,
            "Explore o acampamento para descobrir seus segredos"
        ));
        
        questsAtivas.add(new Quest(
            "Caçador de Sombras",
            "Derrote 5 criaturas",
            QuestTipo.COMBATE,
            100,
            "As sombras estão tomando conta do lugar"
        ));
        
        questsAtivas.add(new Quest(
            "Colecionador",
            "Colete 2 artefatos mágicos",
            QuestTipo.COLETA,
            75,
            "Artefatos antigos guardam grande poder"
        ));
    }
    
    public void atualizarProgresso(String tipo, int quantidade) {
        for (Quest quest : questsAtivas) {
            if (quest.tipo.toString().equalsIgnoreCase(tipo)) {
                quest.progresso += quantidade;
                if (quest.progresso >= quest.objetivo && !quest.concluida) {
                    completarQuest(quest);
                }
            }
        }
    }
    
    private void completarQuest(Quest quest) {
        quest.concluida = true;
        questsConcluidas.add(quest);
        
        // Adicionar conquista
        String conquista = "[CONQUISTA] " + quest.nome;
        if (!conquistas.contains(conquista)) {
            conquistas.add(conquista);
        }
    }
    
    public List<Quest> getQuestsAtivas() {
        return questsAtivas.stream()
                .filter(q -> !q.concluida)
                .toList();
    }
    
    public List<Quest> getQuestsConcluidas() {
        return questsConcluidas;
    }
    
    public List<String> getConquistas() {
        return conquistas;
    }
    
    public int getTotalXPDisponivel() {
        return questsConcluidas.stream()
                .mapToInt(q -> q.recompensaXP)
                .sum();
    }
}

class Quest {
    String nome;
    String descricao;
    QuestTipo tipo;
    int objetivo;
    int progresso;
    int recompensaXP;
    String dica;
    boolean concluida;
    
    public Quest(String nome, String descricao, QuestTipo tipo, int recompensaXP, String dica) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.recompensaXP = recompensaXP;
        this.dica = dica;
        this.concluida = false;
        this.progresso = 0;
        
        // Definir objetivo baseado no tipo
        switch (tipo) {
            case EXPLORACAO -> this.objetivo = 3;
            case COMBATE -> this.objetivo = 5;
            case COLETA -> this.objetivo = 2;
            case PRINCIPAL -> this.objetivo = 3;
        }
    }
    
    public int getProgresso() {
        return Math.min(100, (progresso * 100) / objetivo);
    }
    
    @Override
    public String toString() {
        String status = concluida ? "[COMPLETA]" : progresso + "/" + objetivo;
        return nome + " [" + status + "] - " + recompensaXP + " XP";
    }
}

enum QuestTipo {
    PRINCIPAL, EXPLORACAO, COMBATE, COLETA
}
