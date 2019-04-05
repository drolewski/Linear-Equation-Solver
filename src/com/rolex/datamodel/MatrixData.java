package com.rolex.datamodel;

import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MatrixData extends Matrix {

    public MatrixData(int matrixSize) {
        super(matrixSize);
    }
// Store Data in the file
    public void storeMatrix(String name) throws IOException{
        Path fileName = Paths.get(name);
        BufferedWriter bw = Files.newBufferedWriter(fileName);
        try{
            bw.write(String.format("Number of Equations:\t%s",super.getMatrixSize()));
            bw.newLine();
            bw.write("-------------------------------------------");
            bw.newLine();
            bw.write("Starting Matrix: ");
            bw.newLine();
            for(int i = 0; i < super.getMatrixSize(); i++){
                for(int j = 0; j < super.getMatrixSize(); j++){
                    bw.write("\t"+ super.getStartMatrix()[i][j] + "\t");
                }
                bw.newLine();
            }

            bw.write("-------------------------------------------");
            bw.newLine();
            bw.write("Vector of Values: ");
            bw.newLine();
            for(int i = 0; i < super.getValueVector().length; i++){
                bw.write("\t" + super.getValueVector()[i] + "");
                bw.newLine();
            }

            bw.write("===========================================");
            bw.newLine();
            bw.write("RESULTS");
            bw.newLine();
            bw.write("===========================================");
            bw.newLine();
            for(int i = 0 ; i < super.getSolutionVector().length; i++ ){
                bw.write("X" + (i+1) + "\t" + super.getSolutionVector()[i]);
                bw.newLine();
            }
            bw.write("-------------------------------------------");

        }finally {
            if(bw != null){
                bw.close();
            }
        }
    }
}
