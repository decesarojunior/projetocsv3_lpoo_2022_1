
package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
            
            //tb_jogador
            PreparedStatement ps = this.con.prepareStatement("select nickname, data_cadastro, data_ultimo_login, pontos, senha, endereco_id from tb_jogador where nickname = ? ");
            
            ps.setString(1, id.toString());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                
                Jogador j = new Jogador();
                    j.setNickname(rs.getString("nickname"));
                    
                        Calendar dtCad = Calendar.getInstance();
                        dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
                    j.setData_cadastro(dtCad);
                        
                    if(rs.getDate("data_ultimo_login") != null){
                        Calendar dtU = Calendar.getInstance();
                        dtU.setTimeInMillis(rs.getDate("data_ultimo_login").getTime());
                        j.setData_ultimo_login(dtU);
                    }
                    
                    j.setPontos(rs.getInt("pontos"));
                    j.setSenha(rs.getString("senha"));
                        Endereco end = new Endereco();
                        end.setId(rs.getInt("endereco_id"));
                    j.setEndereco(end);
                    
                    PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.cor, p.nome from tb_jogador_patente jp, tb_patente p where p.id=jp.patente_id and jp.jogador_nickname = ? ");
                    ps2.setString(1, id.toString());
                    
                    ResultSet rs2 = ps.executeQuery();
                    
                    while(rs2.next()){
                        
                        Patente p = new Patente();
                        p.setId(rs2.getInt("id"));
                        p.setNome(rs2.getString("nome"));
                        p.setCor(rs2.getString("cor"));
                        
                        j.setPatente(p);
                    }
                
            }
            
            
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
    
            
        }else if(c == Patente.class){
                                   
            PreparedStatement ps = this.con.prepareStatement("select id, cor, nome from tb_patente where id = ?");
            
            ps.setInt(1, Integer.parseInt(id.toString()));
                                                           
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
            
        }else if(o instanceof Jogador){
        
        Jogador j = (Jogador) o;
            
            if(j.getData_cadastro() == null){
                
                //insert
                
                PreparedStatement ps = this.con.prepareStatement("insert into tb_jogador "
                                                                            + "(nickname, data_cadastro, pontos, senha, endereco_id) values "
                                                                            + "(?, now(), ?, ?, ? ) ");

                ps.setString(1, j.getNickname());            
                ps.setInt(2, j.getPontos());
                ps.setString(3, j.getSenha());
                ps.setInt(4, j.getEndereco().getId());                

                ps.execute();

                if(j.getPatentes() != null && !j.getPatentes().isEmpty()){

                    for(Patente p : j.getPatentes()){

                         PreparedStatement ps2 = this.con.prepareStatement("insert into tb_jogador_patente "
                                                                                    + "(jogador_nickname, patente_id) values "
                                                                                    + "(?, ? ) ");

                         ps2.setString(1, j.getNickname());
                         ps2.setInt(2, p.getId());

                         ps2.execute();

                    }

                }
                
                
                
            }else {
                
                //update.
                //alterar as colunas : data_cadastro, pontos, senha, endereco_id
                
                PreparedStatement ps = this.con.prepareStatement("update tb_jogador set "
                                                                            + "pontos = ?, senha = ?, endereco_id = ? where nickname = ? ");
                
                ps.setInt(1, j.getPontos());
                ps.setString(2, j.getSenha());
                ps.setInt(3, j.getEndereco().getId());                
                ps.setString(4, j.getNickname());     
                
                ps.execute();
                
                PreparedStatement ps2 = this.con.prepareStatement("delete from tb_jogador_patente where jogador_nickname = ? ");
                ps2.setString(1, j.getNickname());
                                
                if(j.getPatentes() != null && !j.getPatentes().isEmpty()){

                    for(Patente p : j.getPatentes()){

                         PreparedStatement ps3 = this.con.prepareStatement("insert into tb_jogador_patente "
                                                                                    + "(jogador_nickname, patente_id) values "
                                                                                    + "(?, ? ) ");

                         ps3.setString(1, j.getNickname());
                         ps3.setInt(2, p.getId());

                         ps3.execute();

                    }

                }
                
            }
            
        } else if (o instanceof Patente){
                
            Patente p = (Patente) o; //converter o para o e que é do tipo Endereco
            if(p.getId() == null){ 
                
                PreparedStatement ps = 
                        this.con.prepareStatement(
                                "insert into tb_patente "
                                + "(id, cor, nome) values "
                                + "(nextval('seq_patente_id'), ?, ?) "
                                + "returning id");
                
                ps.setString(1, p.getCor());
                ps.setString(2, p.getNome());
                
                //executa o comando e recupera o retorno. 
                ResultSet rs = ps.executeQuery();                                 
                
                if (rs.next()) {
                    //seta no objeto e, para disponibilizar o acesso ao id gerado.
                    p.setId(rs.getInt(1));
                }
                
                
                
            }else{
                
                //update.
                
                PreparedStatement ps = 
                        this.con.prepareStatement(
                                "udpate tb_patente  set "
                                + "cor =  ?, nome = ? where id = ?");
                
                ps.setString(1, p.getCor());
                ps.setString(2, p.getNome());
                ps.setInt(3, p.getId());
                
                //executa o comando e recupera o retorno. 
                ResultSet rs = ps.executeQuery();                                 
                
                if (rs.next()) {
                    //seta no objeto e, para disponibilizar o acesso ao id gerado.
                    p.setId(rs.getInt(1));
                }
            }
                
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
        }else if(o instanceof Jogador){
            
            //remove registro(s) em tb_jogador_patente
            //remove registro em tb_jogador
            
            Jogador j = (Jogador) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_jogador_patente where jogador_nickname = ?");
            ps.setString(1, j.getNickname());       
            ps.execute();
            ps.close();
            
            ps = this.con.prepareStatement("delete from tb_jogador where nickname = ?");
            ps.setString(1, j.getNickname());       
            ps.execute();
            ps.close();
            
        }else if(o instanceof Patente){
            
            Patente p = (Patente) o; //converter o para o e que é do tipo Endereco            
            
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
    
    @Override
    public List<Jogador> listJogadores() throws Exception {
                
        List<Jogador> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select j.nickname, j.data_cadastro, j.data_ultimo_login, j.pontos, j.senha, "
                + "e.id as endereco_id, e.cep "
                + "from tb_jogador j left join tb_endereco e "
                + "on j.endereco_id=e.id "
                + "order by j.data_cadastro asc");
        
        ResultSet rs = ps.executeQuery();//executa a query        

        lista = new ArrayList();
        while(rs.next()){
            
            Jogador j = new Jogador();
            j.setNickname(rs.getString("nickname"));

                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());                        
            j.setData_cadastro(dtCad);

            if(rs.getDate("data_ultimo_login") != null){
                Calendar dtU = Calendar.getInstance();
                dtU.setTimeInMillis(rs.getDate("data_ultimo_login").getTime());
                j.setData_ultimo_login(dtU);
            }
            
            j.setPontos(rs.getInt("pontos"));
            j.setSenha(rs.getString("senha"));
                Endereco end = new Endereco();
                end.setId(rs.getInt("endereco_id"));
                end.setCep(rs.getString("cep"));
            j.setEndereco(end);

            PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.cor, p.nome from tb_jogador_patente jp, tb_patente p where p.id=jp.patente_id and jp.jogador_nickname = ? ");
            ps2.setString(1, j.getNickname());

            ResultSet rs2 = ps2.executeQuery();

            while(rs2.next()){

                Patente p = new Patente();
                p.setId(rs2.getInt("id"));
                p.setNome(rs2.getString("nome"));
                p.setCor(rs2.getString("cor"));

                j.setPatente(p);
            }

            
            lista.add(j);            
        
        }                
        return lista;
        
    }
    
    @Override
    public List<Patente> listPatentes() throws Exception {
        

        List<Patente> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select id, cor, nome from tb_patente order by id asc");
        
        ResultSet rs = ps.executeQuery();//executa a query        

        lista = new ArrayList();
        while(rs.next()){
            
            Patente p = new Patente();
            p.setId(rs.getInt("id"));
            p.setCor(rs.getString("cor"));
            p.setNome(rs.getString("nome"));
            
            lista.add(p);
            
        }
        
        rs.close();
        
        return lista;
        
    }
    
}
