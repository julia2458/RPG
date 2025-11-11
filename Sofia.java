import java.util.Random;
public class Sofia extends Personagem
{
    public Sofia (String nome) throws Exception
    {
        super(nome, 130, 15, 35, 1);
    }
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        int ataqueTotal = this.ataque + dadoBase;
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        alvo.receberDano(danoCausado);
        System.out.println("\n A Sofia defende o grupo e ataca com determinaÃ§Ã£o. \n Seu ataque causou "+danoCausado+
                           " de dano no inimigo "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse inimigo.");
    }
    public Sofia(Sofia outra) throws Exception
    {
        super(outra);
    }
    @Override
    public Sofia clone()
    {
        Sofia retorno = null;
        try { 
            retorno = new Sofia(this); 
        } catch (Exception e) {}
        return retorno;
    }
}
