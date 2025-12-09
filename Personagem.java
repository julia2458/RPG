import java.util.Objects;

public abstract class Personagem implements Cloneable
{
    protected String nome;
    protected int pontosVida;
    protected int ataque;
    protected int defesa;
    protected int nivel;
    protected Inventario inventario;
    
    public Personagem (String nome, int pontosVida, int ataque, int defesa, int nivel) throws Exception
    {
        if(nome == null || nome.trim().isEmpty())
            throw new Exception ("Nome do personagem inválido.");
        if(pontosVida <= 0)
            throw new Exception ("Pontos de Vida do Personagem Inválido.");
        if(ataque < 0 || defesa < 0)
            throw new Exception ("Ataque ou defesa do personagem inválido. O valor deve ser maior que zero.");
        if(nivel < 1 || nivel > 7)
            throw new Exception ("O nível atribuido ao personagem é inválido.");
        
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel; 
        this.inventario = new Inventario();
    }
    
    public void setNome (String nome) throws Exception
    {
        if(nome == null || nome.trim().isEmpty())  
            throw new Exception ("Nome do personagem inválido.");
        
        this.nome = nome;
    }
    
    public void setPontosVida(int pontosVida) throws Exception
    {
        
        this.pontosVida = Math.max(0, pontosVida);
    }
    
    public void setAtaque(int ataque) throws Exception
    {
        if( ataque < 0)
            throw new Exception (" Ataque do Personagem Inválido.");
        
        this.ataque = ataque;
    }
    
    public void setDefesa(int defesa) throws Exception
    {
        if(defesa < 0)
            throw new Exception (" Defesa do Personagem Inválido.");
        
        this.defesa = defesa;
    }
    
    public void setNivel(int nivel) throws Exception
    {
        if(nivel < 0 || nivel > 7)
            throw new Exception ("Nivel do Personagem Inválido.");
        
        this.nivel = nivel;
    }
    
    public void setInventario(Inventario novoInventario) throws Exception
    {
        if(novoInventario == null)
            throw new Exception ("Inventário do Personagem Inválido.");
            
        Inventario cloneInv = novoInventario.clone();
            
        if (cloneInv == null)
            throw new Exception("Falha ao clonar o novo Inventário.");
         
        this.inventario = cloneInv;
    }
    
    public String getNome()
    {
        return nome;
    }
    
    public int getPontosVida()
    {
        return pontosVida;
    }
    
    public int getAtaque()
    {
        return ataque;
    }
    
    public int getDefesa()
    {
        return defesa;
    }
    
    public int getNivel()
    {
        return nivel;
    }
    
    public Inventario getInventario()
    {
        return inventario;
    }
    
    public abstract void batalhar (Personagem inimigo) throws Exception;
    
    public void receberDano(int dano) throws Exception
    {
        if (dano<0)
            throw new Exception ("O dano do personagem não pode ser negativo.");
        
        this.pontosVida -= dano;
        
        if(this.pontosVida<0)
            this.pontosVida = 0;
    }
    
    //Métodos obrigatórios
    
    /*toString, equals, hashcode*/
    @Override
    public String toString()
    {
        return "---: "+this.nome+ "---\n"+
                "Tipo: "+this.getClass().getSimpleName()+"\n"+
                "Nivel: "+this.nivel+"\n"+
                "HP: "+this.pontosVida+"\n"+
                "Ataque: "+this.ataque+"\n"+
                "Defesa: "+this.defesa+"\n"+
                this.inventario.toString();
    }
    
    @Override
    public boolean equals (Object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        Personagem p = (Personagem)obj;
        
        return this.nome.equalsIgnoreCase(p.nome) &&
           this.pontosVida == p.pontosVida &&
           this.ataque == p.ataque &&
           this.defesa == p.defesa &&
           this.nivel == p.nivel &&
           this.inventario.equals(p.inventario);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(nome, pontosVida, ataque, defesa, nivel, inventario);
    }
    
    /*Construtor de cópia e clone*/
    protected Personagem(Personagem personagem) throws Exception 
    {
        if (personagem == null) 
            throw new Exception("Modelo ausente.");
        
        this.nome = personagem.nome;
        this.pontosVida = personagem.pontosVida;
        this.ataque = personagem.ataque;
        this.defesa = personagem.defesa;
        this.nivel = personagem.nivel;
        this.inventario = personagem.inventario != null ? personagem.inventario.clone() : new Inventario();
    }
    
    @Override
    public  abstract Personagem clone();
    
}
