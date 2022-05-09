
package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author telmo
 */
public class PersistenciaJDBC implements InterfacePersistencia{
    
    /*
      <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/db_lpoo_cs_2022_1"/>
      <property name="javax.persistence.jdbc.user" value="postgres"/>
      <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
      <property name="javax.persistence.jdbc.password" value="123456"/>
    */     
    
    private final String DRIVER = "org.postgresql.Driver";
    private final String USER = "postgres";
    private final String SENHA = "123456";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_lpoo_cs_2022_1";
    private Connection con = null;
    
    public PersistenciaJDBC() throws Exception{
        
        Class.forName(DRIVER); //carregamento do driver postgresql em tempo de execução
        System.out.println("Tentando estabelecer conexao JDBC com : "+URL+" ...");
            
        //tenta estabelecer a conexao com o BD
        this.con = (Connection) 
                DriverManager.getConnection(URL, USER, SENHA); 
        
    }

    @Override
    public Boolean conexaoAberta() {
        
        try {
            if(con != null)
                return !con.isClosed();//verifica se a conexao está aberta
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return false;
       }

    @Override
    public void fecharConexao() {
        try{                               
            this.con.close();//fecha a conexao.
            System.out.println("Fechou conexao JDBC");
        }catch(SQLException e){            
            e.printStackTrace();//gera uma pilha de erro na saida.
        } 
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        
        //testa a classe
        if(Jogador.class == c){
            
            
        }else if (Endereco.class == c){
            
            //prepara o comando de selecao.
            PreparedStatement ps = 
                    this.con.prepareStatement(
                            "select id, cep, complemento "
                         + "from tb_endereco where id = ? ");
            ps.setInt(1, Integer.parseInt(id.toString()));
            
            //testa o comando sql e recupera o retorno.
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
            
                Endereco e = new Endereco();
                e.setId(rs.getInt("id"));
                e.setCep(rs.getString("cep"));
                e.setComplemento(rs.getString("complemento"));
                
                ps.close();
                
                return e;                
            }
    
            
        }
        //testar as demais classes do pacote model.
        
        
        return null;
        
        
    }

    @Override
    public void persist(Object o) throws Exception {
       
        //descobrir a instancia do Object o
        if(o instanceof Endereco){
            
            Endereco e = (Endereco) o; //converter o para o e que é do tipo Endereco
            
            //descobrir se é para realiar insert ou update.
            if(e.getId() == null){ 
                
                PreparedStatement ps = 
                        this.con.prepareStatement(
                                "insert into tb_endereco "
                                + "(id, cep, complemento) values "
                                + "(nextval('seq_endereco_id'), ?, ?) "
                                + "returning id");
                
                ps.setString(1, e.getCep());
                ps.setString(2, e.getComplemento());
                
                //executa o comando e recupera o retorno. 
                ResultSet rs = ps.executeQuery();                                 
                
                if (rs.next()) {
                    //seta no objeto e, para disponibilizar o acesso ao id gerado.
                    e.setId(rs.getInt(1));
                }
                
            }else{
                
                //update.
                PreparedStatement ps = 
                        this.con.prepareStatement(
                                "update tb_endereco set "
                              + "cep = ?, "
                              + "complemento = ? "
                              + "where id = ?");
                ps.setString(1, e.getCep());
                ps.setString(2, e.getComplemento());
                ps.setInt(3, e.getId());
                
                ps.execute();//executa o comando.
                
            }//fecha else
        
        }
        
        
        
    }

    @Override
    public void remover(Object o) throws Exception {
       
        //testa instancia de Endereco.
        if(o instanceof Endereco){

            Endereco e = (Endereco) o; //converter o para o e que é do tipo Endereco
            
            //prepara o comando sql 
            PreparedStatement ps = 
                    this.con.prepareStatement(
                            "delete from tb_endereco where id = ?");
            ps.setInt(1, e.getId());        
            
            //executa o comando sql através da conexao.
            //nesse caso não tem retorno.
            ps.execute();            
            
        //testa a instancia de Jogador    
        }else if (o instanceof Jogador){
            
            
        }
        //testar as demais instancias para as classes do pacote model.
        
        
    }

    @Override
    public Jogador doLogin(String nickname, String senha) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Endereco> listEnderecos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
