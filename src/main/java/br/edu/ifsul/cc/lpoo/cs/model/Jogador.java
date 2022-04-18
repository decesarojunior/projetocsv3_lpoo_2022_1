
package br.edu.ifsul.cc.lpoo.cs.model;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author telmo
 */
public class Jogador {
    
    private String nickname;
    private String senha;
    private Integer pontos;
    private Calendar data_cadastro;
    private Calendar data_ultimo_login;
    private Endereco endereco;//associação.
    private List<Patente> patentes;//agregacao.
    
    private Jogador(){
        
    }
}
