package com.example.clinicabdt3.Model;

public class Usuario {
    private String nome;
    private String CPF;
    private String senha;

    public Usuario(String nome, String CPF, String senha){
        this.CPF = CPF;
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCPF() {
        return CPF;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
