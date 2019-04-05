package com.rolex.UI;

import javafx.fxml.FXML;

import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DialogPaneController {

    @FXML
    private DialogPane dialogPane;

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox vBoxX;


    public void noResult(){
        dialogPane.setPrefHeight(150);
        dialogPane.setPrefWidth(250);
        dialogPane.setHeaderText("");

        Label label = new Label("This System of Linear Equations doesn't have result.");
        label.setWrapText(true);

        gridPane.add(label, 2,2);
    }

    public void showResult(double[] result){

        dialogPane.setPrefHeight(400);
        dialogPane.setPrefWidth(400);

        VBox vBoxX = new VBox();
        gridPane.add(vBoxX, 2,2);

        VBox vBoxVector = new VBox();
        gridPane.add(vBoxVector,3,2);

        for(int i = 0; i < result.length ; i++){
            Label labelX = new Label("X " + (i +1) + " = ");
            vBoxX.getChildren().add(labelX);

            String resultElement = Double.toString(result[i]);
            Label labelResult = new Label(resultElement);
            vBoxVector.getChildren().add(labelResult);

        }
    }

}
