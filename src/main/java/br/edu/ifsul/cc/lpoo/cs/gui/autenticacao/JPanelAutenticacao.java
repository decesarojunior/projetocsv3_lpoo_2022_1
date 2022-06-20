
package br.edu.ifsul.cc.lpoo.cs.gui.autenticacao;

import br.edu.ifsul.cc.lpoo.cs.Controle;
import br.edu.ifsul.cc.lpoo.cs.util.Util;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author telmo
 */
public class JPanelAutenticacao extends JPanel implements ActionListener {
    
    private Controle controle;
    private GridBagLayout gridLayout;
    private GridBagConstraints posicionador;
    
    private JLabel  lblNickname;
    private JLabel lblSenha;
    private JTextField txfNickname;
    private JPasswordField psfSenha;
    private JButton btnLogar;
    private Border defaultBorder;
    
    //construtor da classe que recebe um parametro.
    public JPanelAutenticacao(Controle controle){
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents(){
    
        gridLayout = new GridBagLayout();//inicializando o gerenciador de layout
        this.setLayout(gridLayout);//definie o gerenciador para este painel.
        
        lblNickname = new JLabel("Nickname:");        
        lblNickname.setToolTipText("lblNickname"); //acessibilidade
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        this.add(lblNickname, posicionador);//o add adiciona o rotulo no painel
        
        txfNickname = new JTextField(10);
        txfNickname.setFocusable(true);    //acessibilidade    
        txfNickname.setToolTipText("txfNickname"); //acessibilidade
        Util.considerarEnterComoTab(txfNickname);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        defaultBorder = txfNickname.getBorder();
        this.add(txfNickname, posicionador);//o add adiciona o rotulo no painel        
        
        lblSenha = new JLabel("Senha:");        
        lblSenha.setToolTipText("lblSenha"); //acessibilidade        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        this.add(lblSenha, posicionador);//o add adiciona o rotulo no painel
        
        psfSenha = new JPasswordField(10);
        psfSenha.setFocusable(true);    //acessibilidade    
        psfSenha.setToolTipText("psfSenha"); //acessibilidade  
        Util.considerarEnterComoTab(psfSenha);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        this.add(psfSenha, posicionador);//o add adiciona o rotulo no painel  

        btnLogar = new JButton("Autenticar");
        btnLogar.setFocusable(true);    //acessibilidade    
        btnLogar.setToolTipText("btnLogar"); //acessibilidade  
        Util.registraEnterNoBotao(btnLogar);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        btnLogar.addActionListener(this);//registrar o botão no Listener
        btnLogar.setActionCommand("comando_autenticar");
        this.add(btnLogar, posicionador);//o add adiciona o rotulo no painel
                

    }

    public void requestFocus(){
        
        txfNickname.requestFocus();
    }
    
    public void cleanForm(){
        
        txfNickname.setText("");
        psfSenha.setText("");        
        
        txfNickname.setBorder(defaultBorder);        
        psfSenha.setBorder(defaultBorder);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
                
            //testa para verificar se o botão btnLogar foi clicado.
            if(e.getActionCommand().equals(btnLogar.getActionCommand())){
            
                //validacao do formulario.
                if(txfNickname.getText().trim().length() > 4){
                                        
                    txfNickname.setBorder(new LineBorder(Color.green,1));
                    
                    if(new String(psfSenha.getPassword()).trim().length() > 3 ){
                                                
                        psfSenha.setBorder(new LineBorder(Color.green,1));
                        
                        controle.autenticar(txfNickname.getText().trim(), new String(psfSenha.getPassword()).trim());
                        
                    }else {
                        
                        JOptionPane.showMessageDialog(this, "Informe Senha com 4 ou mais dígitos", "Autenticação", JOptionPane.ERROR_MESSAGE);
                        psfSenha.setBorder(new LineBorder(Color.red,1));
                        psfSenha.requestFocus();                        
                        
                    }
                    
                }else{
                
                    JOptionPane.showMessageDialog(this, "Informe Nickname com 4 ou mais dígitos", "Autenticação", JOptionPane.ERROR_MESSAGE);                    
                    txfNickname.setBorder(new LineBorder(Color.red,1));
                    txfNickname.requestFocus();
                }
                                      
            
        } 
        
    }
    
}
