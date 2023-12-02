package com.example.clinicabdt3.Model.DAO;


import com.example.clinicabdt3.Model.Domain.Consulta;
import com.example.clinicabdt3.Model.Domain.Medico;
import com.example.clinicabdt3.Model.Domain.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }



    public boolean inserir(Consulta consulta){

        String sql = "INSERT INTO consulta(fk_cpf_pac, fk_crm_med, data_consulta, horario_consulta) VALUES( ?,?,?,?)";
        System.out.println("Inserir consulta");
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            //stmt.setInt(1, consulta.getIdConsulta());
            stmt.setString(1, consulta.getPaciente());
            stmt.setString(2, consulta.getMedico());
            stmt.setDate(3, Date.valueOf(consulta.getDataConsulta()));
            stmt.setString(4, consulta.getHorario());
            stmt.execute();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no inserir do ConsultaDAO");
            return false;
        }

    }

    public boolean alterar(Consulta consulta){

        String sql = "UPDATE consulta SET fk_cpf_pac=?, fk_crm_med=?, data_consulta=?, horario_consulta=? WHERE idConsulta=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, consulta.getPaciente());
            stmt.setString(2, consulta.getMedico());
            stmt.setDate(3, Date.valueOf(consulta.getDataConsulta()));
            stmt.setString(4, consulta.getHorario());
            stmt.setInt(5, consulta.getIdConsulta());
            stmt.execute();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no alterar do ConsultaDAO");
            return false;
        }
    }

    public boolean remover(Consulta consulta){

        String sql = "DELETE FROM consulta WHERE idConsulta=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, consulta.getIdConsulta());
            stmt.execute();
            return true;
        }catch (SQLException ex){
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no remover do ConsultaDAO");
            return false;
        }
    }

//Não fiz alterar no consulta


    public List<Consulta> listar(){
        String sql = "SELECT * FROM consulta";
        List<Consulta> listConsulta = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){

                Consulta consulta = new Consulta();

                consulta.setIdConsulta(resultado.getInt("idConsulta"));
                consulta.setPaciente(resultado.getString("fk_cpf_pac"));
                consulta.setMedico(resultado.getString("fk_crm_med"));
                consulta.setDataConsulta(resultado.getDate("data_consulta").toLocalDate());
                consulta.setHorario(resultado.getString("horario_consulta"));
                listConsulta.add(consulta);

            }

        }catch (SQLException ex){
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no listar do ConsultaDAO");
        }
        return listConsulta;
    }

    public List<Consulta> buscar(String pesquisa){
        String sql = "SELECT * FROM consulta WHERE fk_cpf_pac LIKE ? or fk_crm_med LIKE ? or data_consulta LIKE ? or horario_consulta LIKE ?";
        List<Consulta> buscarConsulta= new ArrayList<>();

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%"+pesquisa+"%");
            stmt.setString(2, "%"+pesquisa+"%");
            stmt.setString(3, "%"+pesquisa+"%");
            stmt.setString(4, "%"+pesquisa+"%");
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){
                Consulta consulta = new Consulta();
                consulta.setPaciente(resultado.getString("fk_cpf_pac"));
                consulta.setMedico(resultado.getString("fk_crm_med"));
                consulta.setDataConsulta(resultado.getDate("data_consulta").toLocalDate());
                consulta.setHorario(resultado.getString("horario_consulta"));
                buscarConsulta.add(consulta);

            }

        }catch (SQLException ex){
            Logger.getLogger(ConsultaDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no buscar do ConsultaDAO");
        }
        return buscarConsulta;

    }

}
