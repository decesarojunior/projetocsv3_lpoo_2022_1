
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author telmo
 * @data : 11/03/2022
 */

@Entity
@Table(name = "tb_endereco")
@NamedQueries({      
    @NamedQuery(name="Endereco.getbyid",
               query="SELECT e From Endereco e order by e.id asc ")
})
public class Endereco implements Serializable {
    
    //atributos seguindo a nomenclatura do DC.
    @Id
    @SequenceGenerator(name = "seq_endereco", sequenceName = "seq_endereco_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_endereco", strategy = GenerationType.SEQUENCE)    
    private Integer id;
    
    @Column(nullable = false, length = 8)
    private String cep;
    
    @Column(nullable = true, length = 100)
    private String complemento;

    public Endereco(){//construtor sem parametros.
        
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
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
        @Override
    public String toString(){
        return cep;
    }
    
    @Override
    public boolean equals(Object o){

        if(o == null){
            return false;

        }else if(!(o instanceof Endereco)){
            return false;

        }else{
            Endereco e = (Endereco) o;
            if (e.getId().intValue() == this.getId().intValue()){
                return true;
            }else{
                return false;
            }
        }
    }
            
            
    
}
