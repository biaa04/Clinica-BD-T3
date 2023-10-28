package com.example.clinicabdt3.Model.Domain;

import java.time.LocalDate;
import java.util.Date;

public class Paciente {

    private String nome;
    private String CPF;
    private LocalDate data_nascimento;
    private String plano_de_saude;
    private String senha;


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

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getPlano_de_saude() {
        return plano_de_saude;
    }

    public void setPlano_de_saude(String plano_de_saude) {
        this.plano_de_saude = plano_de_saude;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
