package com.example.clinicabdt3.Model;
import java.util.Date;

public class Consulta {

    private String idConsulta;
    private String paciente;

    private Date date;
    private String horario;


    public String getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(String idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
