
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author telmo
 */

@Entity
@Table(name = "tb_round")
public class Round implements Serializable {    
    
    @Id
    @SequenceGenerator(name = "seq_round", sequenceName = "seq_round_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_round", strategy = GenerationType.SEQUENCE) 
    private Integer id;
    
    @Column(nullable = false)
    private Integer numero;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)    
    private Calendar inicio;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar fim;    
    
    @ManyToMany
    @JoinTable(name = "tb_round_objetivo", joinColumns = {@JoinColumn(name = "round_id")}, //agregacao, vai gerar uma tabela associativa.
                                       inverseJoinColumns = {@JoinColumn(name = "objetivo_id")})    
    private List<Objetivo> objetivos;
        
    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false)//associacao    
    private Partida partida;
        
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)    
    private Modo modo;
    
    public Round(){
        
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
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the inicio
     */
    public Calendar getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fim
     */
    public Calendar getFim() {
        return fim;
    }

    /**
     * @param fim the fim to set
     */
    public void setFim(Calendar fim) {
        this.fim = fim;
    }

    /**
     * @return the objetivos
     */
    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos;
    }

    /**
     * @return the partida
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * @param partida the partida to set
     */
    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    /**
     * @return the modo
     */
    public Modo getModo() {
        return modo;
    }

    /**
     * @param modo the modo to set
     */
    public void setModo(Modo modo) {
        this.modo = modo;
    }
    
    
}
