/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weboss.Entities;

import java.sql.Date;

/**
 *
 * @author Neifos
 */
public class UserParent extends User{
    public static UserParent parent=null;
  

    public UserParent(String idUser, int cinUser, String nomUser, String prenomUser, String emailUser, String adresseUser, int numTelUser, Date dateNaissanceUser, String sexeUser, String motDePasseUser, String roleUser,String picUser) {
        super(idUser, cinUser, nomUser, prenomUser, emailUser, adresseUser, numTelUser, dateNaissanceUser, sexeUser, motDePasseUser, roleUser,picUser);
       
    }

   

    @Override
    public String toString() {
        return "Parent{" +super.toString();
    }
    
}
