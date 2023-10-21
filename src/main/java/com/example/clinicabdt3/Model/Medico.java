package com.example.clinicabdt3.Model;

public class Medico extends Usuario{
    private String CRM;
    private String especialidade;
    private String planoQueAtende;

    public Medico(String nome, String CPF, String senha, String CRM) {
        super(nome, CPF, senha);
        this.CRM = CRM;
        this.especialidade = especialidade;
        this.planoQueAtende = planoQueAtende;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getPlanoQueAtende() {
        return planoQueAtende;
    }

    public void setPlanoQueAtende(String planoQueAtende) {
        this.planoQueAtende = planoQueAtende;
    }

    public String getCRM() {
        return CRM;
    }

    public void setCRM(String CRM) {
        this.CRM = CRM;
    }
}
