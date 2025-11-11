# 🚀 Instruções de Compilação e Execução

## ⚠️ IMPORTANTE - Leia Antes de Executar

Este projeto foi desenvolvido em **Java puro** (sem dependências externas).
Todos os arquivos devem estar na mesma pasta.

---

## 📦 Pré-requisitos

- Java Development Kit (JDK) 8 ou superior
- Terminal/PowerShell com acesso ao comando `javac` e `java`

### Verificar instalação do Java:
```powershell
java -version
javac -version
```

Se os comandos acima não funcionarem, você precisa instalar o JDK.

---

## 🔧 Compilação

### Opção 1 - Compilar todos os arquivos de uma vez:
```powershell
javac *.java
```

### Opção 2 - Compilar individualmente (se houver problemas):
```powershell
javac Item.java
javac Inventario.java
javac Personagem.java
javac Inimigo.java
javac Luna.java
javac Clara.java
javac Bia.java
javac Sofia.java
javac Yasmin.java
javac Artefato.java
javac PocaoDeCura.java
javac LanternaEncantada.java
javac Mapa.java
javac Jogo.java
javac Main.java
```

---

## ▶️ Execução

### Para DEMONSTRAR ao professor (recomendado):
```powershell
java Main
```

**Isso irá:**
- ✅ Executar 14 testes automatizados
- ✅ Demonstrar todos os conceitos de OO
- ✅ Mostrar uso de equals, hashCode, clone, compareTo
- ✅ Testar sistema de combate
- ✅ Testar inventário com quantidades
- ✅ Testar saque de inimigos
- ✅ Testar navegação no mapa
- ✅ Mostrar artefatos mágicos em ação

### Para JOGAR o RPG completo:
```powershell
java Jogo
```

**Isso irá:**
- 🎮 Iniciar o jogo interativo
- 👧 Permitir escolher personagem
- 🗺️ Explorar o acampamento
- ⚔️ Enfrentar inimigos
- 📦 Gerenciar inventário
- 🔮 Tomar decisões narrativas

---

## 🐛 Solução de Problemas

### Erro: "javac não é reconhecido"
**Solução:** Você precisa instalar o JDK e configurar a variável PATH.

### Erro: "Could not find or load main class"
**Solução:** 
1. Certifique-se de estar na pasta correta com `cd`
2. Compile novamente com `javac *.java`
3. Execute com `java Main` (sem .java)

### Erro: "package does not exist"
**Solução:** 
- Remova as linhas comentadas `//package ...` no início dos arquivos
- Todos os arquivos já estão sem package ativo

### Erro de compilação em arquivos específicos
**Solução:**
1. Delete todos os arquivos .class: `Remove-Item *.class`
2. Compile na ordem de dependências (Item → Inventario → Personagem → etc)

---

## 📊 Saída Esperada

### Ao executar `java Main`:

```
╔════════════════════════════════════════════════════════════════╗
║          DEMONSTRAÇÃO DO RPG - ACAMPAMENTO MISTÉRIO           ║
╚════════════════════════════════════════════════════════════════╝

▶ TESTE 1: Criando personagens jogáveis
────────────────────────────────────────────────────────────────
✅ Personagens criadas com sucesso!

---: Luna---
Tipo: Luna
Nivel: 1
HP: 180
Ataque: 20
Defesa: 30
Itens na Mochila: 

▶ TESTE 2: Testando construtor de cópia (save point)
────────────────────────────────────────────────────────────────
...
```

### Ao executar `java Jogo`:

```
╔════════════════════════════════════════════════════════════════╗
║      🏕️  ACAMPAMENTO MISTÉRIO: VERÃO ENCANTADO 🏕️             ║
╚════════════════════════════════════════════════════════════════╝

Durante o verão, cinco meninas participam de um acampamento
aparentemente comum. Tudo muda quando uma delas encontra um
artefato brilhante próximo ao lago...

=== ESCOLHA SUA PERSONAGEM ===
1. Luna - Curiosa e corajosa (HP: 180, Ataque: 20, Defesa: 30)
2. Clara - Inteligente e observadora (HP: 95, Ataque: 15, Defesa: 25)
...
```

---

## 📁 Estrutura de Arquivos Necessária

Certifique-se de que todos estes arquivos estão na mesma pasta:

```
RPG/
├── Personagem.java      ✅ Obrigatório
├── Luna.java            ✅ Obrigatório
├── Clara.java           ✅ Obrigatório
├── Bia.java             ✅ Obrigatório
├── Sofia.java           ✅ Obrigatório
├── Yasmin.java          ✅ Obrigatório
├── Inimigo.java         ✅ Obrigatório
├── Item.java            ✅ Obrigatório
├── Inventario.java      ✅ Obrigatório
├── Artefato.java        ✅ Obrigatório
├── PocaoDeCura.java     ✅ Obrigatório
├── LanternaEncantada.java ✅ Obrigatório
├── Mapa.java            ✅ Obrigatório
├── Jogo.java            ✅ Obrigatório
├── Main.java            ✅ Obrigatório
├── README.md            📄 Documentação
├── GUIA.md              📄 Guia de jogo
├── CHECKLIST.md         📄 Checklist de requisitos
└── INSTRUCOES.md        📄 Este arquivo
```

---

## ✅ Checklist Pré-Entrega

Antes de entregar/demonstrar, verifique:

- [ ] Todos os arquivos .java estão na mesma pasta
- [ ] Compilação bem-sucedida: `javac *.java`
- [ ] `java Main` executa sem erros
- [ ] `java Jogo` executa e é jogável
- [ ] Leu o CHECKLIST.md para garantir que todos os requisitos estão implementados

---

## 💡 Dicas para a Demonstração

1. **Comece com `java Main`** - Mostra todos os conceitos de OO
2. **Depois execute `java Jogo`** - Mostra o jogo funcionando
3. **Tenha o código-fonte aberto** - Para mostrar implementações específicas
4. **Destaque os pontos principais:**
   - Herança e polimorfismo
   - Construtores de cópia
   - Clone de inventário (saque)
   - equals/hashCode/compareTo
   - Sistema de combate com dados

---

## 📞 Ajuda

Se tiver problemas:
1. Leia as mensagens de erro com atenção
2. Verifique se todos os arquivos estão presentes
3. Tente compilar individualmente na ordem de dependências
4. Certifique-se de estar no diretório correto

---

**Boa sorte na demonstração! 🍀**
