package model;

public class Endereco {
    private int id_endereco;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco() {
        this.id_endereco = 0;
        this.rua = "";
        this.numero = "";
        this.complemento = "";
        this.bairro = "";
        this.cidade = "";
        this.estado = "";
        this.cep = "";
    }

    public Endereco(int id_endereco, String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.id_endereco = id_endereco;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    public String getRua() { 
        return rua; 
    }
    public void setRua(String rua) { 
        this.rua = rua; 
    }

    public String getNumero() { 
        return numero; 
    }
    public void setNumero(String numero) { 
        this.numero = numero; 
    }

    public String getComplemento() { 
        return complemento; 
    }
    public void setComplemento(String complemento) { 
        this.complemento = complemento; 
    }

    public String getBairro() { 
        return bairro; 
    }
    public void setBairro(String bairro) { 
        this.bairro = bairro; 
    }

    public String getCidade() {
        return cidade; 
    }
    public void setCidade(String cidade) {
        this.cidade = cidade; 
    }

    public String getEstado() {
        return estado; 
    }
    public void setEstado(String estado) {
        this.estado = estado; 
    }

    public String getCep() {
        return cep; 
    }
    public void setCep(String cep) {
        this.cep = cep; 
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "id_endereco=" + id_endereco +
                ", rua='" + rua + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }
}
