
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author telmo
 */
public class ResultadoID {
    
    private Objetivo objetivo;

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
