
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author telmo
 */

@Embeddable
public class ResultadoID implements Serializable{
    
    @ManyToOne
    @JoinColumn(name = "objetivo_id")
    private Objetivo objetivo;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;
    
    public ResultadoID(){
        
    }

    /**
     * @return the objetivo
     */
    public Objetivo getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the round
     */
    public Round getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(Round round) {
        this.round = round;
    }
    
    
    
}
