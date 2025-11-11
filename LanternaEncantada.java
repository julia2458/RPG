public class LanternaEncantada extends Item {
    private boolean acesa;
    public LanternaEncantada() {
        super("Lanterna Encantada",
              "Revela passagens secretas e ilumina os caminhos escuros.",
              "RevelaÃ§Ã£o de passagens secretas",
              (byte) 1);
        this.acesa = false;
    }
    public boolean estaAcesa() {
        return acesa;
    }
    public void acender() {
        if (!acesa) {
            acesa = true;
            System.out.println("ðŸ”¦ A Lanterna Encantada brilha com uma luz mÃ¡gica!");
        } else {
            System.out.println("A lanterna jÃ¡ estÃ¡ acesa.");
        }
    }
    public void apagar() {
        if (acesa) {
            acesa = false;
            System.out.println("A lanterna se apaga.");
        } else {
            System.out.println("A lanterna jÃ¡ estÃ¡ apagada.");
        }
    }
    public void usar() {
        if (!verificarItem()) {
            System.out.println("VocÃª nÃ£o possui a Lanterna Encantada.");
            return;
        }
        if (!acesa) {
            acender();
        } else {
            System.out.println("ðŸ”¦ A luz da lanterna ilumina passagens ocultas nas sombras...");
        }
    }
    @Override
    public void adicionarQuantidade(byte valor) throws Exception {
        if (valor > 0)
            throw new Exception("VocÃª jÃ¡ possui a Lanterna Encantada.");
    }
    public LanternaEncantada(LanternaEncantada outra) throws Exception {
        super(outra);
        this.acesa = outra.acesa;
    }
    @Override
    public LanternaEncantada clone() {
        LanternaEncantada retorno = null;
        try {
            retorno = new LanternaEncantada(this);
        } catch (Exception e) {}
        return retorno;
    }
    @Override
    public String toString() {
        return super.toString() + 
               "\nStatus: " + (acesa ? "Acesa âœ¨" : "Apagada");
    }
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (!(obj instanceof LanternaEncantada)) return false;
        LanternaEncantada outra = (LanternaEncantada) obj;
        return this.acesa == outra.acesa;
    }
    @Override
    public int hashCode() {
        int resultado = super.hashCode();
        resultado = 31 * resultado + (acesa ? 1 : 0);
        return resultado;
    }
}
