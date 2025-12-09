public class Item implements Cloneable, Comparable<Item>
{
    private String nome;
    private String descricao;
    private String efeito;
    private byte quantidade;
    public Item (String nome, String descricao, String efeito, byte quantidade)
    {
        this.nome = nome;
        this.descricao = descricao;
        this.efeito = efeito;
        this.quantidade = quantidade;
    }
    public String getNome(){ return this.nome; }
    public String getDescricao(){ return this.descricao; }
    public String getEfeito(){ return this.efeito; }
    public byte getQuantidade(){ return this.quantidade; }
    public void setNome (String nome) throws Exception
    {
        if (nome == null || nome.trim().isEmpty())
            throw new Exception ("O nome do item nao pode ser vazio.");
        this.nome = nome;
    }
    public void setDescricao (String descricao) throws Exception
    {
        if (descricao == null || descricao.trim().isEmpty())
            throw new Exception ("A descricao do item nao pode ser vazia.");
        this.descricao = descricao;
    }
    public void setEfeito (String efeito) throws Exception
    {
        if (efeito == null || efeito.trim().isEmpty())
            throw new Exception ("O efeito do item nao pode ser vazio.");
        this.efeito = efeito;
    }
    public void setQuantidade (byte quantidade) throws Exception
    {
        if (quantidade < 0)
            throw new Exception ("Quantidade invalida.");
        this.quantidade = quantidade;
    }
    public boolean verificarItem ()
    {
        if (this.quantidade > 0)
            return true;
        return false;
    }
    public void adicionarQuantidade (byte valor) throws Exception
    {
        if (valor < 1)
            throw new Exception ("O valor a ser adicionado deve ser maior que zero.");
        this.quantidade =(byte)(this.quantidade + valor);
    }
    public void usarItem ()
    {
        if (!verificarItem()){
            System.out.println("Voce nao possui mais o item " + nome + ".");
            return;
        }
        this.quantidade--;
        System.out.println("Voce usou o item: " + nome + ". Agora Restam " + quantidade + " unidades.");
    }
    @Override
    public String toString()
    {
        return "Nome: " + this.nome + "\n" +
                "Descricaoo: " + this.descricao + "\n" +
                "Efeito: " + this.efeito + "\n" +
                "Quantidade: " + (this.quantidade);
    }
    @Override
    public boolean equals (Object obj)
    {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Item i = (Item)obj;
        return this.nome.equalsIgnoreCase(i.nome) &&
               this.descricao.equalsIgnoreCase(i.descricao) &&
               this.efeito.equalsIgnoreCase(i.efeito);
    }
    @Override
    public int hashCode ()
    {
        int ret = 7;
        ret = 31 * ret + this.nome.toLowerCase().hashCode();
        ret = 31 * ret + this.descricao.toLowerCase().hashCode();
        ret = 31 * ret + this.efeito.toLowerCase().hashCode();
        return ret;
    }
    public Item (Item item) throws Exception
    {
        if(item == null)
            throw new Exception ("Modelo ausente");
        this.nome=item.nome;
        this.descricao=item.descricao;
        this.efeito=item.efeito;
        this.quantidade=item.quantidade;
    }
    @Override
    public Item clone()
    {
        Item retorno = null;
        try {
            retorno = new Item(this);
        } catch (Exception erro) {}
        return retorno;
    }
    @Override
    public int compareTo (Item outro)
    {
        return this.nome.compareToIgnoreCase(outro.nome);
    }
}
