/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cs.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cs.gui.autenticacao.JPanelAutenticacao;
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
    
        
        //inicia a interface gráfica.
        //"caminho feliz" : passo 5
        
        frame = new JFramePrincipal(this);
        
        telaAutenticacao = new JPanelAutenticacao(this);// inicializa
        
        frame.addTela(telaAutenticacao, "tela_autenticacao"); //adiciona
        
        menuBar = new JMenuBarHome(this);
        
        telaHome = new JPanelHome(this);
        
        
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
    
}
