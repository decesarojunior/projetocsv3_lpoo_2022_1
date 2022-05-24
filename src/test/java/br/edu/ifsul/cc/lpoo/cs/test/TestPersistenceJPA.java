
package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJPA;
import java.util.Calendar;
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
            
            System.out.println("fechou a conexao com o BD via JPA");
            
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
    
    
    //@Test
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
    
    //metodo de acesso privado(somente dentro dessa classe) que recebe dois parametros. Retorna o jogador. Se nao existir na tabela, persiste antes de retornar.
    private Jogador getJogador(PersistenciaJPA persistencia, String nickname) throws Exception {
                
        Jogador j = (Jogador) persistencia.find(Jogador.class, nickname);
        if(j == null){
            j = new Jogador();
            j.setNickname(nickname);
            j.setData_cadastro(Calendar.getInstance());
            j.setSenha("123");
            persistencia.persist(j);

        }
            
        return j;                                    
        
    }
    
    @Test
    public void testPersistenciaListPartida_atividadeassincrona0705() throws Exception {
                      
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            //recupera a lista de partidas pela consulta nomeada.
            List<Partida> list = persistencia.listPartidas();
            
            //se nao contiver registros na tabela tb_partida
            if(list == null || list.isEmpty()){
                
                Partida p = new Partida();
                p.setInicio(Calendar.getInstance());                
                p.setJogador(getJogador(persistencia, "teste@"));
                
                //persiste uma nova partida
                persistencia.persist(p);     
                
                System.out.println("Cadastrou a partida: "+p.getId());
                
                p = new Partida();
                p.setInicio(Calendar.getInstance());                
                p.setJogador(getJogador(persistencia, "teste@"));
                
                //persiste uma nova partida
                persistencia.persist(p);     
                
                System.out.println("Cadastrou a partida: "+p.getId());
                
            }else{
                
                for(Partida p : list){
                    System.out.println("Partida: "+p.getId());
                    System.out.println("\t Jogador: "+p.getJogador().getNickname());
                    
                    p.setFim(Calendar.getInstance());
                    
                    //altera a partida.
                    persistencia.persist(p);
                    System.out.println("Alterou a partida "+p.getId());
                    
                    //remove a partida.
                    persistencia.remover(p);
                    
                    System.out.println("Remvou a partida "+p.getId());
                }
            }
            
            persistencia.fecharConexao();
            
            System.out.println("fechou a conexao com o BD via JPA");
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }
    
}
