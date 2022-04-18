
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author telmo
 */
public class Municao extends Artefato {
    
    private Boolean explosiva;
        
    private Calibre calibre;
    
    public Municao(){
        
    }

    /**
     * @return the explosiva
     */
    public Boolean getExplosiva() {
        return explosiva;
    }

    /**
     * @param explosiva the explosiva to set
     */
    public void setExplosiva(Boolean explosiva) {
        this.explosiva = explosiva;
    }

    /**
     * @return the calibre
     */
    public Calibre getCalibre() {
        return calibre;
    }

    /**
     * @param calibre the calibre to set
     */
    public void setCalibre(Calibre calibre) {
        this.calibre = calibre;
    }
    
    
    
}
