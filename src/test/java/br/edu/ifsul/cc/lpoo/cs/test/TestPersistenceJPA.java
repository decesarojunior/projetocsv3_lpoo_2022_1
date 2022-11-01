
package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Arma;
import br.edu.ifsul.cc.lpoo.cs.model.Artefato;
import br.edu.ifsul.cc.lpoo.cs.model.Calibre;
import br.edu.ifsul.cc.lpoo.cs.model.Compra;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.ItensCompra;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Municao;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.Tipo;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJPA;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author telmo
 */
public class TestPersistenceJPA {
    

    
    @Test 
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
    private Endereco getEndereco(PersistenciaJPA persistencia, String id) throws Exception {
                
        Endereco e = (Endereco) persistencia.find(Endereco.class, Integer.parseInt(id));
        if(e == null){
            e = new Endereco();
            e.setCep("99160000");
            persistencia.persist(e);

        }
            
        return e;                                    
        
    }
    
    //metodo de acesso privado(somente dentro dessa classe) que recebe dois parametros. Retorna o jogador. Se nao existir na tabela, persiste antes de retornar.
    private Jogador getJogador(PersistenciaJPA persistencia, String nickname) throws Exception {
                
        Jogador j = (Jogador) persistencia.find(Jogador.class, nickname);
        if(j == null){
            j = new Jogador();
            j.setNickname(nickname);
            j.setData_cadastro(Calendar.getInstance());
            j.setSenha("123");
            j.setEndereco(getEndereco(persistencia, "1"));
            persistencia.persist(j);

        }
            
        return j;                                    
        
    }
    
    private Artefato getArtefato(PersistenciaJPA persistencia, Integer id, String tipo) throws Exception {
        
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
    
    //metodo de acesso privado(somente dentro dessa classe) que recebe dois parametros. Retorna o jogador. Se nao existir na tabela, persiste antes de retornar.
    private ItensCompra getItemCompra(PersistenciaJPA persistencia, Float quantidade) throws Exception {
                
        ItensCompra i = (ItensCompra) persistencia.find(ItensCompra.class, 1);
        if(i == null){
            i = new ItensCompra();
            i.setArtefato(getArtefato(persistencia, 1, "A"));
            i.setQuantidade(quantidade);
            i.setValor( i.getQuantidade() * i.getArtefato().getValor());
        }
            
        return i;                                    
        
    }
    
        /*
        Atividade assíncrona - 07/05/2022
    
        Codifique na classe TestPersistenceJPA um teste unitário contendo os seguintes passos via (JPA):
    
        1) Recuperar a lista de partidas.
        2) Se a lista de partidas não for vazia, imprimir na tela os dados de cada objeto, altere e depois remova-o.
        3) Se a lista de partidas for vazia, persistir dois novos objetos de partida.
    
    */
    
    //@Test
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
    
    /*
    
       2) Atividade de revisão para a avaliação da primeira etapa. 30/05/2022.

       recupera a lista de compras

       imprimir na tela os dados de cada compra e as seus respectivos itens

       remova os itens e a compra

       caso a lista de compra esteja vazia, gera uma compra contendo itens. 
    */
    
    //@Test
    public void testListPersistenciaCompra() throws Exception {
        
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if(persistencia.conexaoAberta()){
            System.out.println("abriu a conexao com o BD via JPA");
            
            List<Compra> listCompras = persistencia.listCompras();
            if(listCompras != null && !listCompras.isEmpty()){
                
                for(Compra c : listCompras){
                    System.out.println("Compra: "+c.getId());
                    for(ItensCompra item : c.getItens()){
                        System.out.println("\tItem: "+item.getArtefato().getNome());
                        
                        System.out.println("Removendo o item: "+item.getId());
                        persistencia.remover(item);
                    }
                    
                    System.out.println("Removendo a compra: "+c.getId());
                    persistencia.remover(c);
                }
            }else{
                
                Compra c = new Compra();
                c.setData(Calendar.getInstance());
                c.setJogador(getJogador(persistencia, "teste@"));
                c.setTotal(0f);
                persistencia.persist(c);
                ItensCompra i = getItemCompra(persistencia, 1f);
                i.setCompra(c);
                persistencia.persist(i);
                
                System.out.println("Cadastrando a compra: "+c.getId());
                
            }

            
            persistencia.fecharConexao();
            
            System.out.println("fechou a conexao com o BD via JPA");
            
        }else{
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
        
    } 
    
}
