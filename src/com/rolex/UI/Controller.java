package com.rolex.UI;

import com.rolex.datamodel.Matrix;
import com.rolex.datamodel.MatrixData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

public class Controller {

    //gridPane that will handle coefficients of equations
    private static GridPane equationGridPane = new GridPane();

    //Array list with textField information
    private static ArrayList<TextField> textFields = new ArrayList<>();

    //Array list with result Information
    private static ArrayList<TextField> resultField = new ArrayList<>();

    @FXML
    private Spinner matrixSize;

    @FXML
    private GridPane mainWindow;

    @FXML
    private Button calculateButton;

    @FXML
    private VBox menuBar;

//    Take size of equations system and generate it
    @FXML
    public void getMatrixSize(){

//      size clear the gridPane and set the size of gaps between element
        equationGridPane.getChildren().clear();
        mainWindow.getChildren().remove(equationGridPane);
        equationGridPane.setVgap(7);
        equationGridPane.setHgap(7);

        int sizeOfMatrix = (int) matrixSize.getValue();
        Matrix matrix = new Matrix(sizeOfMatrix);

//        System.out.println(matrix.getMatrixSize());

//        clear ArrayList of Matrix elements and arrayList of result elements.
        textFields.clear();
        resultField.clear();
//        create fields with declared size to input our matrix data
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
            }
            TextField tf = new TextField();
            tf.setPromptText("Result " + i);
            tf.setMaxSize(100,400);
            resultField.add(tf);
            equationGridPane.add(tf, 2 + sizeOfMatrix , 1 + i);

            if(i == sizeOfMatrix/2){
                equationGridPane.add(equalSign,1 + sizeOfMatrix , i + 1);
            }
        }
        mainWindow.add(equationGridPane,0,3);
        calculateButton.setDisable(false);
    }

//    Calculate the system of linear equations.
//    And create DialogPane with information about our results.
    @FXML
    public void calculateMatrix(){

        //dialog Pane initialization
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Linear Equations Result");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("resultWindow.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch(IOException e){
            System.out.println("Couldn't load the dialog.");
            e.printStackTrace();
            return;
        }

        Optional<ButtonType> optionButton; //option <button>

        MatrixData matrix = new MatrixData((int) matrixSize.getValue());
        ArrayList<Double> matrixVector = new ArrayList<>();
        double[] resultVector = new double[(int) matrixSize.getValue()];

        for(TextField tf : textFields){
            if(tf.getText().equals("")){
                tf.setText("0.0");
            }

            if(tf.getText().contains(",")){ //replace , to . from the input fields
                tf.setText(tf.getText().replace(',','.'));
            }

            //replace elements different than numbers
            if((!tf.getText().trim().matches("^-??[0-9]++\\.[0-9]++")) &&
                    (!tf.getText().trim().matches("^-??[0-9]++"))){

                tf.setText("0.0");
            }

//            System.out.println(tf.getText());
            matrixVector.add(Double.parseDouble(tf.getText()));
        }

        for(int i = 0; i < (int) matrixSize.getValue(); i++){
            if(resultField.get(i).getText().equals("")){
                resultField.get(i).setText("0.0");
            }

            if(resultField.get(i).getText().contains(",")){ // replace , to .
                resultField.get(i).setText(resultField.get(i).getText().replace(',','.'));
            }

            //replace elements different than numbers
            if((!resultField.get(i).getText().trim().matches("^-??[0-9]++\\.[0-9]++")) &&
                    (!resultField.get(i).getText().trim().matches("^-??[0-9]++"))){

                resultField.get(i).setText("0.0");
            }

//            System.out.println(resultField.get(i).getText());
            resultVector[i] = Double.parseDouble(resultField.get(i).getText());
        }


        matrix.createMatrix(matrixVector);
        double[] result;

        //      declaration of DialogPane with result information.
        if((result = matrix.solveLinearEquationDistributionMatrix(resultVector)) != null){
//            for(int i = 0; i < result.length; i++){
//                System.out.println("\n");
//                System.out.println("X" + (i + 1) + " = " + result[i]);
//            }
            ButtonType save = new ButtonType("Save");
            ButtonType close = new ButtonType("Close");

            dialog.getDialogPane().getButtonTypes().add(save);
            dialog.getDialogPane().getButtonTypes().add(close);

            DialogPaneController controller = fxmlLoader.getController();
            controller.showResult(result);

            optionButton = dialog.showAndWait();
            /*
            Open dialogPane with result of calculation and giv user choose to close the window
            or to save the created data by clicking OK.
            If user Click OK then fileChooser will appear to choose place there we would like to
            save data.
            */
            if(optionButton.isPresent() && optionButton.get() == save) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Solution of Linear Equations.");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text", "*.txt")
                );
                File file = fileChooser.showSaveDialog(mainWindow.getScene().getWindow());

                if(file != null) {
                    try {
                        matrix.storeMatrix(file.getPath().toString());

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
//               else{
//                    System.out.println("Chooser was canceled.");
//                }
            } else {
                dialog.close();
            }

        }else{
//            System.out.println("This System of Linear Equations doesn't have result.");

//          declaration of DialogPane with no result Alert.

            DialogPaneController controller = fxmlLoader.getController();
            controller.noResult();
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);


            optionButton = dialog.showAndWait();
            if(optionButton.isPresent() && optionButton.get() == ButtonType.OK){
                dialog.close();
            }

        }
    }

    @FXML
    public void closeWindow(){
        Stage window = (Stage) mainWindow.getScene().getWindow();
        window.close();
    }
}










