package model;

import javax.persistence.Entity;

@Entity
public class Proprietario extends Pessoa {
    
    public Proprietario() {
        super(); 
        
    }

    public Proprietario(int id, String nome, String email, String telefone, String senha, String cpf, boolean ativo) {
        super(id, nome, email, telefone, senha, cpf, ativo);   
    }

    @Override
    public String toString() {
        return "Proprietario{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", ativo=" + isAtivo() +
                '}';
    }
}
