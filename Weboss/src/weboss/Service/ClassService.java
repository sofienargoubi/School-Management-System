/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weboss.Service;


import Iservice.IService;
import Iservice.IService2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import weboss.BD.Database;
import weboss.Entities.Emplois;
import weboss.Entities.User;
import weboss.Entities.Class;

/**
 *
 * @author Pytrooooo
 */
public class ClassService implements IService2<Class>{
       private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    
    
    public ClassService() {
        cnx = Database.getInstance().getConnexion();
    }
    
       @Override
     public void AddClass(Class t) {
        String req = "Insert into classe ( name, niveau ,spec, nbr_etudiant,Description)  values (?,?,?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
             pst.setString(1,t.getName());
             pst.setString(2,t.getNiveau());
             pst.setString(3,t.getSpec());
             pst.setInt(4,t.getNbr_Etudiant());
             pst.setString(5,t.getDescription());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     @Override
       public ObservableList<Class> GetClass() {
        String req = "select * from classe";
        ObservableList<Class> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Class(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
       
       
        public ObservableList<String> GetNomClass() {
        String req = "select name from classe";
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                String nom=rs.getString(1);
                list.add(nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
                public ObservableList<String> GetEns() {
        String req = "select nom_class from classeenseignantmatiere ";
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                String nom=rs.getString(1);
                list.add(nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
                public ObservableList<String> GetMatiere() {
        String req = "select nom from matier";
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                String nom=rs.getString(1);
                list.add(nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
                
                    public String GetIDMatiere(String lol) {
        String req = "select id from matier where nom = '"+lol+"'";
        //ObservableList<String> list = FXCollections.observableArrayList();
        String nom="";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                nom=rs.getString(1);
               // list.add(nom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nom;
    }            
        
       
       @Override  
       public void DeleteClass(int id) {
        String req = "delete from classe where id = "+id+" ";
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
       
         @Override   
         public void UpdateClass(Class t) {
        String req = "update classe set name =?,niveau =?,spec=?,nbr_etudiant =?,description =? where id =? ";
        try {
            pst = cnx.prepareStatement(req);
             pst.setString(1,t.getName());
             pst.setString(2,t.getNiveau());
             pst.setString(3,t.getSpec());
             pst.setInt(4,t.getNbr_Etudiant());
             pst.setString(5,t.getDescription());
             pst.setInt(6,t.getId());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
         
             public void AffecteClass(int id,String classe) {
        String req = "update users set classeEtd =? where idUser =?  ";
        try {
            pst = cnx.prepareStatement(req);
             pst.setString(1,classe);
             pst.setInt(2,id);
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
             
        public ObservableList<User> GetEtudFromClass(String classe) {
        String req = "select nomUser,prenomUser from users where classeEtd = '"+classe+"' ";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new User(rs.getString("nomUser"),rs.getString("prenomUser")));
                System.out.println(list);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }   
        public ObservableList<User> GetEnseiFromClass(String classe) {
        String req = "SELECT * from users as u inner join classeenseignantmatiere as c on u.idUser=c.id_user where c.nom_class='"+classe+"' ";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new User(rs.getString("nomUser"),rs.getString("prenomUser")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }   
             
        public void AffecteEnseigantClass(String classe,int id_user,int id_mateire) {
        String req = "Insert into classeenseignantmatiere (nom_class, id_user,id_matiere)  values (?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
             
             pst.setString(1,classe);
             pst.setInt(2,id_user);
             pst.setInt(3,id_mateire);
            pst.executeUpdate();
           // System.out.println(pst.executeQuery());
            
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }          
         
         @Override
    public ObservableList<Class> SearchClass(String nom) {
        String req = "select * from classe where name like '%"+nom+"%' ";
        ObservableList<Class> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
                    
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new Class(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClassService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void AddEmplois(Emplois t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Emplois> GetEmplois() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Emplois> SearchEmplois(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DeleteEmplois(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void UpdateEmplois(Emplois t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    

    
  
}
