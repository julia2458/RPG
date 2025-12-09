//package br.com.julialupi.rpg.model.*;
import java.util.Random;

public class Clara extends Personagem
{
    //Construtor
    public Clara (String nome) throws Exception
    {
        super(nome, 95, 35, 10, 1);
    }
    
    //Método Batalhar - sobrescrevendo o metodo abstrato de Personagem
    @Override
    public void batalhar(Personagem alvo) throws Exception
    {
        // cria um objeto para gerar números de 1 até 10 de maneira aleatória simulando os dados do jogo
        Random rand = new Random();
        int dadoBase = rand.nextInt(10)+1;
        
        //calcular poder de ataque total
        int ataqueTotal = this.ataque + dadoBase;
        
        //Calcular dano causado
        int danoCausado = Math.max(1, ataqueTotal - alvo.defesa);
        
        //subtrair danoCausado do Inimigo
        alvo.receberDano(danoCausado);
        
        //Exibir detalhes do ataqueTotal
        System.out.println("\n A Clara defende o grupo e ataca com determinação. \n Seu ataque causou "+danoCausado+
                           " de dano no inimigo "+alvo.nome+". Restam apenas "+alvo.pontosVida+
                           " para esse inimigo.");
    }
    
    //Métodos Obrigatórios
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
