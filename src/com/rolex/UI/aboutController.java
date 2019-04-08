package com.rolex.UI;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

public class aboutController {

    @FXML
    private DialogPane about;

    @FXML
    private Label label;

    public void showInformation(){
        about.setHeaderText("Short Description.");

        label.setWrapText(true);
        label.setText("Linear Equations Solver is programme created to calculate systems of linear equations with graphic User Interface created in JavaFX.\n" +
                "In this application you can create matrices with 10000 fields to generate solution of your 100 linear equations, " +
                "after that you can save your problem result in *.txt file and reuse in the future application use:\n" +
                "File -> Open command.\n\n" +
                "Application is simple and useful. For example Civil Engineering students can use it to calculate construction beams .\n" +
                "\n" +
                "This solution uses distribution matrix algorithm. It firstly creates distributed matrix, " +
                "after that it multiples with vector B (vector of values) and return vector X.");
    }
}
