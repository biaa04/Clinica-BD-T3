package com.example.clinicabdt3.Model.DAO;

import com.example.clinicabdt3.Model.Domain.Paciente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacienteDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Paciente paciente){


        String sql = "INSERT INTO paciente(cpf, nome_pac,data_nascimento, id_paciente) VALUES(?,?,?,?)";

        try {
            System.out.println("inserir");
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, paciente.getCPF());
            stmt.setString(2, paciente.getNome());
            stmt.setDate(3, Date.valueOf(paciente.getData_nascimento()));
            stmt.setInt(4, paciente.getIdPaciente());
            stmt.execute();
            connection.close();
            return true;

        }catch (SQLException ex){

            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no inserir do PAcienteDAo");
            return false;
        }

    }

    public boolean remover(Paciente paciente){

        String sql = "DELETE FROM paciente WHERE cpf=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, paciente.getCPF());
            stmt.execute();
            connection.close();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no remover do PacienteDAO");
            return false;

        }
    }

    public boolean alterar(Paciente paciente){

        String sql = "UPDATE paciente SET cpf=?, nome_pac=?, data_nascimento=? WHERE id_paciente=?";
        System.out.println("Alterar PacienteDAO1");

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, paciente.getCPF());
            stmt.setString(2, paciente.getNome());
            stmt.setDate(3, Date.valueOf(paciente.getData_nascimento()));
            stmt.setInt(4, paciente.getIdPaciente());
            stmt.execute();
            connection.close();
            System.out.println("Alterar PacienteDAO2");
            return true;
        }catch (SQLException ex){

            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no alterar do PacienteDAO");
            return false;

        }

    }

    public List<Paciente> listar(){
        String sql = "SELECT * FROM paciente";
        List<Paciente> listPaciente = new ArrayList<>();

        try {
        System.out.println("ENtrou no listar");
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){

                Paciente paciente = new Paciente();
                paciente.setCPF(resultado.getString("cpf"));
                paciente.setNome(resultado.getString("nome_pac"));
                paciente.setData_nascimento(resultado.getDate("data_nascimento").toLocalDate());
                paciente.setIdPaciente(resultado.getInt("id_paciente"));
                listPaciente.add(paciente);

            }

        }catch (SQLException ex){
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no listar do PacienteDAO");
        }
        return listPaciente;
    }

    public List<Paciente> buscar(String pesquisa){
        String sql = "SELECT * FROM paciente WHERE id_paciente LIKE ? or cpf LIKE ? or nome_pac LIKE ? or data_nascimento LIKE ?";
        List<Paciente> buscarPaciente = new ArrayList<>();

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%"+pesquisa+"%");
            stmt.setString(2, "%"+pesquisa+"%");
            stmt.setString(3, "%"+pesquisa+"%");
            stmt.setString(4, "%"+pesquisa+"%");
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(resultado.getInt("id_paciente"));
                paciente.setCPF(resultado.getString("cpf"));
                paciente.setNome(resultado.getString("nome_pac"));
                paciente.setData_nascimento(resultado.getDate("data_nascimento").toLocalDate());
                buscarPaciente.add(paciente);

            }

        }catch (SQLException ex){
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no buscar do PacienteDAO");
        }
        return buscarPaciente;

    }

    public List<String> pegarPacienteConsulta(){
        String sql = "SELECT cpf FROM paciente";
        List<String> listPaciente = new ArrayList<>();

        try {
            System.out.println("ppppppppppppppppp");
            System.out.println("ENtrou no listar");
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){

                Paciente paciente = new Paciente();
                paciente.setCPF(resultado.getString("cpf"));
                listPaciente.add(paciente.getCPF());

            }

        }catch (SQLException ex){
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no listar do PacienteDAO");
        }
        return listPaciente;
    }


}
