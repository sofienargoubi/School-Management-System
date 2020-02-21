/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import project.IService.IService;
import project.Utils.DataBase;
import project.entities.Parent;

/**
 *
 * @author Neifos
 */
public class ParentService implements IService<Parent>{
     private Connection con;
    private Statement ste;

    public ParentService() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Parent t) throws SQLException {
 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PreparedStatement pre = con.prepareStatement("INSERT INTO `users` (`idUser`, `cinUser`,`nomUser`,`prenomUser`,`dateNaissanceUser`,`sexeUser`,`emailUser`,`adresseUser`,`numTelUser`,`roleUser`,`motDePasseUser`,`idParent`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);");
        pre.setString(1, null);
        pre.setInt(2, t.getCinUser());
        pre.setString(3, t.getNomUser());
        pre.setString(4, t.getPrenomUser());
        pre.setString(5, dateFormat.format(t.getDateNaissanceUser()));
        pre.setString(6, t.getSexeUser());
        pre.setString(7, t.getEmailUser());
        pre.setString(8, t.getAdresseUser());
        pre.setInt(9, t.getNumTelUser());
        pre.setString(10, "Parent");
        pre.setString(11, t.getMotDePasseUser());
        pre.setString(12, t.getIdParent());
      

        pre.executeUpdate();    }

    @Override
    public boolean delete(Parent t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Parent t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Parent> readAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}