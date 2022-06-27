
package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Compra;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author telmo
 */
public class PersistenciaJPA implements InterfacePersistencia{
    
    public EntityManagerFactory factory;    //fabrica de gerenciadores de entidades
    public EntityManager entity;            //gerenciador de entidades JPA

    
    public PersistenciaJPA(){
        
        //parametro: é o nome da unidade de persistencia (Persistence Unit)
        factory = Persistence.createEntityManagerFactory("pu_db_lpoo_cs_2022_1");
        entity = factory.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        
        return entity.isOpen(); 
    }

    @Override
    public void fecharConexao() {
       entity.close();  
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
       
        return entity.find(c, id);//encontra um determinado registro              
    }

    @Override
    public void persist(Object o) throws Exception {
        
        entity.getTransaction().begin();// abrir a transacao.
        entity.persist(o); //realiza o insert ou update.
        entity.getTransaction().commit(); //comita a transacao (comando sql)
        
        
    }

    @Override
    public void remover(Object o) throws Exception {
        
        entity.getTransaction().begin();// abrir a transacao.
        entity.remove(o); //realiza o delete
        entity.getTransaction().commit(); //comita a transacao (comando sql)
        
        
    }

    @Override
    public Jogador doLogin(String nickname, String senha) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Endereco> listEnderecos() throws Exception {
        
        return entity.createNamedQuery("Endereco.getbyid").getResultList();
        
    }

    @Override
    public List<Jogador> listJogadores() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Patente> listPatentes() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Compra> listCompras() throws Exception {
       
        return entity.createNamedQuery("Compra.getbyid").getResultList();        
    }

    @Override
    public List<Partida> listPartidas() throws Exception {
        
        //resposta para a atividade assincrona 7/05
        //executa a consulta nomeada e retorna a lista.
        return entity.createNamedQuery("Partida.getbyid").getResultList();
    }
    
}
