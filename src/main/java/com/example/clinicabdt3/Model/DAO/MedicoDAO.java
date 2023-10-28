package com.example.clinicabdt3.Model.DAO;

import com.example.clinicabdt3.Model.Domain.Medico;
import com.example.clinicabdt3.Model.Domain.Paciente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Medico medico){

        String sql = "INSERT INTO medico(crm, nome_med, especialidade, senha) VALUES (?,?,?,?)";

        try {
            System.out.println("a");
            PreparedStatement stmt = connection.prepareStatement(sql);
            System.out.println("a");
            stmt.setString(1, medico.getCRM());
            stmt.setString(2, medico.getNome());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getSenha());
            stmt.execute();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no inserir do MedicoDAO");
            return false;
        }

    }

    public boolean remover(Medico medico){

        String sql = "DELETE FROM medico WHERE crm=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, medico.getCRM());
            stmt.execute();
            return true;
        }catch (SQLException ex){
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no remover do MedicoDAO");
            return false;
        }
    }

    public boolean alterar(Medico medico){

        String sql = "UPTADE medico SET crm=?, nome_med=?, especialidade=?, senha=? WHERE crm=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, medico.getCRM());
            stmt.setString(2, medico.getNome());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getSenha());
            stmt.execute();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no alterar do MedicoDAO");
            return false;
        }
    }

    public List<Medico> listar(){
        String sql = "SELECT * FROM medico";
        List<Medico> listMedico = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){

                Medico medico = new Medico();
                medico.setCRM(resultado.getString("crm"));
                medico.setNome(resultado.getString("nome_med"));
                medico.setEspecialidade(resultado.getString("especialidade"));
                medico.setSenha(resultado.getString("senha"));
                listMedico.add(medico);

            }

        }catch (SQLException ex){
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no listar do MedicoDAO");
        }
        return listMedico;
    }
}
