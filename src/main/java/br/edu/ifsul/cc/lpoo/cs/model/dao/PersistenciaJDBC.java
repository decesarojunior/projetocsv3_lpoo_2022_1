
package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Arma;
import br.edu.ifsul.cc.lpoo.cs.model.Artefato;
import br.edu.ifsul.cc.lpoo.cs.model.Calibre;
import br.edu.ifsul.cc.lpoo.cs.model.Compra;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Municao;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import br.edu.ifsul.cc.lpoo.cs.model.Tipo;
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
                    
                    
                    PreparedStatement ps3 = this.con.prepareStatement("select a.id, a.nome, a.peso, a.valor, a.tipo from tb_jogador_artefato ja, tb_artefato a where a.id=ja.artefato_id and ja.jogador_nickname = ? ");
                    ps3.setString(1, id.toString());
                    
                    ResultSet rs3 = ps3.executeQuery();
                    
                    while(rs3.next()){
                                                
                        
                        if(rs3.getString("tipo").equals("A")){
                            
                            Arma a = new Arma();
                            a.setId(rs3.getInt("id"));
                            a.setNome(rs3.getString("nome"));
                            a.setValor(rs3.getFloat("valor"));
                            a.setTipoArtefato("A");

                            PreparedStatement  ps4 = this.con.prepareStatement("select comprimento_cano, tipo from tb_arma where id = ?");

                            ps4.setInt(1, a.getId());

                            ResultSet rs4 = ps4.executeQuery();

                            if(rs4.next()){

                                a.setComprimento_cano(rs4.getFloat("comprimento_cano"));
                                if(rs4.getString("tipo").equals(Tipo.FOGO)){
                                    a.setTipo(Tipo.FOGO);
                                }else if(rs4.getString("tipo").equals(Tipo.BRANCA)){
                                    a.setTipo(Tipo.BRANCA);
                                }                        
                            }
                            rs4.close();

                            //adiciona a Arma na lista de armas do jogador.
                            j.setArtefato(a);

                        }else if(rs3.getString("tipo").equals("M")){
                            
                            Municao m = new Municao();
                            m.setId(rs3.getInt("id"));
                            m.setNome(rs3.getString("nome"));
                            m.setValor(rs3.getFloat("valor"));
                            m.setTipoArtefato("M");

                            PreparedStatement  ps4 = this.con.prepareStatement("select calibre, explosiva from tb_municao where id = ?");

                            ps4.setInt(1, m.getId());

                            ResultSet rs4 = ps4.executeQuery();

                            if(rs2.next()){

                                m.setExplosiva(rs4.getBoolean("explosiva"));
                                if(rs4.getString("calibre").equals(Calibre.C03)){
                                    m.setCalibre(Calibre.C03);
                                }else if(rs4.getString("calibre").equals(Calibre.C05)){
                                    m.setCalibre(Calibre.C05);
                                }else if(rs4.getString("calibre").equals(Calibre.C08)){
                                    m.setCalibre(Calibre.C08);
                                }                        
                            }
                            rs4.close();

                            j.setArtefato(m);
                        }
                                                
                        
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
            
        }else if(c == Artefato.class){
            
            PreparedStatement ps = this.con.prepareStatement("select id, nome, peso, valor, tipo from tb_artefato where id = ?");
            
            ps.setInt(1, Integer.parseInt(id.toString()));
                                                           
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                
                if(rs.getString("tipo").equals("A")){
                    Arma a = new Arma();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setValor(rs.getFloat("valor"));
                    a.setTipoArtefato("A");
                    
                    PreparedStatement  ps2 = this.con.prepareStatement("select comprimento_cano, tipo from tb_arma where id = ?");
            
                    ps2.setInt(1, Integer.parseInt(id.toString()));

                    ResultSet rs2 = ps2.executeQuery();

                    if(rs2.next()){
                        
                        a.setComprimento_cano(rs2.getFloat("comprimento_cano"));
                        if(rs2.getString("tipo").equals(Tipo.FOGO)){
                            a.setTipo(Tipo.FOGO);
                        }else if(rs2.getString("tipo").equals(Tipo.BRANCA)){
                            a.setTipo(Tipo.BRANCA);
                        }                        
                    }
                    rs2.close();
                    
                    return a;
                    
                }else if(rs.getString("tipo").equals("M")){
                    Municao m = new Municao();
                    m.setId(rs.getInt("id"));
                    m.setNome(rs.getString("nome"));
                    m.setValor(rs.getFloat("valor"));
                    m.setTipoArtefato("M");
                    
                    PreparedStatement  ps2 = this.con.prepareStatement("select calibre, explosiva from tb_municao where id = ?");
            
                    ps2.setInt(1, Integer.parseInt(id.toString()));

                    ResultSet rs2 = ps2.executeQuery();

                    if(rs2.next()){
                        
                        m.setExplosiva(rs2.getBoolean("explosiva"));
                        if(rs2.getString("calibre").equals(Calibre.C03)){
                            m.setCalibre(Calibre.C03);
                        }else if(rs2.getString("calibre").equals(Calibre.C05)){
                            m.setCalibre(Calibre.C05);
                        }else if(rs2.getString("calibre").equals(Calibre.C08)){
                            m.setCalibre(Calibre.C08);
                        }                        
                    }
                    rs2.close();
                    
                    return m;
                }
                                               
            }  
            
            ps.close();
            
            
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
                
                //verifica se precisa persistir os artefatos do jogador.
                
                if(j.getArtefatos() != null && !j.getArtefatos().isEmpty()){

                    for(Artefato a : j.getArtefatos()){

                         PreparedStatement ps2 = this.con.prepareStatement("insert into tb_jogador_artefato "
                                                                                    + "(jogador_nickname, artefato_id) values "
                                                                                    + "(?, ? ) ");

                         ps2.setString(1, j.getNickname());
                         ps2.setInt(2, a.getId());

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
                ps2.execute();
                                                
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
                
                
                //verifica se precisa persistir os artefatos do jogador.
                PreparedStatement ps4 = this.con.prepareStatement("delete from tb_jogador_artefato where jogador_nickname = ? ");
                ps4.setString(1, j.getNickname());
                ps4.execute();
                                
                if(j.getArtefatos() != null && !j.getArtefatos().isEmpty()){

                    for(Artefato a : j.getArtefatos()){

                         PreparedStatement ps5 = this.con.prepareStatement("insert into tb_jogador_artefato "
                                                                                    + "(jogador_nickname, artefato_id) values "
                                                                                    + "(?, ? ) ");

                         ps5.setString(1, j.getNickname());
                         ps5.setInt(2, a.getId());

                         ps5.execute();

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
                
        }else if (o instanceof Artefato){
            
            
            Artefato a = (Artefato) o; //converter o para o e que é do tipo Endereco
            if(a.getId() == null){ 
                
                PreparedStatement ps = 
                        this.con.prepareStatement(
                                "insert into tb_artefato "
                                + "(id, nome, peso, valor, tipo) values "
                                + "(nextval('seq_artefato_id'), ?, ?, ?, ?) "
                                + "returning id");
                
                ps.setString(1, a.getNome());
                ps.setFloat(2, a.getPeso());
                ps.setFloat(3, a.getValor());
                ps.setString(4, a.getTipoArtefato());
                
                //executa o comando e recupera o retorno. 
                ResultSet rs = ps.executeQuery();                                 
                
                if (rs.next()) {
                    //seta no objeto e, para disponibilizar o acesso ao id gerado.
                    a.setId(rs.getInt(1));
                }
                
                ps.close();
                
                if(a.getTipoArtefato().equals("A")){
                    
                    PreparedStatement ps2 = 
                        this.con.prepareStatement(
                                "insert into tb_arma "
                                + "(id, tipo, comprimento_cano) values "
                                + "(?,?,?) ");
                    ps2.setInt(1, a.getId());
                    ps2.setString(2, ( (Arma) a ).getTipo().toString());
                    ps2.setFloat(3, ( (Arma) a ).getComprimento_cano());
                    ps2.execute();
                    
                    ps2.close();
                    
                }else if(a.getTipoArtefato().equals("M")){
                    
                    PreparedStatement ps2 = 
                        this.con.prepareStatement(
                                "insert into tb_municao "
                                + "(id, explosiva, calibre) values "
                                + "(?,?,?) ");
                    ps2.setInt(1, a.getId());
                    ps2.setBoolean(2, ( (Municao) a ).getExplosiva());
                    ps2.setString(3, ( (Municao) a ).getCalibre().toString());
                    ps2.execute();
                    
                    ps2.close();
                    
                }                
                
            }else{
                
                //update.
                
                PreparedStatement ps = 
                        this.con.prepareStatement(
                                "udpate tb_artefato set "
                                + "nome = ? , peso = ?, valor = ? where id = ? ");
                
                ps.setString(1, a.getNome());
                ps.setFloat(2, a.getPeso());
                ps.setFloat(3, a.getValor());
                ps.setInt(4, a.getId());
                
                ps.execute();
                
                ps.close();
                
                if(a.getTipoArtefato().equals("A")){
                    
                    PreparedStatement ps2 = 
                        this.con.prepareStatement(
                                "update tb_arma set "
                                + "tipo = ? , comprimento_cano = ?  where "
                                + "id = ? ");
                    
                    ps2.setString(1, ( (Arma) a ).getTipo().toString());
                    ps2.setFloat(2, ( (Arma) a ).getComprimento_cano());
                    ps2.setInt(3, a.getId());
                    ps2.execute();
                    
                    ps2.close();
                    
                }else if(a.getTipoArtefato().equals("M")){
                    
                    PreparedStatement ps2 = 
                        this.con.prepareStatement(
                                "update tb_municao set "
                                + "explosiva = ?, calibre = ? where "
                                + "id = ? ");
                    ps2.setBoolean(1, ( (Municao) a ).getExplosiva());
                    ps2.setString(2, ( (Municao) a ).getCalibre().toString());
                    ps2.setInt(3, a.getId());                    
                    ps2.execute();
                    
                    ps2.close();
                    
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
            
            ps = this.con.prepareStatement("delete from tb_jogador_artefato where jogador_nickname = ?");
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
       Jogador jogador = null;
        
         PreparedStatement ps = 
            this.con.prepareStatement("select j.nickname, j.senha from tb_jogador j where j.nickname= ? and j.senha = ? ");
                        
            ps.setString(1, nickname);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();//o ponteiro do REsultSet inicialmente está na linha -1
            
            if(rs.next()){//se a matriz (ResultSet) tem uma linha

                jogador = new Jogador();
                jogador.setNickname(rs.getString("nickname"));                
            }
        
            ps.close();
            return jogador;   
    }

    @Override
    public List<Endereco> listEnderecos() throws Exception {
         List<Endereco> lista = null;
                        
        PreparedStatement ps = this.con.prepareStatement("select id, cep, complemento from tb_endereco order by id asc");
        
        ResultSet rs = ps.executeQuery();//executa a query        
        /*                                     
                  8 | 99052250 | 110
            next->9 | 990010   | 1            
        */        
        lista = new ArrayList();
        while(rs.next()){
            
            Endereco end = new Endereco();
            end.setId(rs.getInt("id"));
            end.setCep(rs.getString("cep"));
            end.setComplemento(rs.getString("complemento"));
            
            lista.add(end);//adiciona na lista o objetivo que contem as informações de um determinada linha do ResultSet.
            
        }                
        return lista;
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
            
            PreparedStatement ps3 = this.con.prepareStatement("select a.id, a.nome, a.peso, a.valor, a.tipo from tb_jogador_artefato ja, tb_artefato a where a.id=ja.artefato_id and ja.jogador_nickname = ? ");
            ps3.setString(1, j.getNickname());

            ResultSet rs3 = ps3.executeQuery();

            while(rs3.next()){


                if(rs3.getString("tipo").equals("A")){

                    Arma a = new Arma();
                    a.setId(rs3.getInt("id"));
                    a.setNome(rs3.getString("nome"));
                    a.setValor(rs3.getFloat("valor"));
                    a.setTipoArtefato("A");

                    PreparedStatement  ps4 = this.con.prepareStatement("select comprimento_cano, tipo from tb_arma where id = ?");

                    ps4.setInt(1, a.getId());

                    ResultSet rs4 = ps4.executeQuery();

                    if(rs4.next()){

                        a.setComprimento_cano(rs4.getFloat("comprimento_cano"));
                        if(rs4.getString("tipo").equals(Tipo.FOGO)){
                            a.setTipo(Tipo.FOGO);
                        }else if(rs4.getString("tipo").equals(Tipo.BRANCA)){
                            a.setTipo(Tipo.BRANCA);
                        }                        
                    }
                    rs4.close();

                    //adiciona a Arma na lista de armas do jogador.
                    j.setArtefato(a);

                }else if(rs3.getString("tipo").equals("M")){

                    Municao m = new Municao();
                    m.setId(rs3.getInt("id"));
                    m.setNome(rs3.getString("nome"));
                    m.setValor(rs3.getFloat("valor"));
                    m.setTipoArtefato("M");

                    PreparedStatement  ps4 = this.con.prepareStatement("select calibre, explosiva from tb_municao where id = ?");

                    ps4.setInt(1, m.getId());

                    ResultSet rs4 = ps4.executeQuery();

                    if(rs2.next()){

                        m.setExplosiva(rs4.getBoolean("explosiva"));
                        if(rs4.getString("calibre").equals(Calibre.C03)){
                            m.setCalibre(Calibre.C03);
                        }else if(rs4.getString("calibre").equals(Calibre.C05)){
                            m.setCalibre(Calibre.C05);
                        }else if(rs4.getString("calibre").equals(Calibre.C08)){
                            m.setCalibre(Calibre.C08);
                        }                        
                    }
                    rs4.close();

                    j.setArtefato(m);
                }


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

    @Override
    public List<Compra> listCompras() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Partida> listPartidas() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
