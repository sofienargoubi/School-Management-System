/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entite.Question;
import Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bensl
 */
public class ServiceQuestion {
    private Connection con;
    private Statement ste;

    public ServiceQuestion() {
        con = DataBase.getInstance().getConnection();
    }
    
    public List<Question> readAll() throws SQLException {
        List<Question> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from question");
        while (rs.next()) {
              int id_question = rs.getInt(1);
              String body = rs.getString(2);
              int vote_question = rs.getInt(3);
              int id_tag = rs.getInt(4);
              int id_personne = rs.getInt(5);

            Question q = new Question(id_question, body, vote_question, id_tag, id_personne);
            arr.add(q);
        }
        return arr;
    }
    
    public void update(int id_question, String body, int vote_question, int id_tag, int id_personne) throws SQLException {
        String query = "UPDATE `question` SET `body`=?,`vote_question`=?,`id_tag`=?,`id_personne`=? WHERE `id_question`=?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            
            stmt.setString(1, body);
            stmt.setInt(2, vote_question);
            stmt.setInt(3, id_tag);
            stmt.setInt(4, id_personne);
            stmt.setInt(5, id_question);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void delete(int pid) throws SQLException {
        String query = "DELETE FROM `question` WHERE `id_question`=?";

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, pid);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void ajouter(Question q) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `question` (`id_question`, `body`, `vote_question`, `id_tag`, `id_personne`) VALUES ('"+q.getId_question()+"', '"+q.getBody()+"', '"+q.getVote()+"', '"+q.getId_tag()+"', '"+q.getId_personne()+"');";
        ste.executeUpdate(requeteInsert);
    }
    
    public void ajouter2(Question q) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `question` (`body`, `vote_question`, `id_tag`, `id_personne`) VALUES ('"+q.getBody()+"', '"+q.getVote()+"', '"+q.getId_tag()+"', '"+q.getId_personne()+"');";
        ste.executeUpdate(requeteInsert);
    }
    
    
    
}
