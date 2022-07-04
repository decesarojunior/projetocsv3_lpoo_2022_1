
package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cs.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cs.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cs.gui.jogador.JPanelAJogador;
import br.edu.ifsul.cc.lpoo.cs.gui.jogador.designer.JPanelDJogador;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import javax.swing.JOptionPane;

/**
 *
 * @author telmo
 */
public class Controle {
    
    private PersistenciaJDBC conexaoJDBC;
    
    private JPanelAutenticacao telaAutenticacao; //tela de autentiacao.
    
    private JFramePrincipal frame; // frame principal da minha aplicação gráfica

    private JMenuBarHome menuBar;
    
    private JPanelHome  telaHome;
    
    private JPanelAJogador telaJogador;
    
    private JPanelDJogador telaJogadorDesigner;
    
    
    public Controle(){
                        
    }
    
    public boolean conectarBD() throws Exception {

            conexaoJDBC = new PersistenciaJDBC();

            if(getConexaoJDBC()!= null){

                        return getConexaoJDBC().conexaoAberta();
            }

            return false;
    }
    
    public void initComponents(){
        
        //para cada nova tela de CRUD inicializar o respectivo objeto
        
        frame = new JFramePrincipal(this);
        
        telaAutenticacao = new JPanelAutenticacao(this);// inicializa
        
        frame.addTela(telaAutenticacao, "tela_autenticacao"); //adiciona
        
        menuBar = new JMenuBarHome(this);
        
        telaHome = new JPanelHome(this);
        
        telaJogador = new JPanelAJogador(this);
        
        telaJogadorDesigner = new JPanelDJogador(this);
        
        frame.addTela(telaJogadorDesigner, "tela_jogador_designer");
        
        frame.addTela(telaJogador, "tela_jogador");
        
        frame.addTela(telaHome, "tela_home"); //adiciona
        
        frame.showTela("tela_autenticacao");   //mostra
        
        
        frame.setVisible(true); // torna visível o jframe
        
    }
    
        /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }

    public void autenticar(String cpf, String senha) {
        //  implementar o metodo doLogin da classe PersistenciaJDBC
        //  chamar o doLogin e verificar o retorno.
        // se o retorno for nulo, informar ao usuário
        //se nao for nulo, apresentar a tela de boas vindas e o menu.
        try{

            Jogador j =  conexaoJDBC.doLogin(cpf, senha);
            
            if(j != null){

                JOptionPane.showMessageDialog(telaAutenticacao, "Jogador "+j.getNickname()+" autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

                frame.setJMenuBar(menuBar);//adiciona o menu de barra no frame
                frame.showTela("tela_home");//muda a tela para o painel de boas vindas (home)

            }else{

                JOptionPane.showMessageDialog(telaAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        }catch(Exception e){

            JOptionPane.showMessageDialog(telaAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showTela(String nomeTela){
         
        //para cada nova tela de CRUD adicionar um elseif
        
         if(nomeTela.equals("tela_jogador")){
            telaJogador.showTela("tela_jogador_listagem");
            frame.showTela(nomeTela);
            
         }else if(nomeTela.endsWith("tela_jogador_designer")){
             
             telaJogadorDesigner.showTela("listagem");
              frame.showTela(nomeTela);
         }
         
    }
     
    public void fecharBD(){

        System.out.println("Fechando conexao com o Banco de Dados");
        getConexaoJDBC().fecharConexao();

    }
    
}
