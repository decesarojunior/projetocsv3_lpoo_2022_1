/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;

/**
 *
 * @author telmo
 */
public class Controle {
    
    private PersistenciaJDBC conexaoJDBC;
    
    private JPanelAutenticacao telaAutenticacao; //tela de autentiacao.
    
    private JFramePrincipal frame; // frame principal da minha aplicação gráfica

    
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
        
        frame.showTela("tela_autenticacao");   //mostra
        
        frame.setVisible(true); // torna visível o jframe
        
    }
    
        /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }

    public void autenticar(String trim, String trim0) {
        //  implementar o metodo doLogin da classe PersistenciaJDBC
        //  chamar o doLogin e verificar o retorno.
        // se o retorno for nulo, informar ao usuário
        //se nao for nulo, apresentar a tela de boas vindas e o menu.
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
