package com.example.clinicabdt3.Model;

public class Paciente extends Usuario{
    private int idade;
    private String planoDeSaude;


    public Paciente(String nome, String CPF, String senha, int idade, String planoDeSaude) {
        super(nome, CPF, senha);
        this.idade = idade;
        this.planoDeSaude = planoDeSaude;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPlanoDeSaude() {
        return planoDeSaude;
    }

    public void setPlanoDeSaude(String planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }
}
