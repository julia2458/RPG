import java.util.*;
public class Jogo {
    private Personagem jogador;
    private AcampamentoMisterioMapa mapa;
    private ContextoJogo contexto;
    private Scanner scanner;
    private Random random;
    private boolean jogoAtivo;
    public Jogo() throws Exception {
        this.contexto = new ContextoJogo();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.jogoAtivo = true;
    }
    public void iniciar() throws Exception {
        exibirIntroducao();
        escolherPersonagem();
        configurarMapa();
        adicionarItensIniciais();
        loopPrincipal();
        exibirEncerramento();
    }
    private void exibirIntroducao() {
        System.out.println("â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
        System.out.println("â•‘      ðŸ•ï¸  ACAMPAMENTO MISTÃ‰RIO: VERÃƒO ENCANTADO ðŸ•ï¸             â•‘");
        System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
        System.out.println("\nDurante o verÃ£o, cinco meninas participam de um acampamento");
        System.out.println("aparentemente comum. Tudo muda quando uma delas encontra um");
        System.out.println("artefato brilhante prÃ³ximo ao lago...\n");
        System.out.println("FenÃ´menos estranhos comeÃ§am a acontecer â€” luzes misteriosas,");
        System.out.println("vozes sussurrantes e criaturas encantadas surgem pedindo ajuda.\n");
    }
    private void escolherPersonagem() throws Exception {
        System.out.println("\n=== ESCOLHA SUA PERSONAGEM ===");
        System.out.println("1. Luna - Curiosa e corajosa (HP: 180, Ataque: 20, Defesa: 30)");
        System.out.println("2. Clara - Inteligente e observadora (HP: 95, Ataque: 15, Defesa: 25)");
        System.out.println("3. Bia - EmpÃ¡tica e ligada aos animais (HP: 110, Ataque: 25, Defesa: 20)");
        System.out.println("4. Sofia - SensÃ­vel e criativa (HP: 130, Ataque: 28, Defesa: 22)");
        System.out.println("5. Yasmin - Ãgil e determinada (HP: 110, Ataque: 30, Defesa: 18)");
        int escolha = lerOpcao(1, 5);
        switch (escolha) {
            case 1: jogador = new Luna("Luna"); break;
            case 2: jogador = new Clara("Clara"); break;
            case 3: jogador = new Bia("Bia"); break;
            case 4: jogador = new Sofia("Sofia"); break;
            case 5: jogador = new Yasmin("Yasmin"); break;
        }
        System.out.println("\nâœ¨ VocÃª escolheu jogar como " + jogador.getNome() + "!");
    }
    private void configurarMapa() throws Exception {
        mapa = new AcampamentoMisterioMapa();
        System.out.println("\nðŸ—ºï¸  O acampamento se estende Ã  sua frente...");
        mapa.entrarAtual(jogador, contexto);
    }
    private void adicionarItensIniciais() throws Exception {
        jogador.getInventario().adicionarItemNaMochila(new ColarDaVo());
        jogador.getInventario().adicionarItemNaMochila(new LupaMagica());
        jogador.getInventario().adicionarItemNaMochila(new BraceleteDeFolhas());
        jogador.getInventario().adicionarItemNaMochila(new PocaoDeCura("PoÃ§Ã£o de Cura Pequena", 30, (byte)3));
        jogador.getInventario().adicionarItemNaMochila(new LanternaEncantada());
    }
    private void loopPrincipal() throws Exception {
        while (jogoAtivo && jogador.getPontosVida() > 0) {
            exibirMenuPrincipal();
            int opcao = lerOpcao(1, 6);
            switch (opcao) {
                case 1: explorar(); break;
                case 2: moverPara(); break;
                case 3: verInventario(); break;
                case 4: usarItem(); break;
                case 5: verStatus(); break;
                case 6: sair(); break;
            }
            System.out.println("\n" + "â•".repeat(60));
        }
        if (jogador.getPontosVida() <= 0) {
            System.out.println("\nðŸ’€ VocÃª foi derrotada... O mistÃ©rio do acampamento permanece sem soluÃ§Ã£o.");
        }
    }
    private void exibirMenuPrincipal() {
        System.out.println("\nâ•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
        System.out.println("â•‘                    O QUE VOCÃŠ DESEJA FAZER?                    â•‘");
        System.out.println("â• â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•£");
        System.out.println("â•‘ 1. ðŸ” Explorar local atual                                     â•‘");
        System.out.println("â•‘ 2. ðŸš¶ Mover para outro local                                   â•‘");
        System.out.println("â•‘ 3. ðŸŽ’ Ver inventÃ¡rio                                           â•‘");
        System.out.println("â•‘ 4. âœ¨ Usar item                                                â•‘");
        System.out.println("â•‘ 5. ðŸ“Š Ver status                                               â•‘");
        System.out.println("â•‘ 6. ðŸšª Sair do jogo                                             â•‘");
        System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
    }
    private void explorar() throws Exception {
        System.out.println("\nðŸ” VocÃª explora " + mapa.getAtual().getNome() + "...");
        int evento = random.nextInt(100);
        if (evento < 40) {
            encontrarInimigo();
        } else if (evento < 60) {
            encontrarItem();
        } else if (evento < 80) {
            tomarDecisao();
        } else {
            System.out.println("VocÃª caminha cuidadosamente, mas nada de especial acontece.");
            System.out.println("O silÃªncio Ã© perturbador...");
        }
    }
    private void encontrarInimigo() throws Exception {
        String[] nomes = {"Sombra Perdida", "GuardiÃ£o Corrompido", "EspÃ­rito Inquieto", "Criatura das Trevas"};
        String nomeInimigo = nomes[random.nextInt(nomes.length)];
        int hpInimigo = 40 + random.nextInt(40);
        int ataqueInimigo = 10 + random.nextInt(15);
        int defesaInimigo = 5 + random.nextInt(10);
        Inimigo inimigo = new Inimigo(nomeInimigo, hpInimigo, ataqueInimigo, defesaInimigo, 1);
        if (random.nextBoolean()) {
            inimigo.getInventario().adicionarItemNaMochila(new PocaoDeCura("PoÃ§Ã£o de Cura Pequena", 30, (byte)1));
        }
        System.out.println("\nâš”ï¸  Um(a) " + nomeInimigo + " aparece!");
        System.out.println(inimigo);
        combate(inimigo);
    }
    private void combate(Inimigo inimigo) throws Exception {
        System.out.println("\nâš”ï¸ â•â•â•â•â•â•â•â•â•â•â•â•â•â•â• COMBATE INICIADO â•â•â•â•â•â•â•â•â•â•â•â•â•â•â• âš”ï¸");
        while (jogador.getPontosVida() > 0 && inimigo.getPontosVida() > 0) {
            System.out.println("\n--- Turno de Combate ---");
            System.out.println("Seu HP: " + jogador.getPontosVida() + " | Inimigo HP: " + inimigo.getPontosVida());
            System.out.println("\nO que fazer?");
            System.out.println("1. âš”ï¸  Atacar");
            System.out.println("2. ðŸŽ’ Usar item");
            System.out.println("3. ðŸƒ Tentar fugir");
            int acao = lerOpcao(1, 3);
            if (acao == 1) {
                jogador.batalhar(inimigo);
                if (inimigo.getPontosVida() <= 0) {
                    System.out.println("\nðŸŽ‰ VocÃª derrotou " + inimigo.getNome() + "!");
                    saquearInimigo(inimigo);
                    return;
                }
                System.out.println("\n--- Turno do Inimigo ---");
                inimigo.batalhar(jogador);
            } else if (acao == 2) {
                usarItemCombate();
                System.out.println("\n--- Turno do Inimigo ---");
                inimigo.batalhar(jogador);
            } else if (acao == 3) {
                if (tentarFugir()) {
                    System.out.println("âœ… VocÃª conseguiu fugir!");
                    return;
                } else {
                    System.out.println("âŒ VocÃª nÃ£o conseguiu fugir!");
                    System.out.println("\n--- Turno do Inimigo ---");
                    inimigo.batalhar(jogador);
                }
            }
        }
        if (jogador.getPontosVida() <= 0) {
            System.out.println("\nðŸ’€ VocÃª foi derrotada em combate...");
        }
    }
    private boolean tentarFugir() {
        int dado = random.nextInt(10) + 1;
        System.out.println("ðŸŽ² VocÃª rolou um " + dado + " para tentar fugir...");
        return dado >= 5;
    }
    private void saquearInimigo(Inimigo inimigo) throws Exception {
        System.out.println("\nðŸ’° Saqueando o inimigo derrotado...");
        Inventario loot = inimigo.getInventario();
        if (loot == null || loot.toString().contains("vazia")) {
            System.out.println("O inimigo nÃ£o possuÃ­a itens.");
            return;
        }
        try {
            for (Item item : loot.listarItensMochilaOrdenados()) {
                Item itemClonado = item.clone();
                jogador.getInventario().adicionarItemNaMochila(itemClonado);
            }
        } catch (Exception e) {
            System.out.println("NÃ£o havia itens para saquear.");
        }
    }
    private void encontrarItem() throws Exception {
        System.out.println("\nâœ¨ VocÃª encontra algo brilhando no chÃ£o!");
        int tipo = random.nextInt(3);
        switch (tipo) {
            case 0:
                Item pocao = new PocaoDeCura("PoÃ§Ã£o de Cura MÃ©dia", 50, (byte)1);
                jogador.getInventario().adicionarItemNaMochila(pocao);
                System.out.println("VocÃª encontrou: " + pocao.getNome());
                break;
            case 1:
                Item artefato = new PulseiraDaSorte();
                jogador.getInventario().adicionarItemNaMochila(artefato);
                System.out.println("VocÃª encontrou um artefato: " + artefato.getNome());
                break;
            case 2:
                Item caderno = new CadernoDeDesenhos();
                jogador.getInventario().adicionarItemNaMochila(caderno);
                System.out.println("VocÃª encontrou: " + caderno.getNome());
                break;
        }
    }
    private void tomarDecisao() {
        System.out.println("\nðŸ”® VocÃª se depara com uma encruzilhada misteriosa...");
        String[] decisoes = {
            "VocÃª ouve sussurros vindos de uma direÃ§Ã£o. Seguir os sussurros?",
            "HÃ¡ uma bolsa abandonada no chÃ£o. Pegar a bolsa?",
            "Uma porta antiga estÃ¡ entreaberta. Abrir a porta?",
            "SÃ­mbolos estranhos brilham em uma Ã¡rvore. Tocar os sÃ­mbolos?"
        };
        String decisao = decisoes[random.nextInt(decisoes.length)];
        System.out.println(decisao);
        System.out.println("1. Sim");
        System.out.println("2. NÃ£o");
        int escolha = lerOpcao(1, 2);
        if (escolha == 1) {
            int resultado = random.nextInt(2);
            if (resultado == 0) {
                System.out.println("âœ… Boa escolha! VocÃª sente uma energia positiva.");
                contexto.nivelDesequilibrioEmocional = Math.max(0, contexto.nivelDesequilibrioEmocional - 10);
            } else {
                System.out.println("âŒ Algo deu errado... VocÃª sente um arrepio.");
                contexto.nivelPerigoMagico += 2;
                contexto.nivelDesequilibrioEmocional += 5;
            }
        } else {
            System.out.println("VocÃª decide ser cautelosa e segue em frente.");
        }
    }
    private void moverPara() throws Exception {
        Set<Local> saidas = mapa.saidas();
        if (saidas.isEmpty()) {
            System.out.println("NÃ£o hÃ¡ saÃ­das deste local!");
            return;
        }
        System.out.println("\nðŸš¶ Para onde deseja ir?");
        List<Local> listaSaidas = new ArrayList<>(saidas);
        for (int i = 0; i < listaSaidas.size(); i++) {
            System.out.println((i + 1) + ". " + listaSaidas.get(i).getNome());
        }
        System.out.println((listaSaidas.size() + 1) + ". Cancelar");
        int escolha = lerOpcao(1, listaSaidas.size() + 1);
        if (escolha <= listaSaidas.size()) {
            Local destino = listaSaidas.get(escolha - 1);
            mapa.moverPara(destino, jogador, contexto);
            ativarArtefatosAutomaticamente();
        }
    }
    private void ativarArtefatosAutomaticamente() throws Exception {
        System.out.println("\n--- Seus artefatos reagem ao ambiente ---");
        try {
            for (Item item : jogador.getInventario().listarItensMochilaOrdenados()) {
                if (item instanceof Artefato) {
                    Artefato artefato = (Artefato) item;
                    artefato.ativarArtefato(contexto);
                }
            }
        } catch (Exception e) {
        }
    }
    private void verInventario() {
        System.out.println("\nðŸŽ’ â•â•â•â•â•â•â•â•â•â•â•â•â•â•â• INVENTÃRIO â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
        System.out.println(jogador.getInventario());
    }
    private void usarItem() throws Exception {
        try {
            List<Item> itens = jogador.getInventario().listarItensMochilaOrdenados();
            System.out.println("\nâœ¨ Qual item deseja usar?");
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + ". " + itens.get(i).getNome() + 
                                   " (x" + itens.get(i).getQuantidade() + ")");
            }
            System.out.println((itens.size() + 1) + ". Cancelar");
            int escolha = lerOpcao(1, itens.size() + 1);
            if (escolha <= itens.size()) {
                Item item = itens.get(escolha - 1);
                usarItemEspecifico(item);
            }
        } catch (Exception e) {
            System.out.println("Seu inventÃ¡rio estÃ¡ vazio!");
        }
    }
    private void usarItemCombate() throws Exception {
        try {
            List<Item> itens = jogador.getInventario().listarItensMochilaOrdenados();
            System.out.println("\nâœ¨ Qual item deseja usar?");
            for (int i = 0; i < itens.size(); i++) {
                System.out.println((i + 1) + ". " + itens.get(i).getNome() + 
                                   " (x" + itens.get(i).getQuantidade() + ")");
            }
            int escolha = lerOpcao(1, itens.size());
            Item item = itens.get(escolha - 1);
            usarItemEspecifico(item);
        } catch (Exception e) {
            System.out.println("VocÃª nÃ£o tem itens disponÃ­veis!");
        }
    }
    private void usarItemEspecifico(Item item) throws Exception {
        if (item instanceof PocaoDeCura) {
            PocaoDeCura pocao = (PocaoDeCura) item;
            pocao.usar(jogador);
            jogador.getInventario().removerItem(item.getNome(), 1);
        } else if (item instanceof Artefato) {
            Artefato artefato = (Artefato) item;
            artefato.ativarArtefato(contexto);
        } else if (item instanceof LanternaEncantada) {
            LanternaEncantada lanterna = (LanternaEncantada) item;
            lanterna.usar();
            contexto.mensagensInvisiveis = true;
            System.out.println("A lanterna revela passagens secretas!");
        } else {
            System.out.println("VocÃª usa " + item.getNome() + ".");
            item.usarItem();
        }
    }
    private void verStatus() {
        System.out.println("\nðŸ“Š â•â•â•â•â•â•â•â•â•â•â•â•â•â•â• STATUS â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
        System.out.println(jogador);
        System.out.println("\n--- Local Atual ---");
        System.out.println(mapa.getAtual().observar());
        System.out.println("\n" + contexto);
    }
    private void sair() {
        System.out.println("\nðŸ‘‹ Tem certeza que deseja sair?");
        System.out.println("1. Sim");
        System.out.println("2. NÃ£o");
        int escolha = lerOpcao(1, 2);
        if (escolha == 1) {
            jogoAtivo = false;
            System.out.println("\nSalvando sua jornada...");
        }
    }
    private void exibirEncerramento() {
        System.out.println("\nâ•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—");
        System.out.println("â•‘           Obrigada por jogar Acampamento MistÃ©rio!            â•‘");
        System.out.println("â•‘         O verÃ£o encantado aguarda seu retorno...  ðŸŒ™           â•‘");
        System.out.println("â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•");
    }
    private int lerOpcao(int min, int max) {
        while (true) {
            try {
                System.out.print("\nEscolha uma opÃ§Ã£o [" + min + "-" + max + "]: ");
                int opcao = Integer.parseInt(scanner.nextLine().trim());
                if (opcao >= min && opcao <= max) {
                    return opcao;
                } else {
                    System.out.println("âŒ OpÃ§Ã£o invÃ¡lida! Escolha entre " + min + " e " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("âŒ Digite um nÃºmero vÃ¡lido!");
            }
        }
    }
    public static void main(String[] args) {
        try {
            Jogo jogo = new Jogo();
            jogo.iniciar();
        } catch (Exception e) {
            System.out.println("âŒ ERRO: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
