
package br.edu.ifsul.cc.lpoo.cs.model;

import java.util.Calendar;
import java.util.List;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author telmo
 */

@Entity
@Table(name = "tb_partida" )
@NamedQueries({      
    @NamedQuery(name="Partida.getbyid",
               query="SELECT p From Partida p order by p.id asc ")
})
public class Partida implements Serializable {
    
    
    @Id
    @SequenceGenerator(name = "seq_partida", sequenceName = "seq_partida_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_partida", strategy = GenerationType.SEQUENCE) 
    private Integer id;
     
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar inicio;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)    
    private Calendar fim;
        
    @ManyToOne
    @JoinColumn(name = "jogador_nickname", nullable = false)    
    private Jogador jogador;
    
    @OneToMany(mappedBy = "partida")//mappedBy deve apontar para a referencia de jogador dentro de Compra.
    private List<Round> rounds;
    
    
    public Partida(){
        
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
     * @return the jogador
     */
    public Jogador getJogador() {
        return jogador;
    }

    /**
     * @param jogador the jogador to set
     */
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    /**
     * @return the rounds
     */
    public List<Round> getRounds() {
        return rounds;
    }

    /**
     * @param rounds the rounds to set
     */
    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }
    
    
    
}
