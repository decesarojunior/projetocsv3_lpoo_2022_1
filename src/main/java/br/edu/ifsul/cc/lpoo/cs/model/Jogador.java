
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;

/**
 *
 * @author telmo
 */
@Entity
@Table(name = "tb_jogador")
@NamedQueries({      
    @NamedQuery(name="Jogador.login",
               query="SELECT j From Jogador j where j.nickname = :paramN and j.senha = :paramS")
})
public class Jogador implements Serializable {
    
    @Id
    private String nickname;
    
    @Column(nullable = false, length = 10)
    private String senha;
    
    @Column
    private Integer pontos;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_cadastro;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_ultimo_login;
    
    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;//associação.
    
    @ManyToMany
    @JoinTable(name = "tb_jogador_patente", joinColumns = {@JoinColumn(name = "jogador_nickname")}, //agregacao, vai gerar uma tabela associativa.
                                       inverseJoinColumns = {@JoinColumn(name = "patente_id")})
    private List<Patente> patentes;//agregacao.
    
    
    @ManyToMany
    @JoinTable(name = "tb_jogador_artefato", joinColumns = {@JoinColumn(name = "jogador_nickname")}, //agregacao, vai gerar uma tabela associativa.
                                       inverseJoinColumns = {@JoinColumn(name = "artefato_id")})
    private List<Artefato> artefatos;//agregacao.
    
    
    @OneToMany(mappedBy = "jogador")//mappedBy deve apontar para a referencia de jogador dentro de Compra.
    private List<Compra> compras; //agregacao por composicao
    
    
    public Jogador(){
        
    }

    /**
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the pontos
     */
    public Integer getPontos() {
        return pontos;
    }

    /**
     * @param pontos the pontos to set
     */
    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    /**
     * @return the data_cadastro
     */
    public Calendar getData_cadastro() {
        return data_cadastro;
    }

    /**
     * @param data_cadastro the data_cadastro to set
     */
    public void setData_cadastro(Calendar data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    /**
     * @return the data_ultimo_login
     */
    public Calendar getData_ultimo_login() {
        return data_ultimo_login;
    }

    /**
     * @param data_ultimo_login the data_ultimo_login to set
     */
    public void setData_ultimo_login(Calendar data_ultimo_login) {
        this.data_ultimo_login = data_ultimo_login;
    }

    /**
     * @return the endereco
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * @param endereco the endereco to set
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    /**
     * @return the patentes
     */
    public List<Patente> getPatentes() {
        return this.patentes;
    }

    /**
     * @param patentes the patentes to set
     */
    public void setPatentes(List<Patente> patentes) {
        this.patentes = patentes;
    }
    
    public void setPatente(Patente patente) {
        if( this.patentes == null)
            this.patentes = new ArrayList() ;
        
        this.patentes.add(patente);
    }

    /**
     * @return the artefatos
     */
    public List<Artefato> getArtefatos() {
        return artefatos;
    }
    
    //atividade de revisao.
    public void setArtefato(Artefato artefato) {
        if( this.artefatos == null)
            this.artefatos = new ArrayList() ;
        
        this.artefatos.add(artefato);
    }

    /**
     * @param artefatos the artefatos to set
     */
    public void setArtefatos(List<Artefato> artefatos) {
        this.artefatos = artefatos;
    }

    /**
     * @return the compras
     */
    public List<Compra> getCompras() {
        return compras;
    }

    /**
     * @param compras the compras to set
     */
    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
    
    @Override
    public String toString(){        
        return nickname;
    }
}
