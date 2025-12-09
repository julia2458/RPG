public class TesteQuests {
    public static void main(String[] args) {
        System.out.println("=== TESTE DO SISTEMA DE QUESTS ===\n");
        
        SistemaQuests sistema = new SistemaQuests();
        
        System.out.println("Quests ativas inicialmente:");
        for (Quest q : sistema.getQuestsAtivas()) {
            System.out.println("- " + q.nome + " (" + q.tipo + "): " + q.progresso + "/" + q.objetivo);
        }
        
        System.out.println("\n--- Atualizando EXPLORACAO +1 ---");
        sistema.atualizarProgresso("EXPLORACAO", 1);
        
        System.out.println("\nQuests após 1 atualização:");
        for (Quest q : sistema.getQuestsAtivas()) {
            System.out.println("- " + q.nome + " (" + q.tipo + "): " + q.progresso + "/" + q.objetivo);
        }
        
        System.out.println("\n--- Atualizando EXPLORACAO +1 ---");
        sistema.atualizarProgresso("EXPLORACAO", 1);
        
        System.out.println("\nQuests após 2 atualizações:");
        for (Quest q : sistema.getQuestsAtivas()) {
            System.out.println("- " + q.nome + " (" + q.tipo + "): " + q.progresso + "/" + q.objetivo);
        }
        
        System.out.println("\n--- Atualizando EXPLORACAO +1 ---");
        sistema.atualizarProgresso("EXPLORACAO", 1);
        
        System.out.println("\nQuests ativas após 3 atualizações:");
        for (Quest q : sistema.getQuestsAtivas()) {
            System.out.println("- " + q.nome + " (" + q.tipo + "): " + q.progresso + "/" + q.objetivo);
        }
        
        System.out.println("\nQuests concluídas:");
        for (Quest q : sistema.getQuestsConcluidas()) {
            System.out.println("- " + q.nome + " (" + q.tipo + "): COMPLETA!");
        }
    }
}
