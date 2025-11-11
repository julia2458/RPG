import java.util.Random;
public class Luna extends Personagem
{
    public Luna (String nome) throws Exception
    {
        super(nome, 180, 20, 30, 1);
    }
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        int ataqueTotal = this.ataque + dadoBase;
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        alvo.receberDano(danoCausado);
        System.out.println("\n A Luna defende o grupo e ataca com determinaÃ§Ã£o. \n Seu ataque causou "+danoCausado+
                           " de dano no inimigo "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse inimigo.");
    }
    public Luna(Luna outra) throws Exception
    {
        super(outra);
    }
    @Override
    public Luna clone()
    {
        Luna retorno = null;
        try { 
            retorno = new Luna(this); 
        } catch (Exception e) {}
        return retorno;
    }
}
