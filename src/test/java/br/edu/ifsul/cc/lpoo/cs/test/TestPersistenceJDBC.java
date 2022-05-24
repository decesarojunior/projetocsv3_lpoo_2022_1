
package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Arma;
import br.edu.ifsul.cc.lpoo.cs.model.Artefato;
import br.edu.ifsul.cc.lpoo.cs.model.Calibre;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Municao;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import br.edu.ifsul.cc.lpoo.cs.model.Tipo;
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
    
    
    //@Test
    public void testListPersistenciaJogadorPatente() throws Exception {
        
        // recupera a lista de jogadores
        
        //imprimir na tela os dados de cada jogador e as suas respectivas patentes
        
        //alterar o jogador ao algum dado da tabela associativa.    

        //remove as patentes do jogador (tb_jogador_patente), uma a uma 
                
        //caso a lista de jogadores esteja vazia, insere um ou mais jogadores , bem como, vincula ao menos uma patente no jogador (tb_jogador_patente)        
                       
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JDBC");
            
            List<Jogador> list = persistencia.listJogadores();
            
            if(!list.isEmpty()){
                
                for(Jogador j : list){
                    
                    System.out.println("Jogador : "+j.getNickname());
                    System.out.println("Endereço: "+j.getEndereco().getCep());
                    
                    if(j.getPatentes() != null) {
                        for(Patente p : j.getPatentes()){

                            System.out.println("\tPatente : "+ p.getNome());

                        }
                    }
                    
                    j.setPontos(999);
                    
                    persistencia.persist(j);
                    
                    System.out.println(" Jogador alterado : "+ j.getNickname());
                    
                    persistencia.remover(j);
                    
                    System.out.println(" Jogador removido : "+ j.getNickname());
                                        
                }
                                                                           
            }else{
                
                Jogador j = new Jogador();
                j.setNickname("teste@");
                j.setSenha("123456");
                j.setPontos(0);
                Endereco end = new Endereco();
                end.setCep("99010010");
                
                persistencia.persist(end);                        
                j.setEndereco(end);
                
                Patente p = new Patente();
                p.setNome("Pantente de teste");
                persistencia.persist(p);
                
                j.setPatente(p);
                persistencia.persist(j);
                
                System.out.println("Incluiu o jogaador "+ j.getNickname()+ " com "+ j.getPatentes().size() + " patentes.");
                
                
            }
                    
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
        
        
    }
    
    
    private Artefato getArtefato(PersistenciaJDBC persistencia, Integer id, String tipo) throws Exception {
        
        Artefato a = (Artefato) persistencia.find(Artefato.class, id);
        if(a == null){
            if(tipo.equals("A")){                
                Arma arma = new Arma();
                arma.setNome("Revolver");
                arma.setPeso(1.5f);
                arma.setValor(1500f);
                arma.setComprimento_cano(1.2f);
                arma.setTipo(Tipo.FOGO);
                arma.setTipoArtefato("A");
                persistencia.persist(arma);                             
                return arma;
            }else{
                Municao municao = new Municao();
                municao.setNome("Bala");
                municao.setPeso(0.5f);
                municao.setValor(25.5f);
                municao.setExplosiva(Boolean.FALSE);
                municao.setCalibre(Calibre.C03);
                municao.setTipoArtefato("M");
                persistencia.persist(municao);
                return municao;
            }                
            
        }
        
        return a;
        
    }
    
    private Endereco getEndereco(PersistenciaJDBC persistencia, Integer id) throws Exception {
        
        Endereco e = (Endereco) persistencia.find(Endereco.class, id);
        if(e == null){            
                e = new Endereco();
                e.setCep("99010011");
                e.setComplemento("nenhum");                
                persistencia.persist(e);                             
                                        
        }
        
        return e;
        
    }
    
    
    @Test
    public void testListPersistenciaJogadorArtefato() throws Exception {
        
        // 1) Atividade de revisão para a avaliação da primeira etapa. 
     
        // recupera a lista de jogadores
        
        //imprimir na tela os dados de cada jogador e as suas respectivos artefatos (arma e/ou municao)
            

        //remove os artefatos do jogador (tb_jogador_artefato), um a um
                
        //caso a lista de jogadores esteja vazia, gera um jogador contendo dois artefatos. 
        
        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via jdbc");
            
            List<Jogador> list = persistencia.listJogadores();
            
            if(list == null || list.isEmpty()){
                
                Jogador j = new Jogador();
                j.setNickname("fulano@");
                j.setSenha("123456");
                j.setPontos(0);
                j.setEndereco(getEndereco(persistencia, 1));
                j.setArtefato(getArtefato(persistencia, 1, "A"));
                
                persistencia.persist(j);
                
                System.out.println("Cadastrou o jogador "+j.getNickname());
                
                
            }else{
                
                System.out.println("Listagem de jogadores cadastrados:");
                for(Jogador j : list){
                    
                    System.out.println("\tJogador: "+j.getNickname());                    
                    System.out.println("\t\tArtefatos:");
                    if(j.getArtefatos() != null)
                        for(Artefato a: j.getArtefatos()){
                            System.out.println("\t\t\tArtefato "+a.getNome());
                        }
                    
                    persistencia.remover(j);
                    System.out.println("Removeu o jogador "+j.getNickname());
                }
                                
            }
            
            persistencia.fecharConexao();
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via jdbc");
        } 
    }
            
    
    //@Test
    public void testListPersistenciaCompra() throws Exception {
     
        
         // 2) Atividade de revisão para a avaliação da primeira etapa. 
         
        // recupera a lista de compras
        
        //imprimir na tela os dados de cada compra e as seus respectivos itens

        //remova os itens e a compra
                
        //caso a lista de compra esteja vazia, gera uma compra contendo itens. 
        
    } 
   
}
