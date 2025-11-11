import java.util.Random;
public class Inimigo extends Personagem
{
    public Inimigo (String nome, int pontosVida, int ataque, int defesa, int nivel) throws Exception
    {
        super(nome, pontosVida, ataque, defesa, nivel);
    }
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        int ataqueTotal = this.ataque + dadoBase;
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        alvo.receberDano(danoCausado);
        System.out.println("\n O Inimigo ataca "+alvo.nome+
                           " \n Seu ataque causou "+danoCausado+
                           " de dano para "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse alvo.");
    }
    public Inimigo(Inimigo outra) throws Exception
    {
        super(outra);
    }
    @Override
    public Inimigo clone()
    {
        Inimigo retorno = null;
        try { 
            retorno = new Inimigo(this); 
        } catch (Exception e) {}
        return retorno;
    }
}
