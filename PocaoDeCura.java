public class PocaoDeCura extends Item {
    private int pontosDeVidaRestaurados;
    public PocaoDeCura(String nome, int pontosDeVidaRestaurados, byte quantidade) {
        super(nome, 
              "Restaura " + pontosDeVidaRestaurados + " pontos de vida.",
              "Cura " + pontosDeVidaRestaurados + " HP",
              quantidade);
        this.pontosDeVidaRestaurados = pontosDeVidaRestaurados;
    }
    public int getPontosDeVidaRestaurados() {
        return pontosDeVidaRestaurados;
    }
    public void setPontosDeVidaRestaurados(int pontos) throws Exception {
        if (pontos <= 0)
            throw new Exception("Pontos de vida restaurados devem ser positivos.");
        this.pontosDeVidaRestaurados = pontos;
    }
    public void usar(Personagem alvo) throws Exception {
        if (alvo == null)
            throw new Exception("Alvo invalido.");
        if (getQuantidade() <= 0) {
            System.out.println("Voce nao possui mais " + getNome() + ".");
            return;
        }
        int hpAtual = alvo.getPontosVida();
        int novoHP = hpAtual + pontosDeVidaRestaurados;
        alvo.setPontosVida(novoHP);
        System.out.println("[HEAL] " + alvo.getNome() + " usou " + getNome() + 
                           " e recuperou " + pontosDeVidaRestaurados + " HP!");
        System.out.println("   HP atual: " + alvo.getPontosVida());
        alvo.getInventario().removerItem(getNome(), 1);
    }
    public PocaoDeCura(PocaoDeCura outra) throws Exception {
        super(outra);
        this.pontosDeVidaRestaurados = outra.pontosDeVidaRestaurados;
    }
    @Override
    public PocaoDeCura clone() {
        PocaoDeCura retorno = null;
        try {
            retorno = new PocaoDeCura(this);
        } catch (Exception e) {}
        return retorno;
    }
    @Override
    public String toString() {
        return super.toString() + 
               "\nRestauraÃ§Ã£o: " + pontosDeVidaRestaurados + " HP";
    }
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof PocaoDeCura)) return false;
        PocaoDeCura outra = (PocaoDeCura) obj;
        return this.pontosDeVidaRestaurados == outra.pontosDeVidaRestaurados;
    }
    @Override
    public int hashCode() {
        int resultado = super.hashCode();
        resultado = 31 * resultado + pontosDeVidaRestaurados;
        return resultado;
    }
}
