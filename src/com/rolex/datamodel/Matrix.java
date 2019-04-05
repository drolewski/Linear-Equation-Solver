package com.rolex.datamodel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Matrix {

    private double[][] startMatrix;
    private int matrixSize;
    private double[][] matrix;
    private double[][] unitMatrix;
    private double[] valueVector;

    public Matrix(int matrixSize) {
        this.matrixSize = matrixSize;
        this.matrix = new double[matrixSize][matrixSize];
        this.unitMatrix = new double[matrixSize][matrixSize];
        for(int i = 0; i < matrixSize; i++){
            this.unitMatrix[i][i] = 1;
        }
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[][] getUnitMatrix(){
        return unitMatrix;
    }

//    Transpose matrix stored in the matrix field
    public void transposeMatrix(){
        double[][] matrixTransposed = new double[this.matrixSize][this.matrixSize];

        for(int i = 0; i < this.matrixSize; i++){
            for(int j = 0; j < this.matrixSize; j++){
                matrixTransposed[i][j] = this.matrix[j][i];
            }
        }
        this.matrix = matrixTransposed;
    }

//    Create matrix in our class field
    public void createMatrix(ArrayList<Double> listOfElements){
        int vectorPointer=0;
        for(int i = 0; i < this.matrixSize; i++){
            for(int j = 0 ; j < this.matrixSize ; j++){
                this.matrix[i][j] = listOfElements.get(vectorPointer);
                vectorPointer++;
            }
        }
        this.startMatrix = this.matrix;
    }

    public void printMatrix(double[][] matrix){
        for(int i = 0 ; i < matrix.length ; i++ ){
            for( int j = 0 ; j < matrix[0].length ; j++){
                System.out.print(matrix[i][j] + "\t\t");
            }
            System.out.println("\n");
        }
    }

//    matrices multiplication with vector parameter
//    this method multiple vector and this class field matrix
//    We dont have to worry about different vectors as Nx1,
//    because in Systems of Linear Equations vector X and produce
//    has only one column
    public double[] multipleMatrix(double[][] matrix, double[] tmpVector) {
        double[] resultMatrix = new double[this.matrixSize];
        
        if (matrix.length == tmpVector.length) {
            for (int i = 0; i < matrix.length; i++) {

                double tempValue = 0;
                for (int j = 0; j < matrix[0].length; j++) {

                    tempValue += tmpVector[j] * matrix[i][j];

                    if (j == matrix.length - 1) {
                        resultMatrix[i] = Math.round(tempValue*100000)/100000.0d; //set the precision of multiplication result as 4 sign after decimal point
                    }
                }
            }
        } else {
            System.out.println("Dimensions of matrix and vector aren't compatible.");
        }
        return resultMatrix;
    }

//    determinant function, it takes rank of matrix, analyzing row, vector of column indexes, and calculating matrix
//    In this method we use recursion and method similar as Laplace's way to count determinant
    private double det(int rank, int row, int[] vector,double[][] matrixA) {
        int result;
        int counter;
        int sign;

        if (rank == 1) {
            return matrixA[row][vector[0]];
        }else if( rank == 2){
            return ((matrixA[0][0]*matrixA[1][1]) - (matrixA[0][1]*matrixA[1][0]));
        }else{
            int[] columns = new int[this.matrixSize];
            result = 0;
            sign = 1;
            for(int i = 0; i < rank ; i++){

                counter = 0;
                for(int j = 0; j < rank - 1; j++){
                    if(counter == i){
                        counter++;
                    }
                    columns[j] = vector[counter++];
                }
                result += sign* matrixA[row][vector[i]] * det(rank-1, row + 1, columns, matrixA);
                sign = -sign;
            }
        }
        return result;
    }

//    simplest call of the determinant method
    public double det(){
        int[] vector = new int[this.matrixSize];
        for(int i = 0; i < this.matrixSize ; i++){
            vector[i] = i;
        }
        return det(this.matrixSize,0,vector,this.matrix);
    }

//    generating Lower and Upper distribution of our matrix
    private boolean luDistribution(int size, double[][] matrixA){

        for(int col = 0; col < size - 1; col++){
            if(Math.abs(matrixA[col][col]) < Math.exp(-12)){
                return false;
            }

            for(int i = col + 1; i < size; i++){
                matrixA[i][col] /= matrixA[col][col];
            }

            for(int i = col + 1; i < size; i++){
                for(int j = col + 1; j < size; j++){
                    matrixA[i][j] -= matrixA[i][col] * matrixA[col][j];
                }
            }
        }
        return true;
    }

//    easier way to call luDistribution method, without parameters it uses class fields
    private boolean luDistribution(){
        return luDistribution(this.matrixSize,this.matrix);
    }

//    Generate solution with Lower and Upper distribution to the inverting matrix problem
    private boolean luXSolver(int row){

        double s;

        for(int i = 1; i < this.matrixSize; i++){

            s =0;
            for (int j = 0; j < i; j++){
                s += this.matrix[i][j] * this.unitMatrix[j][row];
            }
            this.unitMatrix[i][row] -= s;
        }

        if(Math.abs(this.matrix[this.matrixSize-1][this.matrixSize-1]) < Math.exp(-12)){
            return false;
        }

        this.unitMatrix[this.matrixSize-1][row] /= this.matrix[this.matrixSize-1][this.matrixSize-1];

        for(int i = this.matrixSize - 2; i >= 0 ; i--){

            s = 0;
            for(int j = i + 1; j < this.matrixSize ; j++){

                s += this.matrix[i][j] * this.unitMatrix[j][row];
            }
            if(Math.abs(this.matrix[i][i]) < Math.exp(-12)){
                return false;
            }

            this.unitMatrix[i][row] = (this.unitMatrix[i][row] - s) / this.matrix[i][i];
        }
        return true;
    }

//    easier way to call luXSolver, to generate distribution matrix, return boolean
    public boolean luXSolver(){
        boolean ok;
        if(luDistribution()) {

            ok = true;
            for (int i = 0; i < this.matrixSize; i++) {
                if (!luXSolver(i)) {
                    ok = false;
                    break;
                }
            }
        }else {
            ok = false;
        }

        this.matrix = this.getUnitMatrix(); // return result of distribution matrix to the processing matrix
        return ok;

    }

//    main structure of calculator with methods call
    public double[] solveLinearEquationDistributionMatrix(double[] vector){
        this.valueVector = vector;
        if(vector.length == this.matrixSize) {
            if (det() == 0.0) {
                System.out.println("Determinant of the matrix is equal to 0.0.");
            } else {
                if (luXSolver()) {
                    double[] result = multipleMatrix(this.matrix,vector);
                    return result;
                } else {
                    System.out.println("Reversed Matrix doesn't exist.");
                }
            }
        }else{
            System.out.println("Different dimensions between vector and matrix.");
        }
        return null;
    }
}
































