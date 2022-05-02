
package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJPA;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author telmo
 */
public class TestPersistenceJPA {
    
    /*
        Atividade assíncrona - 07/05/2022
    
        Codifique na classe TestPersistenceJPA um teste unitário contendo os seguintes passos via (JPA):
    
        1) Recuperar a lista de partidas.
        2) Se a lista de partidas não for vazia, imprimir na tela os dados de cada objeto, altere e depois remova-o.
        3) Se a lista de partidas for vazia, persistir dois novos objetos de partida.
    
    */
    
    //@Test 
    public void testConexaoGeracaoTabelas(){
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    } 
    
    //@Test
    public void testPersistenciaEndereco() throws Exception {
    
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("testPersistenciaEndereco:");            
            Endereco e = (Endereco) persistencia.find(Endereco.class, 1);
            if(e == null){
                System.out.println("nao encontrou o id =1");
                e = new Endereco(); 
                e.setCep("99010011");
                //encaminhar o e
                persistencia.persist(e);               
            }else{      
                System.out.println("encontrou o id=1 para endereco "+e.getCep());
                persistencia.remover(e);
            }            
            persistencia.fecharConexao();           
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }        
    }
    
    
    @Test
    public void testPersistenciaListEndereco() throws Exception {
    
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("testPersistenciaEndereco:");            
            
            List<Endereco> list = persistencia.listEnderecos();
            if(!list.isEmpty()){
            
                for(Endereco end : list){
                    
                    System.out.println("Endereco: "+end.getCep());
                }
            }
            
            
            persistencia.fecharConexao();           
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }        
    }
    
    
}
