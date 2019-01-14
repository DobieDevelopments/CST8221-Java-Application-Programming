package calculator;

/*
 * File Header File name: [CalculatorModel.java ] 
 * Author: [ John Dobie, 040 659 609] 
 * Course: CST8221_ Java Application Programming 
 * Lab Section: [302] 
 * Assignment: [2] 
 * Date: [November 6th, 2018]
 * Professor: [Daniel Cormier] 
 * Purpose: [To perform the calulations for our calculator application]
 * Class list: [CalculatorModel]
 */

import java.math.BigDecimal;

/**
 * This class carries the creation of the UI and implementation of user interaction for the
 * calculator app.
 * 
 * @author John Dobie
 * @version 1.0
 * @see calculator
 * @since 1.8.0_121
 */
public class CalculatorModel {

  //////////// Member Fields////////////////////
  /**
   * 0.0
   */
  private String operand1 = "0.0";

  /**
   * 0.0
   */
  private String operand2 = "0.0";

  /**
   * OperationalMode.FLOAT_MODE
   */
  private OperationalMode mode = OperationalMode.FLOAT_MODE;

  /**
   * 0, 1, 2, or 3
   */
  private int precision;

  /**
   * false
   */
  private boolean errorState = false;

  /**
   * Operation.NONE
   */
  private Operation operation = Operation.NONE;

  /**
   * 
   */
  private double result;

  /**
   * Error.NONE
   */
  private Error errorMode = Error.NONE;

  //////////// Enumerations////////////////////

  /**
   * Operations that can be used.
   */
  enum Operation {
    /**
     * Addition Operation
     */
    ADD, 
    /**
     * Subtraction Operation
     */
    SUBTRACT, 
    /**
     * Multiplication Operation
     */
    MULTIPLY, 
    /**
     * Division Operation
     */
    DIVIDE, 
    /**
     * No Operation
     */
    NONE
  }

  /**
   * Calculator operating modes.
   */
  enum OperationalMode {
    /**
     * Integer mode
     */
    INT_MODE, 
    /**
     * Floating point mode
     */
    FLOAT_MODE
  }

  /**
   * Error modes.
   */
  enum Error {
    /**
     * Undefined
     */
    NAN, 
    /** 
     * Infinity
     */
    INF, 
    /**
     * No Error
     */
    NONE
  }

  //////////// Setters////////////////////

  /**
   * A setter for private member operand1
   * 
   * @param s the value to set operand1
   */
  public void setOperand1(String s) {
    operand1 = s;
  }

  /**
   * A setter for private member operand2
   * 
   * @param s the value to set operand2
   */
  public void setOperand2(String s) {
    operand2 = s;
  }

  /**
   * A setter for the calculator's current operation
   * 
   * @param op the operation to be used
   */
  public void setOperation(Operation op) {
    operation = op;
  }

  /**
   * A setter for the calculator's current mode
   * 
   * @param opMode the operating mode to be used
   */
  public void setMode(OperationalMode opMode) {
    mode = opMode;
  }

  /**
   * A setter for the calculator's floating point precision accuracy mode
   * 
   * @param fpPrecision the state of floating precision to be used
   */
  public void setPrecision(int fpPrecision) {
    this.precision = fpPrecision;
  }

  /**
   * A setter for the calculator's error state
   * 
   * @param state the error state to be set
   */
  public void setErrorState(boolean state) {
    errorState = state;
  }

  //////////// Getters////////////////////

  /**
   * A getter for the current error mode
   * 
   * @return the resulting error mode from calculations
   */
  public Error getError() {
    return errorMode;
  }

  /**
   * A getter for the error state from calculations
   * 
   * @return the resulting error state from calculations
   */
  public boolean getErrorState() {
    return errorState;
  }

  //////////// Methods////////////////////

  /**
   * formats the resulting calculation into a string for output
   * 
   * @return the calculation formatted into a string
   */
  public String getResult() {
    calculate();
    if(Double.isNaN(result) || Double.isInfinite(result))
      result = 0;
    switch (precision) {
      case 0:
        return new BigDecimal(result).toBigInteger().toString(); // Int
      case 1:
        return String.format("%.1f", new BigDecimal(result)); // .0
      case 2:
        return String.format("%.2f", new BigDecimal(result)); // .00
      case 3:
        return String.format("%.6e", new BigDecimal(result)); // sci
      default:
        System.err.println("Error in result precision");
        break;
    }
    return "";
  }

  /**
   * The main method that will perform our calculations
   */
  private void calculate() {
    float result = 0;
    int resultI = 0;

    switch (mode) {
      case INT_MODE:
        resultI = calcInt();
        break;
      case FLOAT_MODE:
        result = calcFloat();
        break;
      default:
        System.err.println("Operational Mode not set correctly");
        break;
    }
    this.result = result > resultI ? (float) result : (int) resultI;
  }

  /**
   * The method that handles integer calculations.
   * 
   * @return the result in an integer
   */
  private int calcInt() {
    int resultI = 0, op1 = 0, op2 = 0;
    errorMode = Error.NONE;

    try {
      op1 = Integer.parseInt(operand1);
    } catch (NumberFormatException e) {
      System.err.println("error: number format exception Operand1");
    }

    try {
      op2 = Integer.parseInt(operand2);
    } catch (NumberFormatException e) {
      System.err.println("error: number format exception Operand2");
    }

    setErrorState(false);

    if (op1 == 0 && op2 == 0 && operation == Operation.DIVIDE) {
      errorMode = Error.NAN;
      setErrorState(true);
    } else if (op2 == 0 && operation == Operation.DIVIDE) {
      errorMode = Error.INF;
      setErrorState(true);
    }

    if(!errorState)
    switch (operation) {
      case ADD:
        resultI = op1 + op2;
        break;
      case SUBTRACT:
        resultI = op1 - op2;
        break;
      case MULTIPLY:
        resultI = op1 * op2;
        break;
      case DIVIDE:
        resultI = op1 / op2;
        break;
      default:
        resultI = op1;
        break;
    }

    return resultI;
  }

  /**
   * The method that handles floating point calculations.
   * 
   * @return the result in a float
   */
  private float calcFloat() {
    float result = 0, op1 = 0, op2 = 0;
    errorMode = Error.NONE;

    try {
      op1 = Float.parseFloat(operand1);
    } catch (NumberFormatException e) {
      System.err.println("error: number format exception Operand1");
    }

    try {
      op2 = Float.parseFloat(operand2);
    } catch (NumberFormatException e) {
      System.err.println("error: number format exception Operand2");
    }

    switch (operation) {
      case ADD:
        result = op1 + op2;
        break;
      case SUBTRACT:
        result = op1 - op2;
        break;
      case MULTIPLY:
        result = op1 * op2;
        break;
      case DIVIDE:
        result = op1 / op2;
        break;
      default:
        result = op1;
        break;
    }

    setErrorState(false);
    if (Double.isNaN(result)) {
      errorMode = Error.NAN;
      setErrorState(true);
    } else if (Double.isInfinite(result)) {
      errorMode = Error.INF;
      setErrorState(true);
    }
    return result;
  }
}
