import java.util.Random;
public class Bia extends Personagem
{
    public Bia (String nome) throws Exception
    {
        super(nome, 110, 25, 20, 1);
    }
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        int ataqueTotal = this.ataque + dadoBase;
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        alvo.receberDano(danoCausado);
        System.out.println("\n A Bia defende o grupo e ataca com determinaÃ§Ã£o. \n Seu ataque causou "+danoCausado+
                           " de dano no inimigo "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse inimigo.");
    }
    public Bia(Bia outra) throws Exception
    {
        super(outra);
    }
    @Override
    public Bia clone()
    {
        Bia retorno = null;
        try { 
            retorno = new Bia(this); 
        } catch (Exception e) {}
        return retorno;
    }
}
