
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author telmo
 * @doc: https://www.devmedia.com.br/tipos-de-heranca-no-hibernate/28641
 */

@Entity
@Table(name = "tb_artefato")
@Inheritance(strategy = InheritanceType.JOINED)//alterar a estratÃ©gia.
@DiscriminatorColumn(name = "tipo")
public abstract class Artefato implements Serializable {
    
    @Id
    @SequenceGenerator(name = "seq_artefato", sequenceName = "seq_artefato_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_artefato", strategy = GenerationType.SEQUENCE) 
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(precision = 2, nullable = true)
    private Float peso;
    
    @Column(precision = 2, nullable = false)
    private Float valor;
    
    @Transient
    private String tipo;
    
    public Artefato(){
        
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
     * @return the peso
     */
    public Float getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(Float peso) {
        this.peso = peso;
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
     * @return the tipo
     */
    public String getTipoArtefato() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipoArtefato(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    
    
}
