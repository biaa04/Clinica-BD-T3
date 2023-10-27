package com.example.clinicabdt3.Model.DAO;

import com.example.clinicabdt3.Model.Domain.Paciente;

import java.sql.Connection;

public class PacienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Paciente paciente){

        //Incompleto
        String sql = "INSERT INTO paciente(nome_pac, cpf, data_nascimento, plano_de_saude, senha) VALUES(?,?,?,?,?)";
        return true;
    }
}
