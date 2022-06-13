
package br.edu.ifsul.cc.lpoo.cs;

import javax.swing.JOptionPane;

/**
 *
 * @author telmo
 */
public class ProjetoCSV3_LPOO_2022_1 {
    
     private Controle controle;
    
    public ProjetoCSV3_LPOO_2022_1(){
        
                //primeiramente - estabelecer uma conexao com o banco de dados.
        
        
        try {
                controle = new Controle();//cria a instancia e atribui para o atributo controle.

                //"caminho feliz" : passo 3
                if(controle.conectarBD()){

                    //"caminho feliz" : passo 4
                    controle.initComponents();

                }else{

                        JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", "Banco de Dados", JOptionPane.ERROR_MESSAGE);
                }

        } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: "+ex.getLocalizedMessage(), "Banco de Dados", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        
       new ProjetoCSV3_LPOO_2022_1();
    }
}
