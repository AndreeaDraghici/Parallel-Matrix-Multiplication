# **Matrix Multiplication App User Manual**

## **Overview**
The Matrix Multiplication App is a JavaFX-based application designed to perform matrix multiplication using various algorithms. It supports three different approaches: Sequential, Parallel, and Strassen. Users can define matrix dimensions, select the desired algorithm, and view the results dynamically.

---

## **Features**
1. **Matrix Multiplication**: Multiply two matrices using Sequential, Parallel, or Strassen approaches.
2. **Dynamic Resizing**: The application adjusts its layout automatically when resized.
3. **Save Options**:
    - Save the output to a `.txt` file.
    - Export performance metrics to an Excel file.
4. **Execution Time**: Displays the time taken for the multiplication in the output.

---

## **Getting Started**

## **Introduction**

The **Matrix Multiplication App** is a robust application designed for educational and practical purposes to perform matrix multiplication using three distinct methods:
1. **Sequential Approach**: Standard matrix multiplication.
2. **Parallel Approach**: Uses multiple threads to improve computation time.
3. **Strassen Approach**: An optimized algorithm based on divide-and-conquer.

This app is equipped with a user-friendly interface, dynamic resizing, and options to save the results and performance metrics for further analysis. Whether you're a student, researcher, or developer, this app provides an intuitive way to understand and utilize matrix multiplication techniques.

---

## **How to Run the Application**

### **Prerequisites**
1. **Java Development Kit (JDK)**: Ensure JDK 8 or a later version is installed on your system.
2. **JavaFX Setup**: JavaFX should be properly configured in your development environment (e.g., IntelliJ IDEA or Eclipse).

### **Steps to Run**
1. Clone or download the project from the repository.
2. Navigate to the project directory and ensure all dependencies are correctly set up.
3. Compile the application:
   ```bash
   ./gradlew build

or use the IDE's build functionality. 4. Run the application:

From the IDE, execute the _MatrixMultiplicationApp.main()_ method.
Or use the terminal:

`java -jar build/libs/MatrixMultiplicationApp.jar`

---

## **User Interface**

### **Main Sections**
1. **Menu Bar**:
    - **Options Menu**:
        - `Exit`: Closes the application.
2. **Input Fields**:
    - **Rows Min & Max**: Specify the range for the number of rows in the matrices.
    - **Cols Min & Max**: Specify the range for the number of columns in the matrices.
3. **Algorithm Choice**:
    - `Sequential Approach`: Basic single-threaded multiplication.
    - `Parallel Approach`: Utilizes multiple threads for faster computation.
    - `Strassen Approach`: A divide-and-conquer-based optimized algorithm.
4. **Output Section**:
    - Displays the results of matrix multiplication.
    - Shows execution time.
   
![img.png](img.png)

---

## **Step-by-Step Guide**

### **1. Input Matrix Dimensions**
- Enter the minimum and maximum number of rows and columns for the matrices:
    - **Rows Min & Rows Max**: The number of rows in the first matrix.
    - **Cols Min & Cols Max**: The number of columns in the first matrix and rows in the second matrix.

### **2. Choose an Algorithm**
- Select one of the three radio buttons:
    - **Sequential Approach**: For basic single-threaded multiplication.
    - **Parallel Approach**: For multithreaded computation.
    - **Strassen Approach**: For advanced divide-and-conquer multiplication.

### **3. Start Multiplication**
- Click the **Start Multiplication** button to initiate the computation.
- The output, including the matrices and the result, will be displayed in the **Output Section**.

### **4. Save Results**
- Navigate to the **Save Tab** for the following options:
    - **Save Output to Text File**:
        - Saves the matrix multiplication results to a `.txt` file.
    - **Save Performance Metrics to Excel**:
        - Saves the execution times and other metrics to an Excel file.

### **5. Exit the Application**
- Use the **Options Menu** > `Exit` to close the application.

---

## **Example Workflow**

1. **Input Dimensions**:
    - Rows Min: 2
    - Rows Max: 4
    - Cols Min: 2
    - Cols Max: 4

2. **Select Algorithm**: Click on `Parallel Approach`.

3. **Start Multiplication**: Click on **Start Multiplication**.

4. **View Results**:
    - Matrices and their multiplication result will appear in the output section.
    - Execution time is also displayed.

5. **Save Results**:
    - Go to the **Save Tab** and choose to save either the text output or performance metrics.

---

## **Troubleshooting**

1. **Application Doesn't Launch**:
    - Ensure you have the correct version of Java and JavaFX installed.

2. **Invalid Input Error**:
    - Enter valid integers in the input fields for matrix dimensions.

3. **File Save Errors**:
    - Ensure you have write permissions to the chosen directory.

---

---
