
package br.edu.ifsul.cc.lpoo.cs.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author telmo
 */

@Entity
@Table(name = "tb_arma")
@DiscriminatorValue("A")
public class Arma extends Artefato{

    @Column(precision = 2, nullable = false)
    private Float comprimento_cano;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    
    
    @ManyToMany
    @JoinTable(name = "tb_arma_municao", joinColumns = {@JoinColumn(name = "artefato_id")}, //agregacao, vai gerar uma tabela associativa.
                                       inverseJoinColumns = {@JoinColumn(name = "municao_id")})
    private List<Municao> municoes;
    
    public Arma(){
        
    }

    /**
     * @return the comprimento_cano
     */
    public Float getComprimento_cano() {
        return comprimento_cano;
    }

    /**
     * @param comprimento_cano the comprimento_cano to set
     */
    public void setComprimento_cano(Float comprimento_cano) {
        this.comprimento_cano = comprimento_cano;
    }

    /**
     * @return the tipo
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the municoes
     */
    public List<Municao> getMunicoes() {
        return municoes;
    }

    /**
     * @param municoes the municoes to set
     */
    public void setMunicoes(List<Municao> municoes) {
        this.municoes = municoes;
    }
    
    
            
}
