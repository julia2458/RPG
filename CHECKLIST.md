# 📋 Checklist de Requisitos - Trabalho de OO

## ✅ Requisitos Obrigatórios Implementados

### 1. Estrutura de Classes ✅

#### Classe Abstrata Personagem
- [x] Atributo `nome`
- [x] Atributo `pontosVida` (HP)
- [x] Atributo `ataque`
- [x] Atributo `defesa`
- [x] Atributo `nivel`
- [x] Atributo `Inventario inventario`
- [x] Método abstrato `batalhar(Personagem inimigo)`

**Arquivo:** `Personagem.java`

#### Subclasses de Personagem (adaptadas ao tema)
Em vez de Guerreiro/Mago/Arqueiro, criamos personagens do acampamento:

- [x] **Luna** - Curiosa e corajosa (HP: 180, ATK: 20, DEF: 30)
- [x] **Clara** - Inteligente e observadora (HP: 95, ATK: 15, DEF: 25)
- [x] **Bia** - Empática (HP: 110, ATK: 25, DEF: 20)
- [x] **Sofia** - Sensível e criativa (HP: 130, ATK: 28, DEF: 22)
- [x] **Yasmin** - Ágil e determinada (HP: 110, ATK: 30, DEF: 18)

**Arquivos:** `Luna.java`, `Clara.java`, `Bia.java`, `Sofia.java`, `Yasmin.java`

#### Classe Inimigo
- [x] Derivada de `Personagem`
- [x] Representa adversários do jogo

**Arquivo:** `Inimigo.java`

---

### 2. Sistema de Itens ✅

#### Classe Item
- [x] Atributo `nome`
- [x] Atributo `descricao`
- [x] Atributo `efeito`
- [x] Atributo `quantidade` (suporta múltiplas unidades)
- [x] Método `equals()` - verifica duplicatas
- [x] Método `compareTo()` - ordena itens por nome
- [x] Método `clone()` - copia independente
- [x] Construtor de cópia

**Arquivo:** `Item.java`

#### Subclasses de Item
- [x] **Artefato** (abstrata) - Base para itens mágicos especiais
  - ColarDaVo - Detector de magia
  - LupaMagica - Revela mensagens
  - BraceleteDeFolhas - Detecta emoções
  - PulseiraDaSorte - Proteção
  - CadernoDeDesenhos - Pistas
- [x] **PocaoDeCura** - Item consumível (similar a PocaoDeCura do exemplo)
- [x] **LanternaEncantada** - Item especial que revela passagens

**Arquivos:** `Artefato.java`, `PocaoDeCura.java`, `LanternaEncantada.java`

---

### 3. Sistema de Inventário ✅

#### Classe Inventario
- [x] Adiciona itens (se já existe, soma quantidade)
- [x] Remove itens (diminui quantidade)
- [x] Lista itens ordenados (usando compareTo)
- [x] Método `clone()` - para saque de inimigos
- [x] Construtor de cópia

**Arquivo:** `Inventario.java`

**Demonstração de uso:**
```java
// Em Jogo.java, método saquearInimigo()
Inventario loot = inimigo.getInventario();
for (Item item : loot.listarItensMochilaOrdenados()) {
    Item itemClonado = item.clone();
    jogador.getInventario().adicionarItemNaMochila(itemClonado);
}
```

---

### 4. Construtores ✅

#### Construtor Padrão
- [x] Todas as classes possuem construtor padrão

#### Construtor de Cópia
- [x] Personagem - permite criar "save points"
- [x] Item - copia itens
- [x] Inventario - clona inventário completo
- [x] Todas as subclasses

**Exemplo em `Personagem.java`:**
```java
protected Personagem(Personagem personagem) throws Exception {
    if (personagem == null) 
        throw new Exception("Modelo ausente.");
    
    this.nome = personagem.nome;
    this.pontosVida = personagem.pontosVida;
    // ... copia todos os atributos
    this.inventario = personagem.inventario != null ? 
                      personagem.inventario.clone() : new Inventario();
}
```

---

### 5. Sistema de Combate ✅

#### Rolagem de Dados
- [x] Cada turno rola um dado de 1-10
- [x] Dano = (Ataque + Dado) - Defesa
- [x] Dano mínimo de 1

**Implementação em `Personagem.java`:**
```java
Random rand = new Random();
int dadoBase = rand.nextInt(10)+1;
int ataqueTotal = this.ataque + dadoBase;
int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
```

#### Método batalhar()
- [x] Turnos alternados entre jogador e inimigo
- [x] Continua até HP <= 0
- [x] Exibe informações do combate

**Arquivo:** `Jogo.java` método `combate()`

#### Sistema de Fuga
- [x] Opção de fugir durante o combate
- [x] 50% de chance (dado >= 5)
- [x] Se falhar, inimigo ataca

**Implementação em `Jogo.java`:**
```java
private boolean tentarFugir() {
    int dado = random.nextInt(10) + 1;
    System.out.println("🎲 Você rolou um " + dado + " para tentar fugir...");
    return dado >= 5;
}
```

---

### 6. História e Navegação ✅

#### Classe Jogo
- [x] Loop principal do RPG
- [x] Menu de opções
- [x] Sistema de exploração

**Arquivo:** `Jogo.java`

#### Mecânicas Implementadas
- [x] **Explorar** - Encontra inimigos, itens ou eventos
- [x] **Usar itens** - Do inventário
- [x] **Fugir** - Com chance de falha
- [x] **Tomar decisões** - Afetam o contexto do jogo

**Eventos de Exploração:**
```java
private void explorar() throws Exception {
    int evento = random.nextInt(100);
    
    if (evento < 40) {
        encontrarInimigo();      // 40% - Combate
    } else if (evento < 60) {
        encontrarItem();         // 20% - Item
    } else if (evento < 80) {
        tomarDecisao();         // 20% - Decisão
    } else {
        // Nada acontece         // 20% - Vazio
    }
}
```

#### Sistema de Mapa
- [x] Navegação entre locais
- [x] 6 locais exploráveis
- [x] Conexões entre áreas

**Arquivo:** `Mapa.java`

---

### 7. Métodos Obrigatórios ✅

Implementados em **TODAS** as classes onde são aplicáveis:

#### toString()
- [x] Personagem e subclasses
- [x] Item e subclasses
- [x] Inventario
- [x] Local e subclasses
- [x] Mapa
- [x] contextoJogo

#### equals()
- [x] Personagem
- [x] Item (compara nome, descrição, efeito - sem quantidade)
- [x] Inventario
- [x] Local
- [x] Mapa
- [x] contextoJogo

#### hashCode()
- [x] Personagem
- [x] Item
- [x] Inventario
- [x] Local
- [x] Mapa
- [x] contextoJogo

#### clone()
- [x] Personagem (abstrato)
- [x] Todas as subclasses de Personagem
- [x] Item
- [x] Todas as subclasses de Item
- [x] Inventario

#### compareTo()
- [x] Item (ordena por nome)
- [x] Usado em `Inventario.listarItensMochilaOrdenados()`

---

## 🎯 Adaptações ao Tema

Conforme permitido nas instruções, adaptamos:

| Sugestão Original | Nossa Implementação | Justificativa |
|------------------|---------------------|---------------|
| Guerreiro/Mago/Arqueiro | Luna/Clara/Bia/Sofia/Yasmin | Personagens do tema do acampamento |
| PocaoDeCura | PocaoDeCura + LanternaEncantada + Artefatos | Itens variados conforme tema |
| Monstros genéricos | Sombras/Guardiões/Espíritos | Criaturas místicas do acampamento |

---

## 📁 Arquivos Principais

### Classes Obrigatórias
1. `Personagem.java` - Classe abstrata base ✅
2. `Luna.java`, `Clara.java`, `Bia.java`, `Sofia.java`, `Yasmin.java` - Personagens jogáveis ✅
3. `Inimigo.java` - Adversários ✅
4. `Item.java` - Classe base de itens ✅
5. `Inventario.java` - Sistema de inventário ✅

### Classes Complementares
6. `Artefato.java` - Itens mágicos especiais ✅
7. `PocaoDeCura.java` - Item consumível ✅
8. `LanternaEncantada.java` - Item especial ✅
9. `Mapa.java` - Sistema de navegação ✅
10. `Jogo.java` - Loop principal do RPG ✅

### Demonstração
11. `Main.java` - Demonstra todos os conceitos de OO ✅

---

## 🎮 Como Demonstrar

### Para o Professor - Demonstração Completa
```powershell
javac *.java
java Main
```

Isso executa uma bateria de 14 testes mostrando:
- ✅ Criação de personagens
- ✅ Construtor de cópia
- ✅ Clone
- ✅ Inventário com quantidades
- ✅ Sistema de combate
- ✅ Uso de itens consumíveis
- ✅ Saque de inimigos
- ✅ Navegação por mapa
- ✅ Artefatos mágicos
- ✅ equals, hashCode, toString, compareTo

### Para Jogar
```powershell
java Jogo
```

Isso inicia o RPG interativo completo.

---

## 🏆 Critérios de Avaliação

### Uso correto de OO (7 pts)
- [x] Herança (Personagem → Luna/Clara/etc, Item → Artefato/PocaoDeCura)
- [x] Polimorfismo (batalhar(), ligarAtivar())
- [x] Encapsulamento (atributos privados/protegidos, getters/setters)
- [x] Abstração (classes abstratas Personagem, Artefato, Local)
- [x] Interfaces (Cloneable, Comparable)
- [x] Construtor de cópia em todas as classes
- [x] Clone implementado corretamente
- [x] equals/hashCode seguindo regras

### Qualidade do código (1,5 pts)
- [x] Código bem organizado
- [x] Comentários explicativos
- [x] Nomes descritivos
- [x] Validações com throws Exception
- [x] Tratamento de erros

### Qualidade da história e navegação (1,5 pts)
- [x] História criativa (Acampamento Mistério)
- [x] 6 personagens únicos
- [x] 6 locais exploráveis
- [x] Eventos aleatórios
- [x] Decisões que importam
- [x] Artefatos com efeitos especiais
- [x] Sistema de combate dinâmico

---

## 📝 Observações Finais

- ✅ Todos os requisitos obrigatórios foram implementados
- ✅ Métodos obrigatórios presentes em todas as classes cabíveis
- ✅ Tema adaptado criativamente mantendo os conceitos de OO
- ✅ Código compilável e funcional
- ✅ Demonstração completa disponível
- ✅ Documentação (README.md e GUIA.md) incluída

**Total de classes:** 18+
**Total de arquivos .java:** 15
**Linhas de código:** ~1900+
