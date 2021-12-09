package com.example.lab1_maryasin;

import com.example.lab1_maryasin.relays.DeltaPIDRegulator;
import com.example.lab1_maryasin.relays.IdealPIDregulator;
import com.example.lab1_maryasin.relays.PIDregulator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.stage.Stage;

public class HelloController {

    ObservableList<String> pids = FXCollections.observableArrayList("Идеальный ПИД-регулятор",
            "Параллельный ПИД-регулятор", "Рекуррентный ПИД-регулятор");

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
    public ComboBox<String> relayType;

    @FXML
    public Button startButton;

    @FXML
    public TextArea tableResults;

    @FXML
    public TextField tdField;

    @FXML
    public TextField tiField;

    @FXML
    public void initialize() {
        relayType.setItems(pids);
        kaField.setText("10");
        tdField.setText("0.2");
        tiField.setText("1");
        minControl.setText("-1.8");
        maxControl.setText("1.8");
        maxProiz.setText("1");

        //Старт программы
        startButton.setOnAction(event->{
            double p = 10, ti =1, td = 0.1, dt = 0.1, maxOutput = 0, minOutput = 0, maxOutputRampRate = 0;
            IdealPIDregulator ireg = null; //Идеальный
            PIDregulator reg = null; //Параллельный
            DeltaPIDRegulator dreg = null; //Рекуррентный


            try {
                p = Double.parseDouble(kaField.getText());
                ti = Double.parseDouble(tiField.getText());
                td = Double.parseDouble(tdField.getText());
                minOutput = Double.parseDouble(minControl.getText());
                maxOutput = Double.parseDouble(maxControl.getText());
                maxOutputRampRate = Double.parseDouble(maxProiz.getText());
            }
            catch (Exception ex){
                System.out.println("Ошибка при записи значений: " + ex.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка записи значений");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }

            System.out.println(relayType.getSelectionModel().getSelectedItem());
            String typePID = relayType.getSelectionModel().getSelectedItem();
            tableResults.appendText("");

            if(typePID.equals("Идеальный ПИД-регулятор")){
                ireg = new IdealPIDregulator(p, ti, td);
                ireg.setOutputLimits(minOutput, maxOutput);
                ireg.setMaxOutputRampRate(maxOutputRampRate);
            }
            if(typePID.equals("Параллельный ПИД-регулятор")){
                reg = new PIDregulator(p, ti, td);
                reg.setOutputLimits(minOutput, maxOutput);
                reg.setMaxOutputRampRate(maxOutputRampRate);
            }
            if(typePID.equals("Рекуррентный ПИД-регулятор")){
                dreg = new DeltaPIDRegulator(p, ti, td, dt);
                dreg.setOutputLimits(minOutput, maxOutput);
                dreg.setMaxOutputRampRate(maxOutputRampRate);
            }

            double k = 1.0;
            double up = 0;
            double r =  0;
            double y = 0;
            double y1 = 0;
            double u = 1.8;
            double T = 1.0;

            System.out.printf("Target\tOutput\tControl\tError\n");
            tableResults.setText("Target\tOutput\tControl\tError\n");
            tableResults.appendText("");
            for (int i = 0; i < 100; i++)
            {
                if (i == 20) r = 1;

                if(typePID.equals("Идеальный ПИД-регулятор")) {
                    u = ireg.getOutput(y1, r);
                }
                if(typePID.equals("Параллельный ПИД-регулятор")) {
                    u = reg.getOutput(y1, r);
                }
                if(typePID.equals("Рекуррентный ПИД-регулятор")) {
                    u = up+dreg.getOutput(y1, r);
                    up = u;
                }

                y1 = k * dt / T * u + (T - dt) / T * y;
                y = y1;
                System.out.printf("%3.2f\t%3.2f\t%3.2f\t%3.2f\n", r, y1, u, (r - y1));
                DecimalFormat df = new DecimalFormat("##.##");
                double sub = r - y1;
                String str = df.format(r) + "\t" + df.format(y1) + "\t" + df.format(u) + "\t" + df.format(sub) + "\n";
                tableResults.appendText(str);
            }

        });
        //выход из программы
        exitButton.setOnAction(event->{
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        });
    }
}