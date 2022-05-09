
package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import org.junit.Test;

/**
 *
 * @author telmo
 */
public class TestPersistenceJDBC {
    
    @Test
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
