
package br.edu.ifsul.cc.lpoo.cs.model;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author telmo
 */
public class Partida {
    
    private Integer id;
        
    private Calendar inicio;
    
    private Calendar fim;
        
    private Jogador jogador;
    
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
