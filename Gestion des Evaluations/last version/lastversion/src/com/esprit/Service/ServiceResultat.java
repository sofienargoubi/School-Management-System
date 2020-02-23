/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.Service;

import com.esprit.Entite.Note;
import com.esprit.Entite.Resultat;
import com.esprit.Utils.DataBase;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aymen
 */
public class ServiceResultat {

    private Connection con;
    private Statement ste;

    public ServiceResultat() {
        con = DataBase.getInstance().getConnection();
    }

    /*public void ajouter(Resultat r) throws SQLException {
        PreparedStatement pre = con.prepareStatement("INSERT INTO  `school`.`Resultat` ( `idEtudiant`, `dateResultat`, `resultat`) VALUES ( ?, ?, ?);");
        pre.setInt(1, r.getIdEtudiant());
        pre.setDate(2, new Date(r.getDateResultat().getYear(), r.getDateResultat().getMonth(), r.getDateResultat().getDay()));
        pre.setFloat(3, 0.0f);
        pre.executeUpdate();
    }*/
    public void ajouterResultat(Resultat r) throws SQLException {
        PreparedStatement pre = con.prepareStatement("INSERT INTO  `school`.`Resultat` ( `idEtudiant`, `dateResultat`, `resultat`) VALUES ( ?, ?, ?);");
        pre.setInt(1, r.getIdEtudiant());
        pre.setDate(2, r.getDateResultat());
        pre.setFloat(3, calculResultatParEtudiant(r.getIdEtudiant()));
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
        ResultSet rs = ste.executeQuery("select idUser from users where roleUser = 'Etudiant'");
        while (rs.next()) {
            int id = rs.getInt("idUser");
            arr.add(id);
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

    public float PourcentageReussiteparClasse(String classe) throws SQLException {

        ste = con.createStatement();
        int nb=1,nbClass=1;
        ResultSet rs = ste.executeQuery("select COUNT(r.resultat) as nb from users u join resultat r "
                +"on u.idUser=r.idEtudiant WHERE classeEtd like '"+classe+"%' and resultat >= 10 ");
        while (rs.next()) {
            nb =rs.getInt("nb");

        }
         ResultSet rs2 = ste.executeQuery("select COUNT(*) as nb2 from users  WHERE classeEtd like '"+classe+"%' and roleUser = 'Etudiant'" );
        while (rs2.next()) {
            System.out.println(rs.getInt("nb2"));
            nbClass =rs.getInt("nb2");

        }
         
        return nbClass*1.0f/nb*100;

    }

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

    public void delete(Resultat r) throws SQLException {
        String sql = "DELETE FROM Resultat WHERE idEtudiant=? ";

        PreparedStatement pre = con.prepareStatement(sql);
        pre.setInt(1, r.getIdEtudiant());

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
            coef = n.getCoefficient();
            sommeCoeff += coef;
            sommeMoy += (n.getMoyenne() * coef);
            
        }
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
        for (Integer E : l) {
            r = new Resultat(E, new Date(2020, 02, 12));
            ajouterResultat(r);
            System.out.println("ajout resultat avec succes ");
            enregistrerResultat(E);

        }

    }

}