/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import API.Mailing;
import pidev.*;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import pidev.entities.DemandeEvenement;
import pidev.entities.Evenement;
import pidev.entities.User;
import pidev.service.DemandeEvenementService;
import pidev.service.EvenementService;
import pidev.service.UserService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class Afficher_Demande_EvenementController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<DemandeEvenement> tab_demande_evenemnt;

    @FXML
    private TableColumn<DemandeEvenement, Integer> id_evenement;

    @FXML
    private TableColumn<DemandeEvenement, Integer> id_club;

    @FXML
    private TableColumn<DemandeEvenement, String> date_debut;

    @FXML
    private TableColumn<DemandeEvenement, String> date_fin;

    @FXML
    private TableColumn<DemandeEvenement, String> description;

    @FXML
    private TableColumn<DemandeEvenement, String> etat;
    @FXML
    private JFXTextField id_evenment_selectionne;
int identfiant=1;
    private final ObservableList<DemandeEvenement> listDemandeEvenement = FXCollections.observableArrayList();
    DemandeEvenementService cs = new DemandeEvenementService();
    EvenementService evs = new EvenementService();
    UserService u=new UserService();
    @FXML
    private TableColumn<DemandeEvenement, Float> Budget;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        try {
            listDemandeEvenement.addAll(cs.affciher());
        } catch (SQLException ex) {
            Logger.getLogger(Page2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        id_evenement.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, Integer>("idDemandeEvenement"));
        id_club.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, Integer>("idclub"));
        date_debut.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, String>("datedebut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, String>("datefin"));
        description.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, String>("description"));
        etat.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, String>("etat"));
        Budget.setCellValueFactory(new PropertyValueFactory<DemandeEvenement, Float>("budget"));
        tab_demande_evenemnt.setItems(listDemandeEvenement);

    }

    @FXML
    void select(MouseEvent event) {
        DemandeEvenement dev = tab_demande_evenemnt.getSelectionModel().getSelectedItem();

        System.out.println("hhhh");
        System.out.println(dev.getIdDemandeEvenement());

        id_evenment_selectionne.setText("" + dev.getIdDemandeEvenement());
    }

    @FXML
    void validation(MouseEvent event) {
        final ObservableList<DemandeEvenement> refresh = FXCollections.observableArrayList();

        try {
            DemandeEvenement dev = tab_demande_evenemnt.getSelectionModel().getSelectedItem();
            cs.valider(Integer.parseInt(id_evenment_selectionne.getText()));
            evs.ajouter_demande_valider();
           // cs.supprimer(dev.getIdDemandeEvenement());
            String maill=u.getMail(identfiant);
            Mailing m=new Mailing();
            m.envoyer(maill);
            System.out.println(maill);
            dev.getIdclub();
            refresh.addAll(cs.affciher());
            tab_demande_evenemnt.setItems(refresh);

        } catch (SQLException ex) {
            ex.getMessage();
        }

    }

}
