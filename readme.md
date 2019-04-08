# Linear Equations Solver.
Linear Equations Solver is programme created to calculate systems of linear equations with graphic User Interface created in JavaFX. 
In this application you can create matrices with 10000 fields to generate solution of your 100 linear equations, after that you can save your problem result in *.txt file and reuse in the future application use:

File -> Open command.

Application is simple and useful. For example Civil Engineering students can use it to calculate construction beams .

# Math => Algorithm
This solution uses distribution matrix algorithm. It firstly creates distributed matrix, after that it multiples with vector B (vector of values) and return vector X. 
To generate invert matrix there is implemented [LU decomposition](https://en.wikipedia.org/wiki/LU_decomposition) method.
[Here](https://courses.lumenlearning.com/ivytech-collegealgebra/chapter/solving-a-system-of-linear-equations-using-the-inverse-of-a-matrix/) You can read more about invert matrix method.

# Author
©[Dominik Rolewski](http://github.com/drolewski)

# License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.