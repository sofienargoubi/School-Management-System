/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weboss.Service;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import weboss.BD.Database;
import weboss.Entities.Note;
import weboss.Entities.Resultat;

/**
 *
 * @author Aymen
 */
public class ServiceResultat {

    private Connection con;
    private Statement ste;

    public ServiceResultat() {
        con = Database.getInstance().getConnexion();
    }

    public void ajouterResultat(Resultat r) throws SQLException {
        PreparedStatement pre = con.prepareStatement("INSERT INTO  `pidev`.`Resultat` ( `idEtudiant`, `dateResultat`, `resultat`) VALUES ( ?, ?, ?);");
        pre.setInt(1,Integer.parseInt(r.getEtudiant().getIdUser()));
        pre.setDate(2, r.getDateResultat());
        pre.setFloat(3, r.getResultat());
        pre.executeUpdate();
    }

    public List<Resultat> readAll() throws SQLException {
        List<Resultat> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from Resultat");
        while (rs.next()) {
            int idEtudiant = rs.getInt("idEtudiant");
            Date d = rs.getDate("dateResultat");
            float resultat = rs.getFloat("resultat");

            Resultat r = new Resultat(idEtudiant, d, resultat);
            arr.add(r);
        }
        return arr;

    }

    public List<Integer> getListEtudiant() throws SQLException {
        List<Integer> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select idEtudiant from note");
        while (rs.next()) {
            int id = rs.getInt("idEtudiant");
            arr.add(id);
        }
        return arr;
    }
    
        public List<String> getListMailEtudiant() throws SQLException {
        List<String> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select email from users join resultat on idUser=idEtudiant ");
        while (rs.next()) {
            String s = rs.getString("email");
            arr.add(s);
        }
        return arr;
    }

    /*   public List<Resultat> getRésultClass(String classe) throws SQLException {
        List<Resultat> arr = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select idUser from users where roleUser = 'Etudiant'");
        while (rs.next()) {
            int id = rs.getInt("idUser");
            arr.add(id);
        }
        return arr;
    }
     */
   /* public int getcoeff(int c) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select coef from matier where id = " + Integer.toString(c));
        while (rs.next()) {
            return rs.getInt("coef");

        }
        return 0;

    }*/


        public float PourcentageReussiteEcole() throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select COUNT(r.resultat)  as nb from resultat r  where r.resultat >= 10");
        while (rs.next()) {
            System.out.println((1.0f*rs.getInt("nb")));
            return ((1.0f*rs.getInt("nb"))/getListEtudiant().size()*100);

        }
        
        return 0;

    }

        public float PourcentageEcheanceEcole() throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select COUNT(r.resultat)  as nb from resultat r  where r.resultat < 10");
        while (rs.next()) {
            System.out.println((1.0f*rs.getInt("nb")));
            return ((1.0f*rs.getInt("nb"))/getListEtudiant().size()*100);

        }
        
        return 0;

    }

    public float getResultat(String idE) throws SQLException {

        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select resultat from Resultat where idEtudiant =" + idE);
        while (rs.next()) {
            return rs.getFloat("resultat");

        }
        return 0;

    }

    
        public void delete(int idE) throws SQLException {
        String sql = "DELETE FROM Resultat WHERE idEtudiant=? ";

        PreparedStatement pre = con.prepareStatement(sql);
        pre.setInt(1, idE);

        int rowsDeleted = pre.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Resultat a été supprimer avec succès");
        }
        /*else
        {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Ajouter Note");
        alert.setHeaderText("Invalid National Identity Card Number !! ");
        alert.setContentText("please verify your Nation Identity card Number ");
        alert.showAndWait();*/
       // }
    
    }
    
        public void deleteAll() throws SQLException {
        String sql = "DELETE FROM Resultat ";

        PreparedStatement pre = con.prepareStatement(sql);
        int rowsDeleted = pre.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Resultat a été supprimer avec succès");
        }
    }

    // public formuleResultat(int coeff,){}
    public float calculResultatParEtudiant(int idE) throws SQLException {
        ServiceNote ser = new ServiceNote();
        float coef;
        float sommeCoeff = 0.0f;
        float sommeMoy = 0.0f;
        List<Note> listN = ser.listeNoteEtudiant(idE);
        for (Note n : listN) {
            coef = n.getMatiere().getCoefficient();
            sommeCoeff += coef;
            sommeMoy += (n.getMoyenne() * coef);
            
        }
        System.out.println(sommeMoy);
        System.out.println(sommeCoeff);
        return (float) sommeMoy / sommeCoeff;
    }

    public void enregistrerResultat(int idE) throws SQLException {
        String sql = "UPDATE Resultat SET resultat=? WHERE idEtudiant=?";

        PreparedStatement pre = con.prepareStatement(sql);
        pre.setFloat(1, calculResultatParEtudiant(idE));
        pre.setInt(2, idE);

        int rowsUpdated = pre.executeUpdate();

        pre.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Resultat enregistrer avec succès!");
        }

    }

    public void calculResultats(List<Integer> l) throws SQLException {
        ServiceNote ser = new ServiceNote();
        Resultat r;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-mm-yyyy");
        Date d =new Date(System.currentTimeMillis());
        for (Integer E : l) {
            r = new Resultat(E,d);
            ajouterResultat(r);
            System.out.println("ajout resultat avec succes ");
            enregistrerResultat(E);

        }

    }
    public void schoolAnnual(List<Integer> l) throws SQLException {
        ServiceNote ser = new ServiceNote();
        Resultat r;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-mm-yyyy");
        Date d =new Date(System.currentTimeMillis());
        for (Integer E : l) {
            r = new Resultat(E,d);
            ajouterResultat(r);
            System.out.println("ajout resultat avec succes ");
            enregistrerResultat(E);

        }

    }
}