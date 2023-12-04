package com.example.clinicabdt3.Model.Domain;

public class Medico {

    private int id;
    private String nome;
    private String CRM;
    private int especialidade;
    private String senha;



    public Medico(){}
    public Medico(String nome, String senha, String CRM, int especialidade) {
        this.nome = nome;
        this.senha = senha;
        this.CRM = CRM;
        this.especialidade = especialidade;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(int especialidade) {
        this.especialidade = especialidade;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
