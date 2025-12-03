package model;

import javax.persistence.Entity;

@Entity
public class Proprietario extends Pessoa {
    
    public Proprietario() {
        super(); 
        
    }

    public Proprietario(int id, String nome, String email, String telefone, String senha, String cpf, boolean ativo) {
        
        super(id,
              validarNome(nome), 
              validarEmail(email),
              validarTelefone(telefone),
              validarSenha(senha),
              validarCpf(cpf),
              ativo);   
    }
    
    private static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (nome.length() < 3) {
            throw new IllegalArgumentException("Nome deve ter pelo menos 3 caracteres.");
        }
        return nome.trim();
    }

    private static String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio.");
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Email no formato inválido.");
        }

        return email.trim().toLowerCase();
    }

    private static String validarCpf(String cpf) {
        if (cpf == null) {
            throw new IllegalArgumentException("CPF não pode ser nulo.");
        }

        cpf = cpf.replaceAll("\\D", ""); 

        if (!cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve ter exatamente 11 dígitos.");
        }

        if (!cpfValido(cpf)) {
            throw new IllegalArgumentException("CPF inválido (dígitos verificadores não batem).");
        }

        return cpf;
    }

    // Validação real de CPF (dígitos verificadores)
    private static boolean cpfValido(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false; // evita 11111111111

        try {
            int soma1 = 0, soma2 = 0;
            for (int i = 0; i < 9; i++) {
                int dig = cpf.charAt(i) - '0';
                soma1 += dig * (10 - i);
                soma2 += dig * (11 - i);
            }

            int dv1 = soma1 % 11;
            dv1 = (dv1 < 2) ? 0 : 11 - dv1;

            int dv2 = (soma2 + dv1 * 2) % 11;
            dv2 = (dv2 < 2) ? 0 : 11 - dv2;

            return dv1 == (cpf.charAt(9) - '0') &&
                   dv2 == (cpf.charAt(10) - '0');

        } catch (Exception e) {
            return false;
        }
    }

    private static String validarTelefone(String telefone) {
       
        telefone = telefone.replaceAll("\\D", "");

        if (telefone.length() < 10 || telefone.length() > 11) {
            throw new IllegalArgumentException("Telefone deve ter DDD + número (10 ou 11 dígitos).");
        }

        return telefone;
    }

    private static String validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia.");
        }

        if (senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres.");
        }

        return senha;
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
