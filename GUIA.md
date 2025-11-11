# 🎮 Guia Rápido - Como Jogar

## 🚀 Início Rápido

### 1. Compilar o projeto
```powershell
javac *.java
```

### 2. Executar
**Para jogar o RPG completo:**
```powershell
java Jogo
```

**Para ver demonstração técnica:**
```powershell
java Main
```

## 🎯 Comandos do Jogo

### Menu Principal
1. **🔍 Explorar** - Procure por itens, inimigos ou eventos
2. **🚶 Mover** - Vá para outro local do acampamento
3. **🎒 Inventário** - Veja seus itens
4. **✨ Usar Item** - Use poções ou ative artefatos
5. **📊 Status** - Veja HP, local e contexto mágico
6. **🚪 Sair** - Encerrar o jogo

### Durante Exploração
Você pode encontrar:
- **⚔️ Inimigos** - Prepare-se para o combate!
- **✨ Itens** - Adicione ao inventário
- **🔮 Decisões** - Escolhas que afetam a história
- **Nada** - Às vezes o silêncio é perturbador...

### Durante Combate
1. **⚔️ Atacar** - Role os dados e ataque
2. **🎒 Usar Item** - Use uma poção de cura
3. **🏃 Fugir** - Tente escapar (50% de chance)

## 🗺️ Locais do Acampamento

1. **Cabana Principal** - Ponto de partida e descanso
2. **Floresta Encantada** - Árvores sussurrantes e criaturas mágicas
3. **Caverna Mágica** - Artefatos antigos e enigmas
4. **Biblioteca Esquecida** - Segredos do acampamento
5. **Lago Encantado** - Reflexos misteriosos
6. **Ilha do Mistério** - Portal mágico (centro do lago)

## 👧 Personagens Disponíveis

1. **Luna** - HP: 180 | ATK: 20 | DEF: 30 - Equilibrada
2. **Clara** - HP: 95 | ATK: 15 | DEF: 25 - Frágil mas inteligente
3. **Bia** - HP: 110 | ATK: 25 | DEF: 20 - Boa atacante
4. **Sofia** - HP: 130 | ATK: 28 | DEF: 22 - Atacante forte
5. **Yasmin** - HP: 110 | ATK: 30 | DEF: 18 - Máximo ataque

## 🧪 Itens Importantes

### Artefatos Mágicos (não consumíveis)
- **Colar da Avó** - Brilha quando há magia por perto
- **Lupa Mágica** - Revela mensagens invisíveis
- **Bracelete de Folhas** - Detecta desequilíbrios emocionais
- **Pulseira da Sorte** - Concede proteção temporária
- **Caderno de Desenhos** - Revela pistas com símbolos

### Itens Consumíveis
- **Poção de Cura Pequena** - Restaura 30 HP
- **Poção de Cura Média** - Restaura 50 HP
- **Poção de Cura Grande** - Restaura 70 HP
- **Lanterna Encantada** - Revela passagens secretas

## 💡 Dicas Estratégicas

1. **Explore cuidadosamente** - Cada local tem eventos únicos
2. **Gerencie suas poções** - Use-as sabiamente no combate
3. **Ative artefatos** - Eles revelam informações importantes
4. **Escolhas importam** - Decisões afetam o contexto mágico
5. **Fuja quando necessário** - Às vezes é melhor viver para lutar outro dia
6. **Saque inimigos** - Sempre colete itens após a vitória

## ⚔️ Sistema de Combate

### Como funciona
- Cada ataque rola um dado de 1-10
- **Dano = (Ataque + Dado) - Defesa do Oponente**
- Mínimo de 1 de dano sempre

### Exemplo
```
Seu ataque: 20
Dado rolado: 7
Total: 27

Defesa do inimigo: 10
Dano causado: 27 - 10 = 17 HP
```

## 🎲 Mecânica de Fuga

- Quando você tenta fugir, rola um dado de 1-10
- Se o resultado for **5 ou maior**, você escapa!
- Caso contrário, o inimigo ataca você

## 📊 Contexto Mágico

O jogo rastreia várias variáveis mágicas:

- **magiaProxima** - Detectada pelo Colar da Avó
- **mensagensInvisiveis** - Reveladas pela Lupa
- **nivelPerigoMagico** - Aumenta em locais perigosos
- **nivelDesequilibrioEmocional** - Afeta criaturas
- **protecao** - Turnos de proteção mágica
- **simboloDesenhado** - Para o Caderno de Desenhos

## 🏆 Objetivos

- Explorar todos os locais do acampamento
- Desvendar os mistérios mágicos
- Coletar artefatos e desbloquear seus poderes
- Sobreviver aos perigos e criaturas
- Restaurar o equilíbrio mágico do acampamento

## ❓ FAQ

**P: Como salvo o jogo?**
R: Atualmente não há sistema de save, mas você pode usar construtores de cópia para criar "checkpoints" do personagem.

**P: Posso trocar de personagem no meio do jogo?**
R: Não, escolha bem no início!

**P: Os artefatos se esgotam?**
R: Não, artefatos mágicos têm usos ilimitados.

**P: Posso voltar a locais anteriores?**
R: Sim! O mapa permite navegação livre entre locais conectados.

**P: Como vejo o mapa?**
R: Use a opção "Ver status" para ver sua localização e saídas disponíveis.

---

Boa sorte explorando o Acampamento Mistério! 🏕️✨
