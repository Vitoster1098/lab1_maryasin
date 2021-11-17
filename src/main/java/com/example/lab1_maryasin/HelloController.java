package com.example.lab1_maryasin;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class HelloController {

    @FXML
    public ResourceBundle resources;

    @FXML
    public URL location;

    @FXML
    public Button exitButton;

    @FXML
    public TextField kaField;

    @FXML
    public TextField maxControl;

    @FXML
    public TextField maxProiz;

    @FXML
    public TextField minControl;

    @FXML
    public ComboBox<?> relayType;

    @FXML
    public Button startButton;

    @FXML
    public ListView<?> tableResults;

    @FXML
    public TextField tdField;

    @FXML
    public TextField tiField;

    @FXML
    public void initialize() {
        //Старт программы
        startButton.setOnAction(event->{
            float ka, ti, td, minContrVal, maxContrVal, maxProizVal;
            String typePID;
            try {
                ka = Float.parseFloat(kaField.getText());
                ti = Float.parseFloat(tiField.getText());
                td = Float.parseFloat(tdField.getText());
                typePID = (String)relayType.getSelectionModel().getSelectedItem();
                minContrVal = Float.parseFloat(minControl.getText());
                maxContrVal = Float.parseFloat(maxControl.getText());
                maxProizVal = Float.parseFloat(maxProiz.getText());
            }
            catch (Exception ex){
                System.out.println("Ошибка при записи значений: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка записи значений");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

        });
        //выход из программы
        exitButton.setOnAction(event->{
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }
}
