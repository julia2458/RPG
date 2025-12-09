import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventario implements Cloneable
{
    private List<Item> itensMochila;
    
    public Inventario()
    {
        this.itensMochila = new ArrayList<>();
    }
    
    public boolean jaExiste(Item novo) {
        for (Item i : itensMochila)
        {
            if(i.equals(novo))
                return true;
        }
        
        return false;
    } 
    
    public void adicionarItemNaMochila(Item novo) throws Exception 
    {
        if (novo == null) {
            throw new Exception("Item inválido.");
        }
    
        if (novo instanceof Artefato || novo instanceof LanternaEncantada) {
            Item clonado = novo.clone();
            if (clonado != null) {
                itensMochila.add(clonado);
            }
            System.out.println("Novo item adicionado à mochila: " + novo.getNome() + ".");
            return;
        }
    
        if (jaExiste(novo)) {
            for (Item i : itensMochila) 
            {
                if (i.equals(novo)) 
                {
                    i.adicionarQuantidade(novo.getQuantidade());
                    System.out.println("Você adicionou mais " + novo.getQuantidade() + 
                                       " unidade(s) de " + novo.getNome() + ".");
                    return;
                }
            }
        } 
        else 
        {
            Item clonado = novo.clone();
            if (clonado != null) {
                itensMochila.add(clonado);
            }
            System.out.println("Novo item adicionado à mochila: " + novo.getNome() + ".");
        }
    }
    
    public boolean removerItem(String nome, int quantidade) throws Exception
    {
        if (nome == null || nome.trim().isEmpty()) 
            throw new Exception("Nome invalido.");
        if (quantidade < 0) 
            throw new Exception("Quantidade para remover deve ser positiva ou zero.");
            
        for (int idx = 0; idx < itensMochila.size(); idx++)
        {
            Item i = itensMochila.get(idx);
            if (i.getNome().equalsIgnoreCase(nome))
            {
                if (quantidade > 0 && i.getQuantidade() < quantidade)
                {
                    throw new Exception ("Quantidade insuficiente na mochila.");
                }
                
                if (quantidade > 0) {
                    i.setQuantidade((byte)(i.getQuantidade() - quantidade));
                }
                
                if(i.getQuantidade() == 0)
                {
                    itensMochila.remove(idx);
                }
                
                return true;
            }
        }
        
        return false;
    }
    
    public List<Item> listarItensMochilaOrdenados() throws Exception
    {
        if(itensMochila.isEmpty())
            return new ArrayList<>();
            
        List<Item> copia = new ArrayList<>(itensMochila);
        Collections.sort(copia);
        
        return copia;
    }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("Itens na Mochila: \n");
        
                
        for (Item i : itensMochila)
        {
            sb.append(" - ").append(i.getNome())
                .append (" (").append(i.getEfeito()).append(")")
                .append(" | Quantidade: ").append(i.getQuantidade())
                .append("\n");
        }
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        Inventario inv = (Inventario)obj;
        
        return this.itensMochila.equals(inv.itensMochila);
    }
    
    @Override
    public int hashCode()
    {
        return itensMochila.hashCode();
    }
    
    public Inventario(Inventario inventario) throws Exception
    {
        if (inventario == null)
            throw new Exception ("Modelo ausente");
        
        this.itensMochila = new ArrayList<>();
        for (Item i : inventario.itensMochila){
            this.itensMochila.add(i.clone());
        }
    }
    
    @Override
    public Inventario clone()
    {
        Inventario retorno = null;
        
        try
        {
            retorno = new Inventario(this);
            
        } catch (Exception e){
            return null;
        }
        
        return retorno;
    }
}
