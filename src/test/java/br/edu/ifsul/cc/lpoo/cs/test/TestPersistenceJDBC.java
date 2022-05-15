
package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author telmo
 */
public class TestPersistenceJDBC {
    
    //@Test
    public void testConexao() throws Exception  {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via jdbc");
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via jdbc");
        }  
    }
    
    /* Exercício : 09/05/2022
    
      Implementar um metodo de teste para realizar as seguintes operacoes:
    
        1) abrir conexao via jdbc
        2) selecionar um determinado registro na tabela tb_endereco
        3) caso encontre, alterar e remover.
        4) caso nao encontre, inserir novo registro.
        5) fechar a conexao.
    
    */
    
    //@Test
    public void testListPersistenciaPatente() throws Exception {
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            
            List<Patente> lista = persistencia.listPatentes();
            
            if(!lista.isEmpty()){
            
                for(Patente p : lista){

                    System.out.println("Endereco: "+p.getId()+" CEP: "+p.getNome());
                                        
                    persistencia.remover(p);
                }

            }else{
                
                System.out.println("Não encontrou o patente");
                
                Patente p = new Patente();
                p.setNome("patente bronze");                
                persistencia.persist(p); //insert na tabela.                
                System.out.println("Cadastrou o Patente "+p.getId());

                
                p.setNome("Patente Bronze"); 
                persistencia.persist(p); //update na tabela.
                System.out.println("Cadastrou o Patente "+p.getId());
                
            }
            
            persistencia.fecharConexao();
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }
    
    
    @Test
    public void testListPersistenciaJogador() throws Exception {
        
        
        // recupera a lista de jogadores
        
        //imprimir na tela os dados de cada jogador e as suas respectivas patentes
        
        //alterar o jogador ao algum dado da tabela associativa.    

        //remove as patentes do jogador (tb_jogador_patente), uma a uma 
                
        //caso a lista de jogadores esteja vazia, insere um ou mais jogadores , bem como, vincula ao menos uma patente no jogador (tb_jogador_patente)        
                       
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");
            
            List<Jogador> list = persistencia.listJogadores();
            
            list.forEach(j->System.out.println("Jogador: "+j.getNickname()+ " CEP: "+j.getEndereco().getCep()));
                    
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
