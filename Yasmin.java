import java.util.Random;

public class Yasmin extends Personagem

{
    public Yasmin (String nome) throws Exception
    {
        super(nome, 110, 30, 15, 1);
    }
    
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        
        int ataqueTotal = this.ataque + dadoBase;
        
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        
        alvo.receberDano(danoCausado);
        
        System.out.println("\n A Yasmin defende o grupo e ataca com determinação. \n Seu ataque causou "+danoCausado+
                           " de dano no inimigo "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse inimigo.");
    }
    
    public Yasmin(Yasmin outra) throws Exception
    {
        super(outra);
    }
    
    @Override
    public Yasmin clone()
    {
        Yasmin retorno = null;
        try { 
            retorno = new Yasmin(this); 
            
        } catch (Exception e) {}
        
        return retorno;
    }
}
