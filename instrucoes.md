Este trabalho deverá ser desenvolvido **EM DUPLA** e deverá ser entregue e **DEMONSTRADO** impreterivelmente na aula do dia **28/outubro/2025**.

Você deverá desenvolver um RPG de texto em Java utilizando conceitos de Orientação a Objetos. O jogo deverá permitir que personagens enfrentem inimigos em combates baseados em rolagem de dados, utilizem itens de inventário (com múltiplas unidades de cada item) e naveguem por uma história simples.

> **Nota de Flexibilidade:** As classes, atributos e métodos sugeridos abaixo são uma **base** para o projeto. Você **pode (e deve)** adaptar os nomes e estruturas conforme fizer sentido para o tema do seu jogo (ex: `NaveEspacial` em vez de `Guerreiro`, `KitMedico` em vez de `PocaoDeCura`, etc.). O importante é aplicar corretamente os conceitos de Orientação a Objetos.

## Estrutura esperada do trabalho:

### Classes de Personagem e Inimigo

Crie uma classe abstrata `Personagem` com atributos:
* `nome`
* `pontosVida` (HP do personagem)
* `ataque` (poder de ataque base)
* `Defesa`
* `nivel`
* `Inventario inventario`

Crie subclasses `Guerreiro`, `Mago` e `Arqueiro` (ou classes equivalentes ao tema do seu jogo).

Crie uma classe `Inimigo`, também derivada de `Personagem`, representando monstros ou adversários que o jogador enfrentará.

---

### Classe Item (quantidades)

A classe `Item` deve possuir os seguintes atributos:
* `nome`
* `descricao`
* `efeito` (ex.: cura, aumento de ataque, etc.)
* `quantidade` (número de unidades disponíveis daquele item).

Diferentemente da versão anterior, o inventário agora pode conter múltiplas unidades do mesmo item (ex.: 2 Poções de Cura, 3 Flechas Mágicas). Sempre que um item for usado, sua quantidade é decrementada.

* **`equals`**: deve ser usado para verificar se um item já existe no inventário, somando na quantidade em vez de criar duplicatas.
* **`compareTo`**: ordena os itens do inventário pelo nome ou poder.

---

### Classe Inventario

O inventário deve ser capaz de:

* adicionar um item (se já existir, aumenta a quantidade);
* remover um item (diminuindo a quantidade);
* listar todos os itens ordenados.

O inventário poderá ser clonado para simular o saque de inimigos (quando o jogador derrota um inimigo, parte dos itens dele pode ser copiada para o inventário do jogador).

* **`clone()`**: usado para criar cópias independentes dos itens no inventário dos inimigos.

---

### Construtores (padrão e cópia)

* Cada classe de personagem deve ter um construtor padrão e um construtor de cópia.
* O construtor de cópia pode ser usado, por exemplo, para criar um *save point* do personagem.

---

### Sistema de Combate (rolagem de dados)

* Crie um método `batalhar(Inimigo inimigo)`.
* A cada turno, ambos (jogador e inimigo) rolam um dado.
* O valor do dado é somado ao atributo `ataque` e deve passar a `defesa` do outro.
* O combate continua até que `pontosVida` <= 0 para algum dos lados.

---

### História e navegação

A classe `Jogo` deve conter o loop principal do RPG.
O jogador pode:
* explorar (explorar lugares, encontrar inimigos e lutar, ou se deparar com armadilhas),
* usar itens do inventário,
* fugir (com chance de falha, também rolando dados).
* Tomar decisões na estória (escolher um caminho, abrir uma porta, pegar uma bolsa no chão)

---

### Avaliação:

* Uso dos métodos e conceitos de orientação a objeto usados de forma correta (7 pts)
* Qualidade do código (1,5 pts)
* Qualidade e criatividade da estória e navegação pelo jogo (1,5 pts)

---

> **OBS:** QUANDO CABÍVEIS, NAS CLASSES ONDE FOREM CABÍVEIS, DE ACORDO COM AS REGRAS ESTUDADAS EM AULA, OS MÉTODOS `toString`, `equals`, `hashCode`, `clone`, construtor de cópia e `compareTo` DEVEM SER FEITOS!