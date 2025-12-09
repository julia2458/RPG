import java.util.*;
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Personagem jogador;
    private static AcampamentoMisterioMapa mapa;
    private static ContextoJogo contexto;
    private static boolean jogoAtivo = true;
    private static int hpMaximo;
    private static int xpTotal = 0;
    private static int nivel = 1;
    private static boolean bossVencido = false;
    private static Set<String> locaisVisitados = new HashSet<>();
    private static Set<String> artefatosColetados = new HashSet<>();
    public static void main(String[] args) {
        try {
            mostrarIntroducao();
            selecionarPersonagem();
            inicializarJogo();
            while (jogoAtivo && jogador.getPontosVida() > 0) {
                mostrarMenuPrincipal();
            }
            if (bossVencido) {
                mostrarFinalVitorioso();
            } else if (jogador.getPontosVida() <= 0) {
                System.out.println("\n[X] GAME OVER - Voce foi derrotado!");
                System.out.println("XP Total: " + xpTotal + " | Nivel: " + nivel);
            } else {
                System.out.println("\nObrigado por jogar!");
                System.out.println("XP Total: " + xpTotal + " | Nivel: " + nivel);
            }
        } catch (Exception e) {
            System.out.println("[ERRO]: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    private static void mostrarIntroducao() {
        System.out.println("================================================================");
        System.out.println("          ACAMPAMENTO MISTERIO - RPG INTERATIVO");
        System.out.println("================================================================");
        System.out.println("\nBem-vindo ao Acampamento Misterio!");
        System.out.println("Um lugar magico onde aventuras e enigmas aguardam...");
        System.out.println("\nUma forca misteriosa esta desequilibrando a harmonia do acampamento.");
        System.out.println("Voce e suas amigas devem explorar locais magicos, enfrentar criaturas");
        System.out.println("sombrias e descobrir os segredos que mantem este lugar em equilibrio.\n");
        pausar();
    }
    private static void selecionarPersonagem() throws Exception {
        System.out.println("===============================================================");
        System.out.println("           SELECAO DE PERSONAGEM");
        System.out.println("===============================================================\n");
        System.out.println("Escolha sua personagem:\n");
        System.out.println("1. Luna - A Corajosa (HP: 180 | ATK: 20 | DEF: 30)");
        System.out.println("   - Guerreira destemida, lidera com bravura e protecao\n");
        System.out.println("2. Clara - A Inteligente (HP: 95 | ATK: 35 | DEF: 10)");
        System.out.println("   - Estrategista brilhante, resolve enigmas complexos\n");
        System.out.println("3. Bia - A Equilibrada (HP: 110 | ATK: 25 | DEF: 20)");
        System.out.println("   - Versatil e adaptavel, equilibra forca e sabedoria\n");
        System.out.println("4. Sofia - A Defensora (HP: 130 | ATK: 15 | DEF: 35)");
        System.out.println("   - Tank resiliente, protege aliados com determinacao\n");
        System.out.println("5. Yasmin - A Agil (HP: 110 | ATK: 30 | DEF: 15)");
        System.out.println("   - Rapida e precisa, ataca antes que percebam\n");
        int escolha = lerOpcao(1, 5);
        switch (escolha) {
            case 1: jogador = new Luna("Luna"); break;
            case 2: jogador = new Clara("Clara"); break;
            case 3: jogador = new Bia("Bia"); break;
            case 4: jogador = new Sofia("Sofia"); break;
            case 5: jogador = new Yasmin("Yasmin"); break;
        }
        System.out.println("\n[OK] " + jogador.getNome() + " foi escolhida!");
        hpMaximo = jogador.getPontosVida();
        System.out.println(jogador);
        pausar();
    }
    private static void inicializarJogo() throws Exception {
        contexto = new ContextoJogo();
        mapa = new AcampamentoMisterioMapa();
        Inventario mochila = new Inventario();
        mochila.adicionarItemNaMochila(new PocaoDeCura("Pocao de Cura", 50, (byte)3));
        jogador.setInventario(mochila);
        System.out.println("\n[ITENS] Voce recebeu itens iniciais:");
        System.out.println("  * 3x Pocao de Cura");
        System.out.println("\n[DICA] Explore os locais para encontrar artefatos magicos!");
        System.out.println("[OBJETIVO] Derrote o Boss na Ilha do Misterio!");
        System.out.println("\n=== SISTEMA DE PROGRESSAO ===");
        System.out.println("[XP] Derrote inimigos para ganhar experiencia (XP)");
        System.out.println("[LEVEL UP] A cada 100 XP voce sobe de nivel!");
        System.out.println("[BONUS] +20 HP | +3 ATK | +2 DEF por nivel");
        System.out.println("=============================\n");
        mapa.entrarAtual(jogador, contexto);
        locaisVisitados.add(mapa.getAtual().getNome());
        pausar();
    }
    private static void mostrarMenuPrincipal() {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                    MENU PRINCIPAL");
        System.out.println("================================================================");
        int hpAtual = jogador.getPontosVida();
        double porcentagemHP = (hpAtual * 100.0) / hpMaximo;
        String barraHP = gerarBarraHP(porcentagemHP);
        System.out.println("\n[" + jogador.getNome() + "] " + barraHP + " " + hpAtual + "/" + hpMaximo + " HP");
        System.out.println("[NIVEL " + nivel + "] XP: " + xpTotal + "/" + (nivel * 100) + 
                         " | ATK: " + jogador.getAtaque() + " | DEF: " + jogador.getDefesa());
        System.out.println("[Local]: " + mapa.getAtual().getNome());
        System.out.println();
        System.out.println("O que voce deseja fazer?\n");
        System.out.println("1. [MAPA] Explorar e Mover");
        System.out.println("2. [BAG] Ver Inventario");
        System.out.println("3. [MAGIC] Usar Artefato");
        System.out.println("4. [FIGHT] Procurar Inimigos");
        System.out.println("5. [INFO] Ver Status Completo");
        System.out.println("6. [EXIT] Sair do Jogo\n");
        int opcao = lerOpcao(1, 6);
        try {
            switch (opcao) {
                case 1: menuExplorar(); break;
                case 2: menuInventario(); break;
                case 3: menuArtefatos(); break;
                case 4: menuCombate(); break;
                case 5: mostrarStatus(); break;
                case 6: jogoAtivo = false; break;
            }
        } catch (Exception e) {
            System.out.println("[ERRO]: " + e.getMessage());
            pausar();
        }
    }
    private static String gerarBarraHP(double porcentagem) {
        int barras = (int)(porcentagem / 10);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            if (i < barras) {
                barra.append("=");
            } else {
                barra.append("-");
            }
        }
        barra.append("]");
        return barra.toString();
    }
    private static void menuExplorar() throws Exception {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                      EXPLORACAO");
        System.out.println("================================================================\n");
        System.out.println(mapa.getAtual().observar());
        System.out.println();
        var saidas = mapa.saidas();
        if (saidas.isEmpty()) {
            System.out.println("[!] Nao ha saidas disponiveis!");
            pausar();
            return;
        }
        System.out.println("Para onde deseja ir?\n");
        List<Local> locais = new ArrayList<>(saidas);
        for (int i = 0; i < locais.size(); i++) {
            System.out.println((i + 1) + ". " + locais.get(i).getNome());
        }
        System.out.println((locais.size() + 1) + ". <- Voltar\n");
        int opcao = lerOpcao(1, locais.size() + 1);
        if (opcao <= locais.size()) {
            Local destino = locais.get(opcao - 1);
            System.out.println("\n[MOVE] Movendo para " + destino.getNome() + "...\n");
            mapa.moverPara(destino, jogador, contexto);
            if (!locaisVisitados.contains(destino.getNome())) {
                locaisVisitados.add(destino.getNome());
                verificarArtefatoNoLocal(destino.getNome());
            }
            pausar();
        }
    }
    private static void verificarArtefatoNoLocal(String nomeLocal) throws Exception {
        Item itemEncontrado = null;
        switch (nomeLocal) {
            case "Floresta Encantada":
                if (!artefatosColetados.contains("ColarDaVo")) {
                    itemEncontrado = new ColarDaVo();
                    artefatosColetados.add("ColarDaVo");
                }
                break;
            case "Biblioteca Esquecida":
                if (!artefatosColetados.contains("LupaMagica")) {
                    itemEncontrado = new LupaMagica();
                    artefatosColetados.add("LupaMagica");
                }
                break;
            case "Caverna MÃ¡gica":
                if (!artefatosColetados.contains("LanternaEncantada")) {
                    itemEncontrado = new LanternaEncantada();
                    artefatosColetados.add("LanternaEncantada");
                }
                break;
        }
        if (itemEncontrado != null) {
            System.out.println("\n[!!!] Voce encontrou um item magico!");
            System.out.println("[ITEM] " + itemEncontrado.getNome() + " - " + itemEncontrado.getDescricao());
            jogador.getInventario().adicionarItemNaMochila(itemEncontrado);
            System.out.println();
        }
    }
    private static void menuInventario() throws Exception {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                      INVENTARIO");
        System.out.println("================================================================\n");
        List<Item> itens = jogador.getInventario().listarItensMochilaOrdenados();
        if (itens.isEmpty()) {
            System.out.println("[BAG] Seu inventario esta vazio!\n");
            pausar();
            return;
        }
        System.out.println("Seus itens:\n");
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            System.out.println((i + 1) + ". " + item.getNome() + 
                             " (" + item.getEfeito() + ") x" + item.getQuantidade());
        }
        System.out.println((itens.size() + 1) + ". <- Voltar\n");
        int opcao = lerOpcao(1, itens.size() + 1);
        if (opcao <= itens.size()) {
            Item item = itens.get(opcao - 1);
            menuUsarItem(item);
        }
    }
    private static void menuUsarItem(Item item) throws Exception {
        System.out.println("\n---------------------------------------------------------------");
        System.out.println("[ITEM] " + item.getNome());
        System.out.println("---------------------------------------------------------------");
        System.out.println("Descricao: " + item.getDescricao());
        System.out.println("Efeito: " + item.getEfeito());
        System.out.println("Quantidade: " + item.getQuantidade());
        System.out.println();
        if (item instanceof PocaoDeCura) {
            System.out.println("Deseja usar esta pocao?");
            System.out.println("1. Sim");
            System.out.println("2. Nao\n");
            if (lerOpcao(1, 2) == 1) {
                ((PocaoDeCura) item).usar(jogador);
            }
        } else {
            System.out.println("[i] Este item nao pode ser usado diretamente.");
        }
        pausar();
    }
    private static void menuArtefatos() throws Exception {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                   ATIVAR ARTEFATOS");
        System.out.println("================================================================\n");
        List<Item> itens = jogador.getInventario().listarItensMochilaOrdenados();
        List<Artefato> artefatos = new ArrayList<>();
        for (Item item : itens) {
            if (item instanceof Artefato) {
                artefatos.add((Artefato) item);
            }
        }
        if (artefatos.isEmpty()) {
            System.out.println("[MAGIC] Voce nao possui artefatos magicos!\n");
            pausar();
            return;
        }
        System.out.println("Escolha um artefato para ativar:\n");
        for (int i = 0; i < artefatos.size(); i++) {
            Artefato art = artefatos.get(i);
            String status = art.estaAtivo() ? "[X] ATIVO" : "[ ] inativo";
            System.out.println((i + 1) + ". " + art.getNome() + " - " + art.getEfeito() + " " + status);
        }
        System.out.println((artefatos.size() + 1) + ". <- Voltar\n");
        int opcao = lerOpcao(1, artefatos.size() + 1);
        if (opcao <= artefatos.size()) {
            Artefato art = artefatos.get(opcao - 1);
            System.out.println("\n[MAGIC] Ativando " + art.getNome() + "...\n");
            art.ativarArtefato(contexto);
            pausar();
        }
    }
    private static void menuCombate() throws Exception {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                       COMBATE");
        System.out.println("================================================================\n");
        boolean isBoss = mapa.getAtual().getNome().equals("Ilha do MistÃ©rio") && !bossVencido;
        Inimigo inimigo = isBoss ? gerarBoss() : gerarInimigo();
        if (isBoss) {
            System.out.println("[!!!] O BOSS FINAL APARECEU!");
            System.out.println("[!!!] Guardiao da Ilha do Misterio!\n");
        } else {
            System.out.println("[!] Um inimigo apareceu!\n");
        }
        System.out.println(inimigo);
        System.out.println();
        int hpInimigoMax = inimigo.getPontosVida();
        while (jogador.getPontosVida() > 0 && inimigo.getPontosVida() > 0) {
            double porcentagemInimigo = (inimigo.getPontosVida() * 100.0) / hpInimigoMax;
            String barraInimigo = gerarBarraHP(porcentagemInimigo);
            System.out.println("---------------------------------------------------------------");
            System.out.println("[VOCE] HP: " + jogador.getPontosVida() + "/" + hpMaximo);
            System.out.println("[INIMIGO] " + barraInimigo + " " + inimigo.getPontosVida() + "/" + hpInimigoMax + " HP");
            System.out.println("---------------------------------------------------------------\n");
            System.out.println("1. [ATK] Atacar");
            System.out.println("2. [HEAL] Usar Pocao");
            System.out.println("3. [RUN] Fugir\n");
            int acao = lerOpcao(1, 3);
            if (acao == 1) {
                int hpAntes = inimigo.getPontosVida();
                System.out.println("\n[ATK] Voce ataca " + inimigo.getNome() + "!");
                jogador.batalhar(inimigo);
                int dano = hpAntes - inimigo.getPontosVida();
                System.out.println("[DANO] Causou " + dano + " de dano!");
                if (inimigo.getPontosVida() > 0) {
                    System.out.println();
                    hpAntes = jogador.getPontosVida();
                    inimigo.batalhar(jogador);
                    dano = hpAntes - jogador.getPontosVida();
                    System.out.println("[DANO] Voce recebeu " + dano + " de dano!");
                }
            } else if (acao == 2) {
                boolean usouPocao = usarPocaoEmCombate();
                if (usouPocao && inimigo.getPontosVida() > 0) {
                    System.out.println("\n[ENEMY] O inimigo aproveita e ataca!");
                    int hpAntes = jogador.getPontosVida();
                    inimigo.batalhar(jogador);
                    int dano = hpAntes - jogador.getPontosVida();
                    System.out.println("[DANO] Voce recebeu " + dano + " de dano!");
                }
            } else {
                if (isBoss) {
                    System.out.println("\n[!] Voce nao pode fugir do Boss Final!");
                } else {
                    System.out.println("\n[RUN] Voce fugiu da batalha!");
                    pausar();
                    return;
                }
            }
            System.out.println();
            if (inimigo.getPontosVida() > 0 && jogador.getPontosVida() > 0) {
                pausar();
            }
        }
        if (jogador.getPontosVida() <= 0) {
            return; 
        }
        System.out.println("[WIN] VITORIA! Voce derrotou " + inimigo.getNome() + "!\n");
        int xpGanho = isBoss ? 500 : (30 + (contexto.nivelPerigoMagico * 15));
        xpTotal += xpGanho;
        int xpRestante = (nivel * 100) - xpTotal;
        System.out.println("[XP] +{" + xpGanho + " XP} (Total: " + xpTotal + "/" + (nivel * 100) + ")");
        if (xpRestante > 0 && !isBoss) {
            System.out.println("[INFO] Faltam " + xpRestante + " XP para o proximo nivel!");
        }
        verificarLevelUp();
        if (!inimigo.getInventario().listarItensMochilaOrdenados().isEmpty()) {
            System.out.println("[LOOT] Coletando itens do inimigo...");
            for (Item item : inimigo.getInventario().listarItensMochilaOrdenados()) {
                jogador.getInventario().adicionarItemNaMochila(item.clone());
            }
        }
        if (isBoss) {
            bossVencido = true;
            System.out.println("\n[!!!] PARABENS! VOCE VENCEU O JOGO!");
            pausar();
            jogoAtivo = false;
            return;
        }
        pausar();
    }
    private static void verificarLevelUp() {
        int xpNecessario = nivel * 100;
        if (xpTotal >= xpNecessario) {
            nivel++;
            System.out.println("\n[LEVEL UP!] Voce subiu para o nivel " + nivel + "!");
            int bonusHP = 20;
            int bonusATK = 3;
            int bonusDEF = 2;
            try {
                hpMaximo += bonusHP;
                jogador.setPontosVida(jogador.getPontosVida() + bonusHP);
                jogador.setAtaque(jogador.getAtaque() + bonusATK);
                jogador.setDefesa(jogador.getDefesa() + bonusDEF);
                System.out.println("[BONUS] HP +" + bonusHP + " | ATK +" + bonusATK + " | DEF +" + bonusDEF);
                System.out.println();
            } catch (Exception e) {
                System.out.println("[ERRO] Falha ao aplicar bonus: " + e.getMessage());
            }
        }
    }
    private static Inimigo gerarBoss() throws Exception {
        Inimigo boss = new Inimigo("Guardiao das Sombras", 200, 35, 25, 1);
        Inventario loot = new Inventario();
        loot.adicionarItemNaMochila(new PocaoDeCura("Pocao Lendaria", 100, (byte)2));
        boss.setInventario(loot);
        return boss;
    }
    private static boolean usarPocaoEmCombate() throws Exception {
        List<Item> itens = jogador.getInventario().listarItensMochilaOrdenados();
        List<PocaoDeCura> pocoes = new ArrayList<>();
        for (Item item : itens) {
            if (item instanceof PocaoDeCura) {
                pocoes.add((PocaoDeCura) item);
            }
        }
        if (pocoes.isEmpty()) {
            System.out.println("\n[!] Voce nao tem pocoes!");
            pausar();
            return false;
        }
        System.out.println("\nEscolha uma pocao:");
        for (int i = 0; i < pocoes.size(); i++) {
            System.out.println((i + 1) + ". " + pocoes.get(i).getNome() + " x" + pocoes.get(i).getQuantidade());
        }
        int opcao = lerOpcao(1, pocoes.size());
        pocoes.get(opcao - 1).usar(jogador);
        return true;
    }
    private static Inimigo gerarInimigo() throws Exception {
        Random rand = new Random();
        int nivel = contexto.nivelPerigoMagico;
        String[] nomes = {"Sombra Perdida", "Espirito Inquieto", "Guardiao Corrompido", 
                         "Eco das Trevas", "Fragmento de Caos"};
        String nome = nomes[rand.nextInt(nomes.length)];
        int hp = 40 + (nivel * 15);
        int atk = 10 + (nivel * 3);
        int def = 5 + (nivel * 2);
        Inimigo inimigo = new Inimigo(nome, hp, atk, def, 1);
        Inventario loot = new Inventario();
        if (rand.nextBoolean()) {
            loot.adicionarItemNaMochila(new PocaoDeCura("Pocao Misteriosa", 30 + (nivel * 10), (byte)1));
        }
        inimigo.setInventario(loot);
        return inimigo;
    }
    private static void mostrarStatus() {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                    STATUS COMPLETO");
        System.out.println("================================================================\n");
        System.out.println("[NIVEL] " + nivel + " | [XP] " + xpTotal + "/" + (nivel * 100));
        System.out.println("[HP] " + jogador.getPontosVida() + "/" + hpMaximo);
        System.out.println();
        System.out.println(jogador);
        System.out.println("\n[PROGRESSO]");
        System.out.println("Locais visitados: " + locaisVisitados.size() + "/6");
        System.out.println("Artefatos coletados: " + artefatosColetados.size() + "/3");
        pausar();
    }
    private static void mostrarContexto() {
        limparTela();
        System.out.println("================================================================");
        System.out.println("                   CONTEXTO DO JOGO");
        System.out.println("================================================================\n");
        System.out.println(contexto);
        pausar();
    }
    private static void mostrarFinalVitorioso() {
        limparTela();
        System.out.println("\n================================================================");
        System.out.println("                    VITORIA FINAL!");
        System.out.println("================================================================\n");
        System.out.println("Parabens, " + jogador.getNome() + "!");
        System.out.println("Voce derrotou o Guardiao das Sombras e restaurou o equilibrio");
        System.out.println("do Acampamento Misterio!\n");
        System.out.println("ESTATISTICAS FINAIS:");
        System.out.println("- Nivel alcancado: " + nivel);
        System.out.println("- XP total: " + xpTotal);
        System.out.println("- Locais explorados: " + locaisVisitados.size() + "/6");
        System.out.println("- Artefatos coletados: " + artefatosColetados.size() + "/3");
        System.out.println("\n================================================================");
        System.out.println("          OBRIGADO POR JOGAR!");
        System.out.println("================================================================\n");
    }
    private static int lerOpcao(int min, int max) {
        while (true) {
            try {
                System.out.print("> Escolha uma opcao (" + min + "-" + max + "): ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); 
                if (opcao >= min && opcao <= max) {
                    return opcao;
                }
                System.out.println("[!] Opcao invalida! Digite um numero entre " + min + " e " + max + ".");
            } catch (Exception e) {
                scanner.nextLine(); 
                System.out.println("[!] Entrada invalida! Digite um numero.");
            }
        }
    }
    private static void pausar() {
        System.out.print("\n[ENTER] Pressione ENTER para continuar...");
        scanner.nextLine();
    }
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
