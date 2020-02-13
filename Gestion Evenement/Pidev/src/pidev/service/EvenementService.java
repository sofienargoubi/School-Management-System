/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.service;

import IService.IService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pidev.BD.Database;
import pidev.entities.Evenement;

/**
 *
 * @author asus
 */
public class EvenementService implements IService<Evenement> {

    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public EvenementService() {
        cnx = Database.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Evenement t) throws SQLException {

        String req = "INSERT INTO Evenement (dateDebut,dateFin,idClub) values(?,?,?)";
        pst = cnx.prepareStatement(req);
        pst.setString(1, t.getDateDebut());
        pst.setString(2, t.getDateFin());
        pst.setInt(3, t.getIdClub());
        pst.execute();
    }

    @Override
    public void modifier(Evenement t, int id) throws SQLException {
        System.out.println(id);
        String req = "UPDATE `Evenement` SET `dateDebut` = '" + t.getDateDebut() + "', `dateFin` = '" + t.getDateFin() + "' WHERE `Evenement`.`idEvenement` = " + id + "";
        pst = cnx.prepareStatement(req);

        pst.execute();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `Evenement` WHERE `Evenement`.`idEvenement` = " + id + "";
        pst = cnx.prepareStatement(req);

        pst.execute();
    }

    @Override
    public List<Evenement> affciher() throws SQLException {
        List<Evenement> arr = new ArrayList<>();
        ste = cnx.createStatement();
        ResultSet rs = ste.executeQuery("select * from Evenement");
        while (rs.next()) {
            int id = rs.getInt("idEvenement");
            int idClub = rs.getInt("idClub");

            Date dd = rs.getDate("dateDebut");
            Date df = rs.getDate("dateFin");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ddd = dateFormat.format(dd);
            DateFormat dateFormat0 = new SimpleDateFormat("yyyy-MM-dd");
            String dff = dateFormat0.format(df);
            Evenement e = new Evenement(id, ddd, dff, idClub);
            arr.add(e);
        }
        return arr;
    }

   

    /* @Override
    public List<Evenement> recherche(String x) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
/* public void ajouterEvenemet(Evenement ev) {
 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_debut = dateFormat.format(ev.getDateDebut());
            DateFormat dateFormat0 = new SimpleDateFormat("yyyy-MM-dd");
            String date_fin = dateFormat0.format(ev.getDateFin());
        try {
            String req = "INSERT INTO Evenement (dateDebut,dateFin,idClub) values(?,?,?)";
            pst = cnx.prepareStatement(req);
            pst.setString(1, date_debut);
            pst.setString(2, date_fin);
            pst.setInt(3, ev.getIdClub());
            pst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(EvenementService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void afficherEvenement() {
        try {
            String req = "SELECT * FROM Evenement ";
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                System.out.println("date debut d'evenement est :" + rs.getString("dateDebut"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void supprimerEvenement(int id) {
        try {

            String req = "DELETE FROM `Evenement` WHERE `Evenement`.`idEvenement` = " + id + "";
            pst = cnx.prepareStatement(req);

            pst.execute();
        } catch (SQLException e) {
            e.getMessage();
        }

    }

    public void modefierEvenement(int id, Date dd, Date df) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date_debut = dateFormat.format(dd);
            DateFormat dateFormat0 = new SimpleDateFormat("yyyy-MM-dd");
            String date_fin = dateFormat0.format(df);

            String req = "UPDATE `Evenement` SET `dateDebut` = '" + date_debut + "', `dateFin` = '" + date_fin + "' WHERE `Evenement`.`idEvenement` = " + id + "";
            pst = cnx.prepareStatement(req);

            pst.execute();
        } catch (SQLException e) {
            e.getMessage();
        }

    }

}*/