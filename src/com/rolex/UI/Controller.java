package com.rolex.UI;

import com.rolex.datamodel.Matrix;
import javafx.beans.DefaultProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.util.ArrayList;

public class Controller {

    //gridPane that will handle coefficients of equations
    private static GridPane equationGridPane = new GridPane();

    @FXML
    private Spinner matrixSize;

    @FXML
    private GridPane mainWindow;

    @FXML
    private Button sizeButton;

    @FXML
    public void getMatrixSize(){

//      size clear the gridPane and set the size of gaps between element
        equationGridPane.getChildren().clear();
        mainWindow.getChildren().remove(equationGridPane);
        equationGridPane.setVgap(7);
        equationGridPane.setHgap(7);

        int sizeOfMatrix = (int) matrixSize.getValue();
        Matrix matrix = new Matrix(sizeOfMatrix);

        System.out.println(matrix.getMatrixSize());

//        create fields with declared size to input our matrix data
        ArrayList<TextField> textFields = new ArrayList<>();
        for(int i = 0 ; i < sizeOfMatrix*sizeOfMatrix ; i++ ) {
            TextField textElement = new TextField();
            textElement.setPromptText("Coefficient " + i);
            textElement.setMaxSize(100, 400);
            textFields.add(textElement);
        }


//        generate elements to the UI layout
        Label equalSign = new Label("=");
        int elementsCounter = 0;
        for(int i = 0 ; i < sizeOfMatrix ; i++){
            for(int j = 0; j < sizeOfMatrix ; j++ , elementsCounter++) {
                equationGridPane.add(textFields.get(elementsCounter),1 + j , 1 + i);
                if(elementsCounter == 0){
                    textFields.get(elementsCounter).requestFocus();
                }
            }
            TextField resultField = new TextField();
            resultField.setPromptText("Result " + i);
            resultField.setMaxSize(100,400);
            equationGridPane.add(resultField, 2 + sizeOfMatrix , 1 + i);

            if(i == sizeOfMatrix/2){
                equationGridPane.add(equalSign,1 + sizeOfMatrix , i + 1);
            }
        }
        mainWindow.add(equationGridPane,1,2);
    }
}
