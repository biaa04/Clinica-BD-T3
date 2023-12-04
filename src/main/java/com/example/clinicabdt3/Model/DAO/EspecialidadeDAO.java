package com.example.clinicabdt3.Model.DAO;

import com.example.clinicabdt3.Model.Domain.Especialidade;
import javafx.fxml.Initializable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EspecialidadeDAO{
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Especialidade especialidade) {

        String sql = "INSERT INTO especialidade( id, nome) VALUES (?,?)";

        try {
            System.out.println("a");
            PreparedStatement stmt = connection.prepareStatement(sql);
            System.out.println("a");
            stmt.setInt(1, especialidade.getId());
            stmt.setString(2, especialidade.getNome());

            return true;
        }
        catch (SQLException ex){
            Logger.getLogger(EspecialidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no inserir do EspecialidadeDAO");
            return false;
        }
    }

    public boolean remover(Especialidade especialidade){

        String sql = "DELETE FROM especialidade WHERE id=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, especialidade.getId());
            stmt.execute();
            return true;
        }catch (SQLException ex){
            Logger.getLogger(EspecialidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no remover do EspecialidadeDAO");
            return false;
        }
    }

    public boolean alterar(Especialidade especialidade){

        String sql = "UPDATE especialidade SET nome =? WHERE id=?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, especialidade.getNome());
            stmt.setInt(2, especialidade.getId());
            stmt.execute();
            return true;

        }catch (SQLException ex){
            Logger.getLogger(EspecialidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no alterar do EspecialidadeDAO");
            return false;
        }
    }

    public List<Especialidade> listar(){
        String sql = "SELECT * FROM especialidade";
        List<Especialidade> listEspecialidade = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){

                Especialidade especialidade = new Especialidade();
                especialidade.setId(resultado.getInt("Id"));
                especialidade.setNome(resultado.getString("nome"));
                listEspecialidade.add(especialidade);

            }

        }catch (SQLException ex){
            Logger.getLogger(EspecialidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no listar do EspecialidadeDAO");
        }
        return listEspecialidade;
    }

    public List<String> pegarEspecialidade(){
        String sql = "SELECT nome FROM especialidade";
        List<String> listEspecialidade = new ArrayList<>();

        try {
            System.out.println("uuuuuuuuuuuuuuuuuu");
            System.out.println("ENtrou no listar");
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()){

                Especialidade especialidade = new Especialidade();
                especialidade.setNome(resultado.getString("nome"));
                listEspecialidade.add(especialidade.getNome());

            }

        }catch (SQLException ex){
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception no listar do MedicoDAO");
        }
        return listEspecialidade;
    }

    public String buscarNomePorId(int idEspecialidade) {
        String query = "SELECT nome FROM especialidade WHERE id = ?";
        String nomeEspecialidade = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idEspecialidade);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    nomeEspecialidade = resultSet.getString("nome");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nomeEspecialidade;
    }
}