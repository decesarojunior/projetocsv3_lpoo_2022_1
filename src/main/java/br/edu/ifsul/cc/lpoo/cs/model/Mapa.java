
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author telmo
 */

@Entity
@Table(name = "tb_mapa")
public class Mapa implements Serializable{
        
    @Id
    @SequenceGenerator(name = "seq_mapa", sequenceName = "seq_mapa_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_mapa", strategy = GenerationType.SEQUENCE)  
    private Integer id;
        
    @Column(nullable = false, length = 100)
    private String nome;
    
    @ManyToMany
    @JoinTable(name = "tb_mapa_local", joinColumns = {@JoinColumn(name = "mapa_id")}, //agregacao, vai gerar uma tabela associativa.
                                       inverseJoinColumns = {@JoinColumn(name = "local_id")})    
    private List<Local> locais;
    
    
    
    public Mapa(){
        
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the locais
     */
    public List<Local> getLocais() {
        return locais;
    }

    /**
     * @param locais the locais to set
     */
    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }
    
    
    
}
