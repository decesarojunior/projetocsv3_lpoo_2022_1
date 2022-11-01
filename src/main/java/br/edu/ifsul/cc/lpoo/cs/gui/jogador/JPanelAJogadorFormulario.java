package br.edu.ifsul.cc.lpoo.cs.gui.jogador;

import br.edu.ifsul.cc.lpoo.cs.Controle;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author telmo
 */
public class JPanelAJogadorFormulario extends JPanel implements ActionListener{
    
    
    private JPanelAJogador pnlAJogador;
    private Controle controle;
    
    private BorderLayout borderLayout;
    private JTabbedPane tbpAbas;
    
    private JPanel pnlDadosCadastrais;    
    private JPanel pnlCentroDadosCadastrais;
    
    private GridBagLayout gridBagLayoutDadosCadastrais;
    private JLabel lblNickname;
    private JTextField txfNickname;
    
    private JLabel lblSenha;
    private JPasswordField txfSenha;
    
    private JLabel lblPontos;
    private JTextField txfPontos;
    
    private JLabel lblEndereco;
    private JComboBox cbxEndereco;
    
    private JLabel lblDataUltimoLogin;
    private JTextField txfDataUltimoLogin;
        
    private JLabel lblDataCadastro;
    private JTextField txfDataCadastro;
    
    private Jogador jogador;
    private SimpleDateFormat format;
    
    private JPanel pnlSul;
    private JButton btnGravar;
    private JButton btnCancelar;
    
    private JPanel pnlDadosCompras;
    private JPanel pnlDadosArtefatos;                
    private JPanel pnlDadosPatentes;
    private JScrollPane scpListagemPatente;
    private JTable tblListagemPatente;
    private JComboBox cbxPatente;
    private JButton btnAdicionarPatente;
    private JButton btnRemoverPatente;
    private DefaultTableModel modeloTabelaPatente;
    private JLabel lblPatenteAdicionar;

    
    
    public JPanelAJogadorFormulario(JPanelAJogador pnlAJogador, Controle controle){
        
        this.pnlAJogador = pnlAJogador;
        this.controle = controle;
        
        initComponents();
        
    }
    
    public void populaComboEndereco(){
        
        cbxEndereco.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxEndereco.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Endereco> listCidades = controle.getConexaoJDBC().listEnderecos();
            for(Endereco e : listCidades){
                model.addElement(e);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Enderecos -:"+ex.getLocalizedMessage(), "Endereços", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }  
        
        
    }
    
    public void populaComboPatente(){
        
        cbxPatente.removeAllItems();//zera o combo

        DefaultComboBoxModel model =  (DefaultComboBoxModel) cbxPatente.getModel();

        model.addElement("Selecione"); //primeiro item        
        try {

            List<Patente> listPatentes = controle.getConexaoJDBC().listPatentes();
            for(Patente p : listPatentes){
                model.addElement(p);
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this, "Erro ao listar Patentes -:"+ex.getLocalizedMessage(), "Patentes", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }  
        
        
    }
    
    
    public Jogador getJogadorbyFormulario(){
        
        //validacao do formulario
         if(txfNickname.getText().trim().length() > 4 && 
            new String(txfSenha.getPassword()).trim().length() > 3 && 
            cbxEndereco.getSelectedIndex() > 0){

            Jogador j = new Jogador();
            j.setNickname(txfNickname.getText().trim());            
            j.setSenha(new String(txfSenha.getPassword()).trim());
            j.setEndereco((Endereco) cbxEndereco.getSelectedItem());
            j.setPontos(Integer.parseInt(txfPontos.getText().trim()));
                        
            if(jogador != null)
                j.setData_cadastro(jogador.getData_cadastro());
            
            if(jogador != null)
                j.setData_ultimo_login(jogador.getData_ultimo_login());
            
            
            DefaultTableModel model =  (DefaultTableModel) tblListagemPatente.getModel();//recuperacao do modelo da tabela
            
            for(Vector<Vector> linha : model.getDataVector()){
                
                Vector v = (Vector) linha; //model.addRow(new Object[]{u, u.getNome(), ...

                Patente p = (Patente) v.get(1);
                
                System.out.println("Add patente no jogador ...");  
                        
                j.setPatente(p);
                
            }
            
        
            
            return j;
         }

         return null;
    }
    
    public void setJogadorFormulario(Jogador j){

        if(j == null){//se o parametro estiver nullo, limpa o formulario
            txfNickname.setText("");
            txfSenha.setText("");
            cbxEndereco.setSelectedIndex(0);
            txfPontos.setText("");
            txfDataCadastro.setText("");
            txfDataUltimoLogin.setText("");
            txfNickname.setEditable(true);
            jogador = null;
            
            //limpa a tabela das patentes do jogador.
            DefaultTableModel model =  (DefaultTableModel) tblListagemPatente.getModel();//recuperacao do modelo da tabela
            model.setRowCount(0);
            

        }else{

            jogador = j;
            txfNickname.setEditable(false);
            txfNickname.setText(jogador.getNickname());
            txfSenha.setText(jogador.getSenha());
            cbxEndereco.getModel().setSelectedItem(jogador.getEndereco());//aqui chama o método equals do classe Endereco
            txfPontos.setText(jogador.getPontos().toString());
            txfDataCadastro.setText(format.format(j.getData_cadastro().getTime()));
            if(j.getData_ultimo_login() != null)
                txfDataUltimoLogin.setText(format.format(j.getData_ultimo_login().getTime()));

            //gera linhas na tabela para listar as patentes de um determinado jogador.
            
            
            if(jogador.getPatentes() != null){
                
                DefaultTableModel model =  (DefaultTableModel) tblListagemPatente.getModel();//recuperacao do modelo da tabela

                model.setRowCount(0);//elimina as linhas existentes (reset na tabela)
                
                for(Patente p : jogador.getPatentes()){
                    
                    model.addRow(new Object[]{p.getId(), p});
        
                }
                
            }else{
                
                DefaultTableModel model =  (DefaultTableModel) tblListagemPatente.getModel();//recuperacao do modelo da tabela

                model.setRowCount(0);//elimina as linhas existentes (reset na tabela)
            }
                        
        }

    }
    
    private void initComponents(){
        
        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        
        tbpAbas = new JTabbedPane();
        this.add(tbpAbas, BorderLayout.CENTER);
        
        pnlDadosCadastrais = new JPanel();
        gridBagLayoutDadosCadastrais = new GridBagLayout();
        pnlDadosCadastrais.setLayout(gridBagLayoutDadosCadastrais);
        
        lblNickname = new JLabel("Nickname:");
        GridBagConstraints posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblNickname, posicionador);//o add adiciona o rotulo no painel  
        
        txfNickname = new JTextField(20);        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfNickname, posicionador);//o add adiciona o rotulo no painel  
                
        lblSenha = new JLabel("Senha:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblSenha, posicionador);//o add adiciona o rotulo no painel  
        
        txfSenha = new JPasswordField(10);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfSenha, posicionador);//o add adiciona o rotulo no painel  
                
        lblPontos = new JLabel("Quantidade de Pontos:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblPontos, posicionador);//o add adiciona o rotulo no painel  
                
        txfPontos = new JTextField(5);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(txfPontos, posicionador);//o add adiciona o rotulo no painel  
            
        lblEndereco = new JLabel("Endereço:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(lblEndereco, posicionador);//o add adiciona o rotulo no painel  
                
        cbxEndereco = new JComboBox();
        posicionador = new GridBagConstraints();
        posicionador.gridy = 3;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        posicionador.anchor = java.awt.GridBagConstraints.LINE_START;//ancoragem a esquerda.
        pnlDadosCadastrais.add(cbxEndereco, posicionador);//o add adiciona o rotulo no painel 
                
        lblDataUltimoLogin = new JLabel("Data último login:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblDataUltimoLogin, posicionador);//o add adiciona o rotulo no painel 
                
        txfDataUltimoLogin = new JTextField(20);
        txfDataUltimoLogin.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 4;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)        
        pnlDadosCadastrais.add(txfDataUltimoLogin, posicionador);//o add adiciona o rotulo no painel 
                        
        lblDataCadastro = new JLabel("Data de Cadastro:");
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(lblDataCadastro, posicionador);//o add adiciona o rotulo no painel         
        
        txfDataCadastro = new JTextField(20);
        txfDataCadastro.setEditable(false);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 5;//policao da linha (vertical)
        posicionador.gridx = 1;// posição da coluna (horizontal)
        pnlDadosCadastrais.add(txfDataCadastro, posicionador);//o add adiciona o rotulo no painel         
        
        tbpAbas.addTab("Dados Cadastrais", pnlDadosCadastrais);
                        
        pnlDadosPatentes = new JPanel();
        
                        
        pnlDadosPatentes.setLayout(new GridBagLayout() );
        
        scpListagemPatente = new JScrollPane();
        tblListagemPatente = new JTable();
        
        modeloTabelaPatente = new DefaultTableModel(
            new String [] {
                "ID", "Nome"
            }, 0);
        
        tblListagemPatente.setModel(modeloTabelaPatente);
        
        scpListagemPatente.setViewportView(tblListagemPatente);
        
        cbxPatente = new JComboBox();
        lblPatenteAdicionar = new JLabel("Escolha a Patente para adicionar:");
        btnAdicionarPatente = new JButton("Adicionar");
        
        btnRemoverPatente = new JButton("Remover");
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosPatentes.add(scpListagemPatente, posicionador);//o add adiciona o rotulo no painel
        
        JPanel pnlLinha = new JPanel();
        pnlLinha.setLayout(new FlowLayout());
        pnlLinha.add(lblPatenteAdicionar);
        pnlLinha.add(cbxPatente);
        
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosPatentes.add(pnlLinha, posicionador);//o add adiciona o rotulo no painel
        
        
        btnAdicionarPatente.addActionListener(this);
        btnAdicionarPatente.setActionCommand("botao_adicionar_patente_formulario_jogador");
  
    
        btnRemoverPatente.addActionListener(this);
        btnRemoverPatente.setActionCommand("botao_remover_patente_formulario_jogador");
  
        
        JPanel pnlLinhaB = new JPanel();
        pnlLinhaB.setLayout(new FlowLayout());
        pnlLinhaB.add(btnAdicionarPatente);
        pnlLinhaB.add(btnRemoverPatente);
        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2;//policao da linha (vertical)
        posicionador.gridx = 0;// posição da coluna (horizontal)
        pnlDadosPatentes.add(pnlLinhaB, posicionador);//o add adiciona o rotulo no painel
   
        
        
        tbpAbas.addTab("Patentes", pnlDadosPatentes);
        
        
        pnlDadosCompras = new JPanel();
        tbpAbas.addTab("Compras", pnlDadosCompras);
        
        
        pnlDadosArtefatos = new JPanel();                
        tbpAbas.addTab("Artefatos", pnlDadosArtefatos);
        
        
        pnlSul = new JPanel();
        pnlSul.setLayout(new FlowLayout());
        
        btnGravar = new JButton("Gravar");
        btnGravar.addActionListener(this);
        btnGravar.setFocusable(true);    //acessibilidade    
        btnGravar.setToolTipText("btnGravarJogador"); //acessibilidade
        btnGravar.setMnemonic(KeyEvent.VK_G);
        btnGravar.setActionCommand("botao_gravar_formulario_jogador");
        
        pnlSul.add(btnGravar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setFocusable(true);    //acessibilidade    
        btnCancelar.setToolTipText("btnCancelarJogador"); //acessibilidade
        btnCancelar.setActionCommand("botao_cancelar_formulario_jogador");
        
        pnlSul.add(btnCancelar);
        
        this.add(pnlSul, BorderLayout.SOUTH);
        
        format = new SimpleDateFormat("dd/MM/yyyy");
        
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        
        if(arg0.getActionCommand().equals(btnGravar.getActionCommand())){
            
            Jogador j = getJogadorbyFormulario();//recupera os dados do formulÃ¡rio
            
            if(j != null){

                try {
                    
                    pnlAJogador.getControle().getConexaoJDBC().persist(j);
                    
                    JOptionPane.showMessageDialog(this, "Jogador armazenado!", "Salvar", JOptionPane.INFORMATION_MESSAGE);
                    
                    pnlAJogador.showTela("tela_jogador_listagem");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar Jogador! : "+ex.getMessage(), "Salvar", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            }else{

                JOptionPane.showMessageDialog(this, "Preencha o formulário!", "Edição", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        }else if(arg0.getActionCommand().equals(btnCancelar.getActionCommand())){
            
            
                pnlAJogador.showTela("tela_jogador_listagem");
            
        }else if(arg0.getActionCommand().equals(btnAdicionarPatente.getActionCommand())){
            
            //adiciona uma patente na lista de patentes do jogador (jtable)
            if(cbxPatente.getSelectedIndex() > 0){
                
                DefaultTableModel model =  (DefaultTableModel) tblListagemPatente.getModel();//recuperacao do modelo da tabela

                Patente  p =  (Patente) cbxPatente.getSelectedItem();
            
                model.addRow(new Object[] {p.getId(), p});
                
            }else{
                
                JOptionPane.showMessageDialog(this, "Selecione uma Patente para adicionar !!", "Patentes do Jogador", JOptionPane.INFORMATION_MESSAGE);
            }

              
        }else if(arg0.getActionCommand().equals(btnRemoverPatente.getActionCommand())){
            
            int indice = tblListagemPatente.getSelectedRow();//recupera a linha selecionada
            if(indice > -1){

                DefaultTableModel model =  (DefaultTableModel) tblListagemPatente.getModel(); //recuperacao do modelo da table
                
                model.removeRow(indice); // remove a linha selecionada.
                   
            }
        }
    }
}
