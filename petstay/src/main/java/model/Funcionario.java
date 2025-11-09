package model;

import javax.persistence.Entity;

@Entity
public class Funcionario extends Pessoa{
    private boolean permissao;
    
    public Funcionario(){
        super();
        this.permissao = false;
    }
    
    public Funcionario(int id, String nome, String email, String telefone, String senha, String cpf, boolean ativo, boolean permissao) {
    super(id, nome, email, telefone, senha, cpf, ativo);
    this.permissao = permissao;
}

   
    public boolean isPermissao() {
        return permissao;
    }

    
    public void setPermissao(boolean permissao) {
        this.permissao = permissao;
    }

     @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", ativo=" + isAtivo() +
                ", permissao=" + permissao +
                '}';
    }
    
}



