/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTimePicker;
import entity.Absence;
import entity.Matiere;
import entity.User;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.AbsenceService;

/**
 * FXML Controller class
 *
 * @author Pytrooooo
 */
public class AbsenceController implements Initializable {

    @FXML
    private TableView<User> ListEtuEns;
    @FXML
    private JFXComboBox<String> SelectRole;
    @FXML
    private JFXComboBox<String> SelectMatiere;
    @FXML
    private JFXTimePicker TimeFin;
    @FXML
    private JFXTimePicker TimeDebut;
  
    @FXML
    private Label IdRole;
    @FXML
    private TableColumn<User,String> UserNomCol;
    @FXML
    private JFXDatePicker DateAb;
    @FXML
    private AnchorPane rootPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        UserNomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        AbsenceService as=new AbsenceService();
              ObservableList<String> optionsRole = 
    FXCollections.observableArrayList("Etudiant","Enseignant");
              
              ObservableList<String> matier= as.GetNomMatiere();
       SelectRole.getItems().clear();
       SelectRole.setItems(optionsRole);
       SelectMatiere.setItems(matier);
        init();
        getall();
    }   
    
    
  
         private void ShowInfo() {
       
            // Open add event view
            try {
               User u= ListEtuEns.getSelectionModel().getSelectedItem();
               // Load root layout from fxml file.
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("AbsenceShowUser.fxml"));
               AnchorPane rootLayout = (AnchorPane) loader.load();
               Stage stage = new Stage(StageStyle.UNDECORATED);
               stage.initModality(Modality.APPLICATION_MODAL); 
               AbsenceService as=new AbsenceService();
               int a=as.GetNbrAbsence(u.getId());
               // Pass main controller reference to view
               AbsenceShowUserController abController = loader.getController();
               abController.setMainController(this);
               abController.setData(u.getRole(),u.getNom(),u.getPrenom(),String.valueOf(a));
               // Show the scene containing the root layout.
               Scene scene = new Scene(rootLayout);
               stage.setScene(scene);
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(CalendarEventController.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
      
    
    

    
    public void init(){
        SelectRole.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                IdRole.setText(SelectRole.getValue());
                AbsenceService as=new AbsenceService();       
               // User a=(User) as.GetRole(SelectRole.getValue());
                ListEtuEns.setItems(as.GetRole(SelectRole.getValue()));
            }
        });
    }
    
    public void getall(){
      
        ListEtuEns.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                System.out.println(  ListEtuEns.getSelectionModel().getSelectedItem());
                ShowInfo();
            }
        });
    }


    @FXML
    private void AddAction(ActionEvent event) throws IOException {
        AbsenceService as=new AbsenceService();  
        System.out.println("a");
        Matiere m=as.GetMatiere(SelectMatiere.getValue());
        System.out.println("aa");
        User u=ListEtuEns.getSelectionModel().getSelectedItem();
        System.out.println("aaa");
        Absence t =new Absence(u, m, Date.valueOf(DateAb.getValue()), Time.valueOf(TimeDebut.getValue()), Time.valueOf(TimeFin.getValue()));
        System.out.println("aaaa");
        as.AddAbsence(t);
        System.out.println("aaaaa");
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }


    
}
