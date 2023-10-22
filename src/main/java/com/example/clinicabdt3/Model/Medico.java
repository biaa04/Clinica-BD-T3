package com.example.clinicabdt3.Model;

public class Medico {
    private String nome;
    private String CRM;
    private String especialidade;
    private String senha;



    public Medico(String nome, String senha, String CRM, String especialidade) {
        this.nome = nome;
        this.senha = senha;
        this.CRM = CRM;
        this.especialidade = especialidade;

    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
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
