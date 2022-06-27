
package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Compra;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import java.util.List;

/**
 *
 * @author telmo
 */
public interface InterfacePersistencia {
    
    public Boolean conexaoAberta();
    
    public void fecharConexao();
    
    public Object find(Class c, Object id) throws Exception;//select.
    
    public void persist(Object o) throws Exception;//insert ou update.
    
    public void remover(Object o) throws Exception;//delete.
    
    public Jogador doLogin(String nickname, String senha) throws Exception;
    
    public List<Endereco> listEnderecos() throws Exception;
    
    public List<Jogador> listJogadores() throws Exception;
    
    public List<Patente> listPatentes() throws Exception;
    
     public List<Compra> listCompras() throws Exception;
     
     public List<Partida> listPartidas() throws Exception; // atividade assincrona 24/05
    
}
