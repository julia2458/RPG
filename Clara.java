import java.util.Random;
public class Clara extends Personagem
{
    public Clara (String nome) throws Exception
    {
        super(nome, 95, 35, 10, 1);
    }
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        int ataqueTotal = this.ataque + dadoBase;
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        alvo.receberDano(danoCausado);
        System.out.println("\n A Clara defende o grupo e ataca com determinaÃ§Ã£o. \n Seu ataque causou "+danoCausado+
                           " de dano no inimigo "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse inimigo.");
    }
    public Clara(Clara outra) throws Exception
    {
        super(outra);
    }
    @Override
    public Clara clone()
    {
        Clara retorno = null;
        try { 
            retorno = new Clara(this); 
        } catch (Exception e) {}
        return retorno;
    }
}
