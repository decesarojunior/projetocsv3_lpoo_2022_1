
package br.edu.ifsul.cc.lpoo.cs.gui;

import br.edu.ifsul.cc.lpoo.cs.Controle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author telmo
 */
public class JMenuBarHome extends JMenuBar implements ActionListener {
    
    private JMenu menuArquivo;
    private JMenuItem menuItemLogout;
    private JMenuItem menuItemSair;

    private JMenu menuCadastro;
    private JMenuItem menuItemJogador;    
    private JMenuItem menuItemJogadorDesigner;    

    private Controle controle;
    
    public JMenuBarHome(Controle controle){
        
        this.controle = controle;        
        
        initComponents();
    }
    
    private void initComponents(){
        
        menuArquivo = new JMenu("Arquivo");
        menuArquivo.setMnemonic(KeyEvent.VK_A);//ativa o ALT + A para acessar esse menu - acessibilidade
        menuArquivo.setToolTipText("Arquivo"); //acessibilidade
        menuArquivo.setFocusable(true); //acessibilidade
                
        menuItemSair = new JMenuItem("Sair");
        menuItemSair.setToolTipText("Sair"); //acessibilidade
        menuItemSair.setFocusable(true);     //acessibilidade
        
        menuItemLogout = new JMenuItem("Logout");
        menuItemLogout.setToolTipText("Logout"); //acessibilidade
        menuItemLogout.setFocusable(true);     //acessibilidade
        
        menuItemLogout.addActionListener(this);
        menuItemLogout.setActionCommand("menu_logout");
        menuArquivo.add(menuItemLogout);

        menuItemSair.addActionListener(this);
        menuItemSair.setActionCommand("menu_sair");
        menuArquivo.add(menuItemSair);

        menuCadastro = new JMenu("Cadastros");
        menuCadastro.setMnemonic(KeyEvent.VK_C);//ativa o ALT + C para acessar esse menu - acessibilidade
        menuCadastro.setToolTipText("Cadastro"); //acessibilidade
        menuCadastro.setFocusable(true); //acessibilidade
        
        menuItemJogador = new JMenuItem("Jogador");
        menuItemJogador.setToolTipText("Jogador"); //acessibilidade
        menuItemJogador.setFocusable(true); //acessibilidade

        menuItemJogador.addActionListener(this);
        menuItemJogador.setActionCommand("menu_jogador");
        menuCadastro.add(menuItemJogador);  
        
        menuItemJogadorDesigner = new JMenuItem("Jogador (Designer)");
        menuItemJogadorDesigner.setToolTipText("Jogador (Desinger)"); //acessibilidade
        menuItemJogadorDesigner.setFocusable(true); //acessibilidade
        
        
        menuItemJogadorDesigner.addActionListener(this);
        menuItemJogadorDesigner.setActionCommand("menu_jogador_designer");
        menuCadastro.add(menuItemJogadorDesigner);  
                        

        this.add(menuArquivo);
        this.add(menuCadastro);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals(menuItemSair.getActionCommand())){
        
            //se o usuario clicou no menuitem Sair
            int d = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema? ", "Sair", JOptionPane.YES_NO_OPTION);
            if(d == 0){                
                //->controle.fecharBD();//fecha a conexao com o banco de dados.
                System.exit(0);//finaliza o processo do programa.
            }
            
            
        }else if(e.getActionCommand().equals(menuItemJogador.getActionCommand())){
            
                        //se o usuario clicou no menuitem Usuario            
                        controle.showTela("tela_jogador");          
                        
        }else if(e.getActionCommand().equals(menuItemLogout.getActionCommand())){
            
                        //->controle.showTela("tela_autenticacao");    
                        
        }else if(e.getActionCommand().equals(menuItemJogadorDesigner.getActionCommand())){
            
                        controle.showTela("tela_jogador_designer");
        }
        
    }
    
    
}
