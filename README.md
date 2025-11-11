# 🏕️ Acampamento Mistério: Verão Encantado

RPG de texto desenvolvido em Java para o trabalho de Orientação a Objetos.

## 📋 Sobre o Projeto

Este é um RPG narrativo ambientado em um acampamento de férias mágico, onde um grupo de meninas descobre segredos antigos e uma conexão com um mundo mágico escondido na floresta.

O projeto foi desenvolvido seguindo os princípios de Orientação a Objetos, com ênfase em:
- Herança e polimorfismo
- Encapsulamento
- Construtores de cópia e clone
- Implementação de interfaces (Cloneable, Comparable)
- Uso adequado de equals, hashCode e toString

## 🎮 Como Executar

### Compilar todos os arquivos:
```powershell
javac *.java
```

### Opção 1 - Jogar o RPG interativo:
```powershell
java Jogo
```

### Opção 2 - Ver demonstração de todos os recursos:
```powershell
java Main
```

## 🏗️ Estrutura do Projeto

### Classes de Personagem
- **Personagem** (abstrata) - Classe base para todos os personagens
- **Luna** - Curiosa e corajosa
- **Clara** - Inteligente e observadora
- **Bia** - Empática e ligada aos animais
- **Sofia** - Sensível e criativa
- **Yasmin** - Ágil e determinada
- **Inimigo** - Adversários do jogo

### Sistema de Itens
- **Item** (classe base) - Implementa Cloneable e Comparable
- **Artefato** (abstrata) - Base para artefatos mágicos
  - ColarDaVo - Detector de magia
  - LupaMagica - Revela mensagens invisíveis
  - BraceleteDeFolhas - Detecta desequilíbrios emocionais
  - PulseiraDaSorte - Proteção temporária
  - CadernoDeDesenhos - Revela pistas
- **PocaoDeCura** - Item consumível que restaura HP
- **LanternaEncantada** - Revela passagens secretas

### Sistema de Inventário
- **Inventario** - Gerencia múltiplas unidades de itens
  - Adiciona itens (soma quantidades de duplicatas)
  - Remove itens (decrementa quantidade)
  - Lista itens ordenados
  - Clone para saque de inimigos

### Sistema de Mapa
- **Local** (abstrata) - Classe base para locais
  - CabanaPrincipal
  - FlorestaEncantada
  - CavernaMagica
  - BibliotecaEsquecida
  - LagoEncantado
  - IlhaDoMisterio
- **Mapa** - Gerencia navegação entre locais
- **AcampamentoMisterioMapa** - Mapa específico do jogo

### Sistema de Jogo
- **contextoJogo** - Armazena estado mágico do mundo
- **Jogo** - Loop principal do RPG com todas as mecânicas

## ✨ Funcionalidades Implementadas

### ✅ Requisitos do Professor
- [x] Classe abstrata Personagem com atributos obrigatórios
- [x] Subclasses de personagens (6 tipos diferentes)
- [x] Classe Inimigo derivada de Personagem
- [x] Sistema de itens com quantidade
- [x] Inventário que suporta múltiplas unidades
- [x] Construtor padrão e de cópia em todas as classes
- [x] Sistema de combate com rolagem de dados
- [x] Método batalhar com turnos alternados
- [x] Sistema de fuga (com chance de falha)
- [x] Exploração com eventos aleatórios
- [x] Decisões que afetam a história
- [x] Uso de itens no combate e exploração
- [x] Clone de inventário para saque de inimigos
- [x] Métodos obrigatórios implementados:
  - toString
  - equals
  - hashCode
  - clone
  - compareTo (Item)

### 🎯 Mecânicas de Jogo
1. **Exploração** - Navegue pelo acampamento e descubra segredos
2. **Combate** - Enfrente criaturas com sistema baseado em dados
3. **Inventário** - Colete e gerencie itens com quantidades
4. **Decisões** - Escolhas que afetam o contexto mágico
5. **Artefatos** - Itens mágicos que reagem ao ambiente
6. **Saque** - Obtenha itens de inimigos derrotados
7. **Fuga** - Tente escapar de combates perigosos

## 🎲 Sistema de Combate

O combate funciona em turnos:
1. **Ataque**: Rolagem de dado (1-10) + Ataque base - Defesa do oponente
2. **Usar Item**: Poções de cura restauram HP
3. **Fugir**: 50% de chance (dado ≥ 5)

O combate continua até:
- O inimigo ser derrotado (você ganha e pode saquear)
- Você ser derrotada (game over)
- Conseguir fugir

## 📦 Estrutura de Arquivos

```
RPG/
├── README.md                    # Este arquivo
├── instrucoes.md               # Instruções do professor
├── tema.md                     # Tema do jogo
│
├── Personagem.java             # Classe abstrata base
├── Luna.java                   # Personagem jogável
├── Clara.java                  # Personagem jogável
├── Bia.java                    # Personagem jogável
├── Sofia.java                  # Personagem jogável
├── Yasmin.java                 # Personagem jogável
├── Inimigo.java                # Classe de inimigos
│
├── Item.java                   # Classe base de itens
├── Artefato.java               # Classe abstrata + subclasses
├── PocaoDeCura.java           # Item consumível
├── LanternaEncantada.java     # Item especial
├── Inventario.java             # Sistema de inventário
│
├── Mapa.java                   # Sistema de mapa e locais
│
├── Jogo.java                   # Loop principal do RPG
└── Main.java                   # Demonstração de recursos
```

## 🎨 Conceitos de OO Aplicados

### Herança
- Todas as personagens herdam de `Personagem`
- Artefatos mágicos herdam de `Item`
- Locais herdam de `Local`

### Polimorfismo
- Método abstrato `batalhar()` implementado diferentemente em cada classe
- Método abstrato `ligarAtivar()` em cada artefato

### Encapsulamento
- Atributos privados/protegidos
- Acesso via getters/setters com validação
- Validações throws Exception

### Clonagem
- `Inventario.clone()` para saque de inimigos
- `Item.clone()` para copiar itens
- Construtor de cópia em todas as classes

### Comparable
- `Item.compareTo()` para ordenação alfabética
- Inventário lista itens ordenados

### Equals e HashCode
- Comparação semântica de objetos
- Uso adequado em todas as classes

## 👥 Autores

Desenvolvido por: [Seu Nome Aqui]

## 📅 Data de Entrega

28 de outubro de 2025

## 📝 Notas

- O jogo possui 5 personagens jogáveis + Inimigos
- O jogo salva o estado do personagem usando construtor de cópia
- Artefatos reagem automaticamente ao entrar em novos locais
- Decisões afetam o `contextoJogo` (níveis de magia, perigo, etc.)
- Sistema de quantidade evita duplicatas no inventário
