package com.rolex.UI;

import com.rolex.datamodel.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;

public class Controller {

    @FXML
    private Spinner matrixSize;

    @FXML
    private GridPane mainWindow;

    @FXML
    private Button sizeButton;

    @FXML
    public void getMatrixSize(){

        sizeButton.setDisable(true);
        matrixSize.setEditable(false);

        int sizeOfMatrix = (int) matrixSize.getValue();
        Matrix matrix = new Matrix(sizeOfMatrix);
        System.out.println(matrix.getMatrixSize());
        ArrayList<TextField> textFields = new ArrayList<>();

        for(int i = 0 ; i < sizeOfMatrix*sizeOfMatrix ; i++ ){
            textFields.add(new TextField("Coefficient " + i));
        }

        Label equalSign = new Label("=");
        int elementsCounter = 0;
        for(int i = 0 ; i < sizeOfMatrix ; i++){
            for(int j = 0; j < sizeOfMatrix ; j++ , elementsCounter++) {
                mainWindow.add(textFields.get(elementsCounter),1 + j , 1 + i);
//                mainWindow.add(new Label("x" + j), 2 + j ,1 + i);
                if(elementsCounter == 0){
                    textFields.get(elementsCounter).requestFocus();
                }
            }
            mainWindow.add(new TextField("Result " + i), 2 + sizeOfMatrix , 1 + i);
            if(i == (int) sizeOfMatrix/2){
                mainWindow.add(equalSign,1 + sizeOfMatrix , i + 1);
            }
        }
    }

}
