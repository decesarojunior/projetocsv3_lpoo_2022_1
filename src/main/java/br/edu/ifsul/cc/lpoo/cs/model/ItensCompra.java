
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author telmo
 */
public class ItensCompra {
    
    private Integer id;
    
    private Float quantidade;
    
    private Float valor;
    
    private Artefato artefato;
        
    private Compra compra;//agregacao por composicao - ref. ent. forte.
    
    public ItensCompra(){
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the quantidade
     */
    public Float getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valor
     */
    public Float getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Float valor) {
        this.valor = valor;
    }

    /**
     * @return the artefato
     */
    public Artefato getArtefato() {
        return artefato;
    }

    /**
     * @param artefato the artefato to set
     */
    public void setArtefato(Artefato artefato) {
        this.artefato = artefato;
    }

    /**
     * @return the compra
     */
    public Compra getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    
    
    
}
